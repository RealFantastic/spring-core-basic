package inflearn.spring.core.singleton;

import inflearn.spring.core.AppConfig;
import inflearn.spring.core.member.repository.MemberRepository;
import inflearn.spring.core.member.service.MemberService;
import inflearn.spring.core.member.service.MemberServiceImpl;
import inflearn.spring.core.order.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository2 = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository = memberService.getMemberRepository();
        MemberRepository memberRepository1 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository);//inflearn.spring.core.member.repository.MemoryMemberRepository@56f2bbea
        System.out.println("orderService -> memberRepository1 = " + memberRepository1);//inflearn.spring.core.member.repository.MemoryMemberRepository@56f2bbea
        System.out.println("memberRepository2 = " + memberRepository2);
        //분명 AppConfig의 코드를 보면 memberService와 orderService를 생성할 때 MemoryMemberRepository를 총 두번 생성한다.
        //하지만 왜 두 Service에서 가져온 memberRepository가 동일한 것일까?, 심지어 memberRepository를 꺼내도 같은 인스턴스가 반환된다 왜일까?
        //아래 configurationDeep 테스트에서 확인해보자

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository2);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository2);
    }

    @Test
    void configurationDeep(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass()); //inflearn.spring.core.AppConfig$$SpringCGLIB$$0
        //@Configurtaion이 붙은 설정클래스 AppConfig도 스프링 빈으로 등록되는데,
        //이 때 실제 AppConfig가 등록되는 것이 아닌, 해당 클래스를 상속받은 AppConfig@CGLIB가 빈으로 등록된다.
        //바이트 코드 조작 라이브러리인 CGLIB가 각 @Bean이 붙은 메서드마다 분기처리 로직을 추가하면서 MemberRespository가 한번만 호출되게 하여(실제로는 더 복잡)
        //싱글톤을 보장하는 것!(중요)
        //@Configuration 어노테이션을 지우고 Bean을 등록하면 순수한 AppConfig가 Bean으로 등록되고,
        //처음 내가 예상한대로 MemberRepository가 3번 호출되게 된다.

    }
}
