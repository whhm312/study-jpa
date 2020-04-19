package me.study.jpa.ch02.services;

import javax.persistence.EntityManager;

import me.study.jpa.EMF;
import me.study.jpa.ch02.exceptions.UserNotFoundException;
import me.study.jpa.ch02.models.User;

public class CancelService {
	public void cancel(String email) {
		EntityManager em = EMF.createEntityManager();
		em.getTransaction().begin();
		try {
			User user = em.find(User.class, email);
			if (user == null) {
				throw new UserNotFoundException();
			}

			em.remove(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

}
