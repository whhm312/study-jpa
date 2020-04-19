package me.study.jpa.ch02;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.mysql.cj.util.StringUtils;

import me.study.jpa.EMF;
import me.study.jpa.ch02.exceptions.DuplicateEmailException;
import me.study.jpa.ch02.exceptions.UserNotFoundException;
import me.study.jpa.ch02.models.User;
import me.study.jpa.ch02.services.CancelService;
import me.study.jpa.ch02.services.ChangeNameService;
import me.study.jpa.ch02.services.FindAllService;
import me.study.jpa.ch02.services.FindService;
import me.study.jpa.ch02.services.JoinService;

public class UserMain {
	public static void main(String[] args) {
		EMF.init();

		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("명령어를 입력하고 엔터키를 입력 하세요.");
			System.out.println("명령어) join, view, list, changename, cancel, exit.");
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
				System.out.println("조회 할 이메일을 입력 하세요.");
				String email = scan.next();
				find(email);
			} else if ("list".equals(userCommand)) {
				findAll();
			} else if ("changename".equals(userCommand)) {
				System.out.println("변경 할 대상의 이메일을 입력하세요.");
				String email = scan.next();
				System.out.println("변경 할 이름을 입력하세요.");
				String newName = scan.next();
				change(email, newName);
			} else if ("cancel".equals(userCommand)) {
				System.out.println("삭제 할 대상의 이메일을 입력하세요.");
				String email = scan.next();
				cancel(email);
			} else {
				System.out.println("올바른 명령어를 입력하세요.");
			}
			System.out.println("-------------------");
		}
		scan.close();

		EMF.close();
	}

	private static void cancel(String email) {
		if (StringUtils.isNullOrEmpty(email)) {
			System.out.println("cancel 하기위한 파라메터 값이 옳바르지 않습니다.");
			System.out.println("이메일 : " + email);
			return;
		}

		CancelService service = new CancelService();
		try {
			service.cancel(email);
			System.out.println("cancel이 성공적으로 됐습니다.");
		} catch (UserNotFoundException e) {
			System.out.println("해당 이메일 정보가 존재하지 않습니다.");
		}
	}

	private static void findAll() {
		FindAllService service = new FindAllService();
		List<User> users = service.getAllUsers();
		if (users.isEmpty()) {
			System.out.println("사용자가 없습니다.");
		} else {
			System.out.println("| 이메일 | 이름 | 생성일자 |");
			users.forEach(user -> System.out.printf("| %s | %s | %tY-%<tm-%<td |%n", user.getEmail(), user.getName(),
					user.getCreateDate()));
		}
	}

	private static void change(String email, String name) {
		if (StringUtils.isNullOrEmpty(email) || StringUtils.isNullOrEmpty(name)) {
			System.out.println("changeName을 하기위한 파라메터 값이 옳바르지 않습니다.");
			System.out.println("이메일 : " + email);
			System.out.println("이름 : " + name);
			return;
		}

		ChangeNameService changeNameService = new ChangeNameService();
		try {
			changeNameService.changeName(email, name);
			System.out.println("이름을 변경했습니다.");
		} catch (UserNotFoundException e) {
			System.out.println("해당 이메일 정보가 존재하지 않습니다.");
		}

	}

	private static void find(String email) {
		if (StringUtils.isNullOrEmpty(email)) {
			System.out.println("view을 하기위한 파라메터 값이 옳바르지 않습니다.");
			System.out.println("이메일 : " + email);
			return;
		}

		FindService findService = new FindService();
		Optional<User> optionalUser = findService.getUser(email);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			System.out.println("이름 : " + user.getName());
			System.out.printf("join 일시 : %tY-%<tm-%<td\n", user.getCreateDate());
		} else {
			System.out.println("존재하지 않습니다.");
		}
	}

	private static void join(String email, String name) {
		if (StringUtils.isNullOrEmpty(email) || StringUtils.isNullOrEmpty(name)) {
			System.out.println("join을 하기위한 파라메터 값이 옳바르지 않습니다.");
			System.out.println("이메일 : " + email);
			System.out.println("이름 : " + name);
			return;
		}

		JoinService joinService = new JoinService();
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
