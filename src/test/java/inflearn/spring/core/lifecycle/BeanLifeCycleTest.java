package inflearn.spring.core.lifecycle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
        /*
        * 생성자 호출, URL : null
        * connect : null
        * call : nullmessage : 초기화 연결 메세지
        * */
        //Bean 등록시 setUrl을 통해 url을 주입했으나, 주입되지 않음.
        //new로 생성자를 호출한 이후에 setter로 초기화 하고있음
        //스프링 빈의 라이프사이클 : 객체 생성 -> 의존관계 주입
        //따라서 객체를 생성하는 시점에는 url에 대한 값이 없기 때문에 null로 출력되는 것.
        //실제 의존관계 주입시에 초기화 하기 때문에 url에 값이 없음.
        /*
        * 생성자 주입은 예외 : 생성자 주입은 생성자 호출 시에 파라미터로 의존관계를 집어넣기 때문에 생성자 호출시점에 의존관계가 존재하지 않으면 객체 생성 불가함.
        * > 파라미터에 null을 넣지 않는 한 NPE 우려가 없음.
        * > 의존관계에 대한 내용을 객체 생성 시점으로 끌어올려 컴파일 시점에 오류를 찾을 수 있음.
        * */

        //스프링 빈의 이벤트 라이프사이클
        //스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입(setter, field inject시 이 시점에 의존관계 주입)
        // -> 초기화 콜백 -> 사용 -> 소멸 전 콜백 -> 스프링 종료
        
        /*
        * 객체의 생성과 초기화를 분리하자
        * 생성자는 필수 정보(파라미터)를 받아 메모리 할당해 객체 생성하는 책임
        * 초기화는 생성된 값을 활용해 커넥션 등 무거운 작업을 수행
        * 생성자에서 무거운 초기화를 함께 하는 것 보단 생성과 초기화를 분리하는게 유지보수에 용이
        */
    }

    @Configuration
    static class LifeCycleConfig {
        //NetworkClient Bean 등록
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://localhost:8080");
            return networkClient;
        }
    }
}
