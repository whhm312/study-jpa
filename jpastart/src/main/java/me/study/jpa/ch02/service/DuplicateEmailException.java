package me.study.jpa.ch02.service;

public class DuplicateEmailException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateEmailException() {
	}

	public DuplicateEmailException(String msg) {
		super(msg);
	}
}
