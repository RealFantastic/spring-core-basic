package inflearn.spring.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*ERROR 발생*/
//ScopeNotActiveException: Error creating bean with name 'myLogger': Scope 'request' is not active for the current thread; consider defining a scoped proxy for this bean if you intend to refer to it from a singleton
//MyLogger의 scope는 request이다. 즉, request가 들어올 때 스프링 컨테이너가 bean을 생성한다는 얘기.
//최초에 서버를 실행할 때에는 request가 없기 때문에 현재 스레드에서 활성되지 않았다는 오류를 발생시키는 것.
//Provider를 사용하여 해결 가능.

@Component
@Scope(value="request")
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
