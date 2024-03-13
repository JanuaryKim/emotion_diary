package emotion.diary.server.diary.entity;

import emotion.diary.server.auditable.BaseTimeEntity;
import emotion.diary.server.member.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    private LocalDateTime reg_date;

    private short emotion;

    private String context;

}
