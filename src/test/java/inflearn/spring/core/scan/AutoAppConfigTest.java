package inflearn.spring.core.scan;

import inflearn.spring.core.AutoAppConfig;
import inflearn.spring.core.member.repository.MemberRepository;
import inflearn.spring.core.member.service.MemberService;
import inflearn.spring.core.order.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {
    @Test
    void basicScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
        //ClassPathBeanDefinitionScanner --  Identified candidate component class 로그와 함꼐 @Component를 붙인 클래스들이 나옴.

        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
    }

}
