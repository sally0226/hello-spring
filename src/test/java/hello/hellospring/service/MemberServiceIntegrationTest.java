package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

// 통합 테스트

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다
@Transactional // 커밋하기 전까지는 쿼리가 디비에 반영이 안됨 , 이 성질 이용해서 test반복가능하도록 롤백해줌
// 테스트 시작전에 트랜잭션을 시작하고 테스트 완료 후에 항상 롤백한다. (db에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다)
public class MemberServiceIntegrationTest {
    @Autowired MemberService memberService; // testcase할 때는 그냥 autowired로 받아도 됨
    @Autowired MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository); //테스트마다 독립적으로 실행되도록 매번 새로 넣어줌
    }

    @Test
    void 회원가입() { // 테스트 코드들은 과감하게 함수 이름 한글로 바꿔도 됨
        // 뭔가가 주어졌을 때(given), 뭐를 실행했는데(when), 뭐가 나와야해(then) 문법!
        // given
        Member member = new Member();
        member.setName("spring");
        // when
        Long saveId = memberService.join(member);
        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        try{
//            memberService.join(member2);
//            fail();
//        }catch(IllegalStateException e){
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then
    }
}
