package com.example.login.domain.member.repository;

import com.example.login.domain.member.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    // 회원가입
    public Member save(Member member) {
        member.setId(++sequence);
        log.info("member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    // 회원조회(id)
    public Member findById(Long id) {
        return store.get(id);
    }

    // 회원조회(loginId)
    public Optional<Member> findByLoginId(String loginId) {
//        List<Member> all = findAll();
//        for (Member member : all) {
//            if(member.getLoginId().equals(loginId)) {
//                return Optional.of(member);
//            }
//        }
//        return Optional.empty();

        return findAll().stream()  // List -> stream 루프를 돈다
                    .filter(member -> member.getLoginId().equals(loginId))
                    .findFirst();
    }

    // 전체 회원조회
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // map 초기화
    public void clear() {
        store.clear();
    }
}
