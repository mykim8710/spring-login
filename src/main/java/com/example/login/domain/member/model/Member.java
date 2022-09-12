package com.example.login.domain.member.model;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String loginId; // 로그인 아이디
    private String name;    // 사용자 이름
    private String password;

    public Member() {
    }

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
