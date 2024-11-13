package inflearn.spring.core.scope;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import static org.assertj.core.api.Assertions.*;

import jakarta.inject.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
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
    @DisplayName("싱글톤 빈에서 프로토타입 빈을 주입받아 사용할 경우 같은 프로토타입이 재사용된다.")
    void singletonClientUsePrototype(){
        /*
        * 스프링 컨테이너 생성 시점에 싱글톤 빈이 생성되고, 프로토타입 빈을 주입받고 있기 때문에 해당 시점에
        * 프로토타입 빈을 생성해 싱글톤 빈에 주입하고 그 뒤로는 관리하지 않는다.
        * 하지만 싱글톤 빈으로 인해 해당 프로토타입 빈이 유지되고 있어서, 싱글톤 빈을 통해 프로토타입 빈을 사용할 경우
        * 스프링 컨테이너에서 생성하는 것이 아닌, 싱글톤 빈에 주입받은 프로토타입 빈 1개만 사용되므로, 목적에 맞게 동작하지 않게 된다.
        * */

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
        /* <<<DL(Dependency Lookup)>>>
         * 의존관계를 주입(DI) 받는 것이 아니라, 직접 필요한 의존관계를 찾는 것.
         * 이 방법을 통해 직접 의존관계를 찾아 주입시킬 수 있다.
         * 하지만 ApplicationContext 전체를 주입받을 경우, 스프링 컨테이너에 종속적인 코드가 되고, 단위테스트가 어려워진다.
         * */

        /* <<<ObjectProvider>>>
        * - 지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스 제공
        * - 과거에는 ObjectFactory가 있었으나, 여기에 편의 기능을 추가해서 ObjectProvider가 만들어짐.
        * - 핵심은 스프링 컨테이너를 통해서 의존성을 찾아주는 과정(DL)을 도와주는 것이다.(프로토타입 전용X)
        * - 기능이 단순하므로 단위테스트를 만들거나 mock 코드를 만들기 쉬워진다.
        * - ObjectFactory : 단순한 기능, 별도의 라이브러리 필요X, 스프링에 의존
        * - ObjectProvider : ObjectFactory 상속, 옵션, 스트림 처리 등 편의 기능 제공, 별도의 라이브러리 필요X, 스프링에 의존
        * */

        /* <<<JSR-330 Provider>>>
        * - javax.inject.Provider
        * - JSR-330 자바 표준을 사용
        * - SpringBoot 3.x 버전에선 jakarta.inject.Provider 사용
        * - 별도의 라이브러리가 필요(gradle에 의존성 추가해야함.)
        * - DL정도의 기능만 제공
        * - 'get()' 메서드 하나로 매우 단순
        * - 스프링이 아닌 다른 컨테이너에서도 사용 가능.
        * */

        @Autowired
        private Provider<PrototypeBean> provider;

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//        @Autowired
//        private ApplicationContext context;

//        private final PrototypeBean prototypeBean;
//
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }



        public int logic(){
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject(); //=> ObjectProvider
            PrototypeBean prototypeBean = provider.get(); //=> jakarta.inject.Provider
//            PrototypeBean prototypeBean = context.getBean(PrototypeBean.class);
            prototypeBean.addCount();

            int count = prototypeBean.getCount();

            return count;
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
