package inflearn.spring.core.scope;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {
    @Test
    @DisplayName("프로토타입 빈은 사용할 때마다 매번 새로 생성된다.")
    void prototypeFind(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean bean1 = context.getBean(PrototypeBean.class);
        bean1.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);

        PrototypeBean bean2 = context.getBean(PrototypeBean.class);
        bean2.addCount();
        assertThat(bean2.getCount()).isEqualTo(1);


        context.close();
    }

    @Test
    @DisplayName("싱글톤 빈에서 프로토타입 빈을 사용할 경우 같은 프로토타입이 재사용된다.")
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean client = context.getBean(ClientBean.class);
        int count1 = client.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean client2 = context.getBean(ClientBean.class);
        int count2 = client2.logic();
        assertThat(count2).isEqualTo(2);


    }

    @Scope("singleton")
    static class ClientBean{
        @Autowired
        private ApplicationContext context;
//        private final PrototypeBean prototypeBean;
//
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic(){
            PrototypeBean prototypeBean = context.getBean(PrototypeBean.class);
            prototypeBean.addCount();

            return prototypeBean.getCount();
        }


    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBeans init: " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBeans destroy");
        }
    }
}
