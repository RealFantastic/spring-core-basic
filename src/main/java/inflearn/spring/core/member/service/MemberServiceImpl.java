package inflearn.spring.core.member.service;

import inflearn.spring.core.member.Member;
import inflearn.spring.core.member.repository.MemberRepository;

public class MemberServiceImpl implements MemberService{
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    /**
     * DIP 준수를 위해 생성자 추가 및 new 삭제
     * MemberService는 MemberRepository가 메모리 저장인지 DB 저장인지 알 필요가 없다.
     */
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //싱글톤 여부 테스트용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
