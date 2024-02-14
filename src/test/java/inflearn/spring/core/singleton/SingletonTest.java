package inflearn.spring.core.singleton;

import inflearn.spring.core.AppConfig;
import inflearn.spring.core.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너는 요청할 때마다 객체를 생성한다.")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        //1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        //2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른 것을 확인한다.
        System.out.println("memberService1 = " + memberService1); //inflearn.spring.core.member.service.MemberServiceImpl@1e13529a
        System.out.println("memberService2 = " + memberService2); //inflearn.spring.core.member.service.MemberServiceImpl@57a3e26a

        //memberService1 != MemberService2
        assertThat(memberService1).isNotSameAs(memberService2);

        //고객 트래픽이 늘어날 때마다 생성하는 객체 수도 그만큼 많아지고 삭제되는 객체수도 많아지게 되는 문제가 발생한다.
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
//        new SingletonService(); //SingletonService() has private access in SingletonService
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        
        assertThat(singletonService1).isSameAs(singletonService2);
        //same : == 동일성 비교, 실제 참조값이 같은지를 비교하게됨.
        //equal : 동등성 비교
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        //2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        //참조값이 다른 것을 확인한다.
        System.out.println("memberService1 = " + memberService1); //inflearn.spring.core.member.service.MemberServiceImpl@1e13529a
        System.out.println("memberService2 = " + memberService2); //inflearn.spring.core.member.service.MemberServiceImpl@57a3e26a

        //memberService1 == MemberService2
        assertThat(memberService1).isSameAs(memberService2);

    }

}
