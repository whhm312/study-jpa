package me.study.jpa.ch02.services;

import java.util.Optional;

import javax.persistence.EntityManager;

import me.study.jpa.EMF;
import me.study.jpa.ch02.models.User;

public class FindService {

	public Optional<User> getUser(String email) {
		EntityManager em = EMF.createEntityManager();
		try {
			User user = em.find(User.class, email);
			return Optional.ofNullable(user);
		} finally {
			em.close();
		}
	}
}
