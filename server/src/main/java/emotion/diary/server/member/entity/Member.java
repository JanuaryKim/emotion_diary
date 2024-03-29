package emotion.diary.server.member.entity;

import emotion.diary.server.auditable.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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

}
