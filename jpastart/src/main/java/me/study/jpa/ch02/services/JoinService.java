package me.study.jpa.ch02.services;

import javax.persistence.EntityManager;

import me.study.jpa.EMF;
import me.study.jpa.ch02.exceptions.DuplicateEmailException;
import me.study.jpa.ch02.models.User;

public class JoinService {
	public void join(User user) {
		EntityManager em = EMF.createEntityManager();
		em.getTransaction().begin();

		try {
			User found = em.find(User.class, user.getEmail());
			if (found != null) {
				throw new DuplicateEmailException();
			}

			em.persist(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

	}
}
