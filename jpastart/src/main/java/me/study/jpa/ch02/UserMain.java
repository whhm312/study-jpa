package me.study.jpa.ch02;

import java.util.Date;
import java.util.Scanner;

import com.mysql.cj.util.StringUtils;

import me.study.jpa.EMF;
import me.study.jpa.ch02.model.User;
import me.study.jpa.ch02.service.DuplicateEmailException;
import me.study.jpa.ch02.service.JoinService;

public class UserMain {
	public static void main(String[] args) {
		EMF.init();

		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("명령어를 입력하고 엔터키를 입력 하세요.");
			System.out.println("명령어) join, view, list, changename, withdraw, exit.");
			String userCommand = scan.next();
			if ("exit".equals(userCommand)) {
				System.out.println("종료합니다.");
				break;
			} else if ("join".equals(userCommand)) {
				System.out.println("가입 할 이메일을 입력 하세요.");
				String email = scan.next();
				System.out.println("가입 할 이름 입력 하세요.");
				String name = scan.next();

				join(email, name);
			} else if ("view".equals(userCommand)) {
			} else if ("list".equals(userCommand)) {
			} else if ("changename".equals(userCommand)) {
			} else if ("withdraw".equals(userCommand)) {
			} else {
				System.out.println("올바른 명령어를 입력하세요.");
			}
			System.out.println("-------------------");
		}
		scan.close();

		EMF.close();
	}

	private static JoinService joinService = new JoinService();

	private static void join(String email, String name) {
		if (StringUtils.isNullOrEmpty(email) || StringUtils.isNullOrEmpty(name)) {
			System.out.println("join을 하기위한 파라메터 값이 옳바르지 않습니다.");
			System.out.println("이메일 : " + email);
			System.out.println("이름 : " + name);
			return;
		}

		try {
			joinService.join(new User(email, name, new Date()));
			System.out.println(name + "이 성공적으로 가입 되었습니다.");
		} catch (DuplicateEmailException e) {
			System.out.println("이미 같은 이메일을 가진 사용자가 존재합니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
