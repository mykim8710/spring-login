package com.example.login.web.member.controller;

import com.example.login.domain.member.model.Member;
import com.example.login.domain.member.repository.MemberRepository;
import com.example.login.web.member.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("member") MemberJoinDto memberJoinDto, BindingResult result) {
        System.out.println("memberJoinDto = " + memberJoinDto);
        
        if (result.hasErrors()) {
            log.error("error={}", result);
            return "members/addMemberForm";
        }

        Member member = new Member();
        member.setLoginId(memberJoinDto.getLoginId());
        member.setName(memberJoinDto.getName());
        member.setPassword(memberJoinDto.getPassword());

        log.info("member={}", member);

        memberRepository.save(member);
        return "redirect:/";
    }
}
