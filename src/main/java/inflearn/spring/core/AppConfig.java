package inflearn.spring.core;

import inflearn.spring.core.discount.DiscountPolicy;
import inflearn.spring.core.discount.FixDiscountPolicy;
import inflearn.spring.core.discount.RateDiscountPolicy;
import inflearn.spring.core.member.repository.MemberRepository;
import inflearn.spring.core.member.repository.MemoryMemberRepository;
import inflearn.spring.core.member.service.MemberService;
import inflearn.spring.core.member.service.MemberServiceImpl;
import inflearn.spring.core.order.service.OrderService;
import inflearn.spring.core.order.service.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
