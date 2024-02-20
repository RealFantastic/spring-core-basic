package inflearn.spring.core.autowired.allbean;

import inflearn.spring.core.AutoAppConfig;
import inflearn.spring.core.discount.DiscountPolicy;
import inflearn.spring.core.member.Grade;
import inflearn.spring.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AllBeanTest {

    @Test
    void findAllbean(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        int discountPrice = discountService.discount(memberA, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(memberA, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService{
        private final Map<String, DiscountPolicy> policyMap; //Map으로 DiscountPolicy 타입의 모든 빈을 주입받음.
        private final List<DiscountPolicy> policyList;

        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
            //policyMap = {fixDiscountPolicy=inflearn.spring.core.discount.FixDiscountPolicy@4c060c8f, rateDiscountPolicy=inflearn.spring.core.discount.RateDiscountPolicy@40620d8e}

            System.out.println("policyList = " + policyList);
            //policyList = [inflearn.spring.core.discount.FixDiscountPolicy@4c060c8f, inflearn.spring.core.discount.RateDiscountPolicy@40620d8e]

        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}
