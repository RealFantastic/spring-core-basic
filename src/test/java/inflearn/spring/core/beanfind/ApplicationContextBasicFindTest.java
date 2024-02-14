package inflearn.spring.core.beanfind;

import inflearn.spring.core.AppConfig;
import inflearn.spring.core.member.service.MemberService;
import inflearn.spring.core.member.service.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2(){
        //구체 타입으로 조회해도 되는 이유
        // - 스프링 빈에 등록된 인스턴스 타입으로 결정되기 때문에 인터페이스가 아닌 구체 타입으로도 조회가 가능하다.
        // - 단, 구현체에 의존하는 것 자체는 좋지 않기 때문에 테스트 코드 작성시에도 인터페이스로 확인해볼 수 있도록 한다.
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회 실패")
    void findBeanByNameFail(){
        //org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'xxxxxxx' available
        //없는 빈을 조회하려고 할 경우 NoSuchBeanDefinitionException 발생

        //테스트 코드에는 실패 테스트도 중요!
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxxxxx", MemberService.class));
    }

}
