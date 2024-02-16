package inflearn.spring.core;

import inflearn.spring.core.member.repository.MemberRepository;
import inflearn.spring.core.member.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        //AppConfig 클래스와 테스트용으로 만든 AppConfig들을 컴포넌트 스캔 대상에서 제외하기 위해 작성, 일반적으로는 Config도 등록함.
)
public class AutoAppConfig {
    @Bean("memoryMemberRepository")
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
