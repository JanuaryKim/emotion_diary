package emotion.diary.server.member.service;

import emotion.diary.server.member.entity.Member;
import emotion.diary.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(String memberId, String memberEmail, String socialKind){
        return memberRepository.save(Member.builder().memberId(memberId).email(memberEmail).reg_kind(socialKind).memberRoles(List.of("USER")).build());
    }

    public void updateMember(String memberId,String memberEmail){
        verifyExistsMember(memberId).setEmail(memberEmail);
    }
    public Optional<Member> findMember(String memberId){
        return memberRepository.findById(memberId);
    }

    private Member verifyExistsMember(String memberId){
        return memberRepository.findById(memberId).orElseThrow(()-> new RuntimeException());
    }


}
