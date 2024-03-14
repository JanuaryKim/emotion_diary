package emotion.diary.server.diary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import emotion.diary.server.auditable.BaseTimeEntity;
import emotion.diary.server.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    private LocalDateTime regDate;

    private short emotion;

    private String content;

}
