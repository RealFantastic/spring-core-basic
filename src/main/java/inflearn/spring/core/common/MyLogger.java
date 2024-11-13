package inflearn.spring.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*ERROR 발생*/
//ScopeNotActiveException: Error creating bean with name 'myLogger': Scope 'request' is not active for the current thread; consider defining a scoped proxy for this bean if you intend to refer to it from a singleton
//MyLogger의 scope는 request이다. 즉, request가 들어올 때 스프링 컨테이너가 bean을 생성한다는 얘기.
//최초에 서버를 실행할 때에는 request가 없기 때문에 현재 스레드에서 활성되지 않았다는 오류를 발생시키는 것.
//Provider를 사용하여 해결 가능.

/*proxyMode*/
// - 가짜 프록시 클래스를 다른 빈에 미리 주입
// - 적용 대상이 클래스라면 TARGET_CLASS 사용
// - 적용 대상이 인터페이스라면 INTERFACES 사용

//myLogger = class inflearn.spring.core.common.MyLogger$$SpringCGLIB$$0
//CGLIB 라이브러리로 MyLogger를 상속받은 가짜 프록시 객체를 만들어 주입
//원본 클래스를 상속받아서 만들어진 프록시 객체이기 때문에 클라이언트 입장에선 이게 원본인지 아닌지 모르게 동일하게 사용 가능(다형성)
//프록시 객체는 실제 request scope와는 관계없음.
//핵심은 Provider를 사용하건, 프록시를 사용하건 진짜 객체 조회를 꼭 필요한 시점까지 지연처리한다는 점 *******

//주의점
// - 싱글톤을 사용하는 것처럼 보이지만 다르게 동작하기 때문에 주의해서 사용해야한다.
// - request와 같은 특별한 scope는 필요한 곳에 최소화해서 사용하는 것이 유지보수에 좋다.
@Component
@Scope(value="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
    private String uuid;
    @Setter
    private String requestURL;

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        this.uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean created ==> " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close ==> " + this);
    }
}
