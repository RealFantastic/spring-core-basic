package inflearn.spring.core.member.service;

import inflearn.spring.core.member.Member;

public interface MemberService {
    void join(Member member);

    Member findMember(Long memberId);
}
