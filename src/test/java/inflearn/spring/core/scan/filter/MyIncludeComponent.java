package inflearn.spring.core.scan.filter;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE}) //클래스 레벨에 사용 가능
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface MyIncludeComponent {
}
