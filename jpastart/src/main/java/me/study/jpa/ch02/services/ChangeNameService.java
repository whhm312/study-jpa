package me.study.jpa.ch02.services;

import javax.persistence.EntityManager;

import me.study.jpa.EMF;
import me.study.jpa.ch02.exceptions.UserNotFoundException;
import me.study.jpa.ch02.models.User;

public class ChangeNameService {
	public void changeName(String email, String newName) {
		EntityManager em = EMF.createEntityManager();
		try {
			em.getTransaction().begin();
			User user = em.find(User.class, email);
			if (user == null) {
				throw new UserNotFoundException();
			}
			user.changeName(newName);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}
