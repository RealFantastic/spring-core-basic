package inflearn.spring.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A사용자가 10000원 주문
        statefulService1.order("userA", 10000);

        //ThreadB: B사용자가 20000원 주문
        statefulService2.order("userA", 20000);

        //ThreadA: A사용자가 주문 금액을 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        //싱글톤 사용시 무상태로 만들어야 하는 이유
        //A가 10000원 주문하고 조회하기 전까지 그 사이에 B 사용자가 20000원 주문을 했다.
        //statefulService1, 2로 나눠서 사용했음에도, 싱글톤이기 때문에 statefulService의 price는 서로 공유된다.
        //따라서 B사용자가 주문할 때 20000원으로 변경되기 때문에, A가 주문을 조회할 때 20000원이 출력됨. 이것은 아주 큰 문제!!!!!
        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}