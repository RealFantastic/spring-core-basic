package inflearn.spring.core.member;

import inflearn.spring.core.AppConfig;
import inflearn.spring.core.member.service.MemberService;
import inflearn.spring.core.member.service.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void join(){
        //given
        //회원 정보 생성
        Member memberA = new Member(1L, "memberA", Grade.VIP);

        //when
        //회원 가입 후
        memberService.join(memberA);
        //가입한 회원 찾기
        Member findMember = memberService.findMember(memberA.getId());

        //then
        // 저장된 회원과 회원 정보가 동일하면 테스트 성공
        Assertions.assertThat(memberA).isEqualTo(findMember);
    }
    //코드 변경, 추가시 테스트 결과를 직관적으로 확인 가능


}
