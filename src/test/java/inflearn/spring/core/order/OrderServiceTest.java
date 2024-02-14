package inflearn.spring.core.order;

import inflearn.spring.core.AppConfig;
import inflearn.spring.core.member.Grade;
import inflearn.spring.core.member.Member;
import inflearn.spring.core.member.service.MemberService;
import inflearn.spring.core.member.service.MemberServiceImpl;
import inflearn.spring.core.order.service.OrderService;
import inflearn.spring.core.order.service.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    @DisplayName("주문 생성 테스트")
    void createOrder(){
        //given
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);

        memberService.join(member);
        //when
        Order order = orderService.createOrder(memberId, "상품A", 10000);

        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }
}
