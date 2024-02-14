package inflearn.spring.core.order;

import inflearn.spring.core.AppConfig;
import inflearn.spring.core.member.Grade;
import inflearn.spring.core.member.Member;
import inflearn.spring.core.member.service.MemberService;
import inflearn.spring.core.order.service.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    //주문 테스트를 위한 메인 메서드
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
//        MemberService memberService = new MemberServiceImpl(null);
//        OrderService orderService = new OrderServiceImpl(null, null);

//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = context.getBean("memberService", MemberService.class);
        OrderService orderService = context.getBean("orderService", OrderService.class);

        //회원정보 생성
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        //회원가입
        memberService.join(member);
        //주문
        Order order = orderService.createOrder(memberId, "상품A", 20000);

        System.out.println("order = " + order.toString());
        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}
