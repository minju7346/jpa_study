package jpabook.jpashop.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class) // Junit 실행 시 스프링을 이용한다.
@SpringBootTest
@Transactional // 영속성 컨텍스트 이용, 테스트 클래스에서만 롤백을 자동으로 하기 때문에 DB에 insert쿼리를 하지 않음
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;

	@Test
	public void 회원가입() throws Exception {
		//given - 이렇게 주어졌을 떄
		Member member = new Member();
		member.setName("kim");

		//when - 이렇게 하면
		Long savedId = memberService.join(member);

		//then - 이렇게 된다.
		assertEquals(member, memberRepository.findOne(savedId));
	}

	@Test(expected = IllegalStateException.class)
	public void 중복회원예외() throws Exception {
		//given
		Member m1 = new Member();
		m1.setName("kim");

		//when
		Member m2 = new Member();
		m2.setName("kim");
		memberService.join(m1);
		memberService.join(m2);
		//then
		fail("예외가 발생해야 한다.");
	}

}