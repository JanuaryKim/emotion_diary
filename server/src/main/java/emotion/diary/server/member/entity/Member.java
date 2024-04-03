package emotion.diary.server.member.entity;

import emotion.diary.server.auditable.BaseTimeEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseTimeEntity {

    @Id
    private String memberId;

    private String email;

    private String reg_kind;

    //@ElementCollection 별도의 테이블에 데이터를 저장함.
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> memberRoles;

}
