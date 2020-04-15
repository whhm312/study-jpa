package me.study.jpa.ch02.service;

import javax.persistence.EntityManager;

import me.study.jpa.ch02.model.User;

public class JoinService {
	public void join(EntityManager em, User user) throws DuplicateEmailException {
		User found = em.find(User.class, user.getEmail());
		if (found != null) {
			throw new DuplicateEmailException(user.getEmail() + "은 중복 된 이메일 주소 입니다.");
		}

		em.persist(user);
	}
}
