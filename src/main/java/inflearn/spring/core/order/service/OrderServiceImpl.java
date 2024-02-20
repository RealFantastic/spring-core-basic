package inflearn.spring.core.order.service;

import inflearn.spring.core.discount.DiscountPolicy;
import inflearn.spring.core.discount.FixDiscountPolicy;
import inflearn.spring.core.discount.RateDiscountPolicy;
import inflearn.spring.core.member.Member;
import inflearn.spring.core.member.repository.MemberRepository;
import inflearn.spring.core.member.repository.MemoryMemberRepository;
import inflearn.spring.core.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    //이제 더 이상 OrderServiceImpl이 memberRepository와 discountPolicy에 어떤 구현체가 들어오는지 알 필요가 없다.
    //해당 객체의 생성은 외부의 AppConfig에서 담당하고, OrderService는 AppConfig에서 생성해준 객체만 주입 받으면 되기 때문.
//    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy;
//
    private final MemberRepository memberRepository; //final 사용시 객체 생성시점 or 필드 자체에 초기화를 해야하기 때문에 생성자 메서드에서 해당 필드의 주입을 까먹어도 컴파일 에러로 알려줌.
    private final DiscountPolicy discountPolicy;

    /*수정자 주입 테스트용*/
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    //싱글톤 여부 테스트용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
