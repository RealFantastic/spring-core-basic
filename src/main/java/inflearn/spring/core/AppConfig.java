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
    //내가 생각한 호출 순서 및 개수 : 총 5번 호출됨, memberRepository  3번
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.memberRepository
    //call AppConfig.orderService
    //call AppConfig.memberRepository

    //실제 호출된 순서 및 개수 : 총 3번 호출됨, 각각 1번씩
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.orderService

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
