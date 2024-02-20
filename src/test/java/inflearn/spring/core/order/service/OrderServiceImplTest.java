package inflearn.spring.core.order.service;

import inflearn.spring.core.discount.FixDiscountPolicy;
import inflearn.spring.core.member.Grade;
import inflearn.spring.core.member.Member;
import inflearn.spring.core.member.repository.MemoryMemberRepository;
import inflearn.spring.core.order.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    @Test
    void createOrder(){
        //이런 단위 테스트단에서는 스프링을 사용하지 않는 순수한 자바 코드로 테스트를 작성하게 된다.
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "memberA", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());//생성자 주입을 사용할 경우 객체를 생성하려고 할 때 의존성 주입을 강제할 수 있다.
        Order order = orderService.createOrder(1L, "itemA", 10000);

        assertThat(order.getDiscountPrice()).isEqualTo(1000 );

        /*수정자 주입 방식을 사용할 경우
        * 테스트 코드를 작성할 때 해당 인스턴스를 생성할 때 무슨 의존성이 필요한지 알 방법이 없다.
        * 이로 인해 테스트 실행 도중 주입 받지 못한 의존성으로 인해 NPE 발생 우려가 있다.
        *
        * 되도록 에러는 컴파일 단계에서 확인할 수 있는 에러가 좋은 에러이기 때문에
        * 생성자 주입을 사용하여 의존관계 주입을 해야한다는 것을 컴파일 단계에 알 수 있게 해야한다.(IDE 빨간줄)
        * */
    }
}