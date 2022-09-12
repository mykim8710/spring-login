package com.example.login.web.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
public class MemberJoinDto {
    @NotBlank
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
