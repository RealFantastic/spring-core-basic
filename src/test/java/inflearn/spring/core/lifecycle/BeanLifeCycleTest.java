package inflearn.spring.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
        /*
        * 생성자 호출, URL = null
        * Connect : null
        * Call : null message = 초기화 연결 메세지
        * */
        //객체 생성 단계에는 URL이 없이 connect가 호출됨.
        //객체의 생성 단계 이후 setter 주입을 통해 url을 주입하기 때문에 connect, call 모두 url이 null인 것.
        //스프링 빈은 "객체 생성 -> 의존관계" 주입의 라이프 사이클을 거친 이후 사용할 준비가 완료됨.
        //생성자 주입은 객체 생성과 의존관계 주입이 동시에 발생 => 객체 생성 시점에 필요한 의존성이 없다면 에러가 발생

        //
    }

    @Configuration
    static class LifeCycleConfig{
        @Bean
        public NetworkClient networkClient(){
            NetworkClient client = new NetworkClient();
            client.setUrl("http://hello.com");
            return client;
        }
    }
}
