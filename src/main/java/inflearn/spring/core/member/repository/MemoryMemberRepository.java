package inflearn.spring.core.member.repository;

import inflearn.spring.core.member.Member;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(long memberId) {
        return store.get(memberId);
    }
}
