package com.example.login.web.session;

import com.example.login.domain.member.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void setSessionManagerTest() {
        // 세션 생성 test
        MockHttpServletResponse response = new MockHttpServletResponse();   // HttpServletRequest, HttpservletResponse 객체를 직접 사용할 수 없기 때문에 테스트에서 비슷한 역할을 해주는 가짜 MockHttpServletRequest, MockHttpServletResponse 를 사용
        Member member = new Member();
        sessionManager.createSession(member, response);

        // 요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션만료
        sessionManager.expireSession(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }

}