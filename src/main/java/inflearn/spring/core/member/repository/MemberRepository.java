package inflearn.spring.core.member.repository;

import inflearn.spring.core.member.Member;

public interface MemberRepository {
    void save(Member member);
    Member findById(long memberId);
}
