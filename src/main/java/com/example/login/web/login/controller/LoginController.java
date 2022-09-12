package com.example.login.web.login.controller;

import com.example.login.domain.login.sevice.LoginService;
import com.example.login.domain.member.model.Member;
import com.example.login.web.login.dto.LoginFormDto;
import com.example.login.web.session.SessionConst;
import com.example.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("")
public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginFormDto form) {
        return "login/loginForm";
    }

    // @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginFormDto form, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            log.error("error={}", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if(loginMember == null) {
            log.error("Login fail!!");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        // 로그인에 성공하면 쿠키를 생성하고 HttpServletResponse에 담는다.
        // 쿠키 이름은 memberId 이고, 값은 회원의 id를 담아둔다.
        // 웹브라우저는 종료 전까지 회원의 id를 서버에 계속 보내 줄 것이다.
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        log.info("Login success!! loginMember={}", loginMember);
        return "redirect:/";
    }

    // @PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute("loginForm") LoginFormDto form, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            log.error("error={}", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if(loginMember == null) {
            log.error("Login fail!!");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
        sessionManager.createSession(loginMember, response);

        log.info("Login success!! loginMember={}", loginMember);
        return "redirect:/";
    }

    // @PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute("loginForm") LoginFormDto form, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            log.error("error={}", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if(loginMember == null) {
            log.error("Login fail!!");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        log.info("Login success!! loginMember={}", loginMember);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV4(@Validated @ModelAttribute("loginForm") LoginFormDto form, BindingResult bindingResult, @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            log.error("error={}", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if(loginMember == null) {
            log.error("Login fail!!");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        log.info("Login success!! loginMember={}", loginMember);
        return "redirect:" + redirectURL;
    }

    // @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    // @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expireSession(request);
        return "redirect:/";
    }

    //@PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);    // true이면 새로운 세션을 생성하기 때문에 false로 한다.
        if (session != null) {
            session.invalidate();   // 세션을 제거
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutV4(HttpServletRequest request) {
        log.info("Do logout!!");
        HttpSession session = request.getSession(false);    // true이면 새로운 세션을 생성하기 때문에 false로 한다.
        if (session != null) {
            session.invalidate();   // 세션을 제거
        }

        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
