package inflearn.spring.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Setter
public class NetworkClient /*implements InitializingBean, DisposableBean*/ {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, URL : " + url);
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String msg){
         System.out.println("call : " + url + " message : " + msg);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close : " + url);
    }


    //1. InitializingBean, DisposableBean 사용
    //초기화, 소멸 interface의 단점
    // - 두 interface는 스프링의 코드이기 때문에 내 코드가 스프링에 의존적으로 변함.
    // - 내가 코드를 고칠 수 없는 외부라이브러리에는 적용하지 못함.
    // - 참고 : 해당 인터페이스는 스프링 초창기에 생성된 인터페이스로 현재는 거의 사용하지 않는다.

    //의존관계 주입이 완료되면 호출
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        connect();
//        call("초기화 연결 메세지");
//    }
//
//    //종료시 호출
//    @Override
//    public void destroy() throws Exception {
//        disconnect();
//    }

    //
    /*2. 빈 등록 초기화, 소멸 메서드 지정
    *  - Bean 등록시 @Bean 어노테이션의 속성으로 initMethod, destroyMethod 지정(ex - initMethod= "init")
    *  - 설정 정보 사용의 특징
    *    > 메서드명을 자유롭게 지을 수 있음.
    *    > 스프링 빈이 스프링 코드에 의존하지 않음.
    *    > 설정정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드 적용 가능(가장 큰 장점)
    *
    *  - 종료 메서드 추론
    *    > 라이브러리는 대부분 close, shutdown이라는 이름의 종료 메서드를 사용
    *    > @Bean의 destroyMethod 속성에는 기본값이 (inferred)(추론)으로 등록되어 있음.
    *    > (inferred)라는 추론 기능으로 close, shutdown이라는 이름의 메서드를 자동으로 호출해줌.
    *    > 따라서 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 동작함.
    *    > 추론 기능이 싫다면 destroyMethod=""처럼 빈 공백을 지정
    *
    * ※ 수업 중 참고 : AutoCloseable 인터페이스는 찾아서 공부해보면 좋다!
    *
    *
    * */

    /*3. @PostConstruct, @PreDestroy 사용
    *  - 스프링에서 권장하는 방법
    *  - javax.annotation.XXX로 스프링 종속인 기술이 아니라, JSR-250 자바 표준이다.
    *  - 따라서 스프링 컨테이너가 아닌 다른 컨테이너에서도 동작한다.
    *  - 컴포넌트 스캔과 잘 어울린다.
    *  - 단점 : 외부 라이브러리에 사용하지 못한다. => @Bean의 initMethod, destroyMethod 옵션을 사용하도록
    * */
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }

}
