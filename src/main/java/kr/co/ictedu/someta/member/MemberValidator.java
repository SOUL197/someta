package kr.co.ictedu.someta.member;

import kr.co.ictedu.someta.vo.MemberVO;

public class MemberValidator {
	
	public static void validate(MemberVO m) {
        validateUsername(m.getUsername());
        validateNickname(m.getNickname());
        validateId(m.getId());
        validatePassword(m.getPwd());
        validateEmail(m.getEmail());
	}
	
	private static void validateUsername(String username) {
        if (username == null || !username.matches("^[가-힣A-Za-z]{1,30}$")) {
            throw new IllegalArgumentException("이름은 1~30자의 한글 또는 영문만 사용할 수 있습니다.");
        }
    }

    private static void validateNickname(String nickname) {
        if (nickname == null || !nickname.matches("^[가-힣A-Za-z]{1,30}$")) {
            throw new IllegalArgumentException("닉네임은 1~30자의 한글 또는 영문만 사용할 수 있습니다.");
        }
    }

    private static void validateId(String id) {
        if (id == null || !id.matches("^[a-z0-9_-]{5,30}$")) {
            throw new IllegalArgumentException("아이디는 5~30자의 영문 소문자, 숫자, _, - 만 사용할 수 있습니다.");
        }
    }

    private static void validatePassword(String pwd) {
        if (pwd == null || !pwd.matches(
                "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9])[^\\s]{8,40}$")) {
            throw new IllegalArgumentException(
                "비밀번호는 8~40자이며 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9]+@[A-Za-z0-9]+$")) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }
}
