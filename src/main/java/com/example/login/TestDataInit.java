package com.example.login;

import com.example.login.domain.item.model.Item;
import com.example.login.domain.item.repository.ItemRepository;
import com.example.login.domain.member.model.Member;
import com.example.login.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

        Member member = new Member();
        member.setLoginId("test");
        member.setName("test");
        member.setPassword("1234");
        memberRepository.save(member);
    }

}