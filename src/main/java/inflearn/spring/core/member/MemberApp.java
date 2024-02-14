package inflearn.spring.core.member;

import inflearn.spring.core.AppConfig;
import inflearn.spring.core.member.service.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//만든 회원가입 테스트 해보기
public class MemberApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = new MemberServiceImpl();
//        MemberService memberService = appConfig.memberService(); //memberService의 구현체는 appConfig에서 생성을 담당하도록 한다.

        //ApplicationContext : 스프링 컨테이너, 등록된 Bean들이 저장되어있음.
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = context.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);

        memberService.join(member);

        Member findMember = memberService.findMember(member.getId());
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
    //main 메서드로 테스트 하는 것은 일일이 눈으로 확인해야하는 번거로움이 있으므로 비효율적
}
