package me.study.jpa.ch02.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import me.study.jpa.EMF;
import me.study.jpa.ch02.models.User;

public class FindAllService {

	public List<User> getAllUsers() {
		EntityManager em = EMF.createEntityManager();
		try {
			em.getTransaction().begin();
			TypedQuery<User> query = em.createQuery("select u from User u order by u.name", User.class);
			List<User> users = query.getResultList();
			em.getTransaction().commit();
			return users;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}
