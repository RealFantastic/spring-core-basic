package inflearn.spring.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, URL : " + url);
    }

    public void setUrl(String url) {
        this.url = url;
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
    
    //의존관계 주입이 완료되면 호출
    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        call("초기화 연결 메세지");
    }
    
    //종료시 호출
    @Override
    public void destroy() throws Exception {
        disconnect();
    }
    //초기화, 소멸 interface의 단점
    //1. 두 interface는 스프링의 코드이기 때문에 내 코드가 스프링에 의존적으로 변함.
    //2. 내가 코드를 고칠 수 없는 외부라이브러리에는 적용하지 못함.

}
