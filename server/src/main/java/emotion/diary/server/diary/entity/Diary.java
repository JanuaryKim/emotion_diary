package emotion.diary.server.diary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import emotion.diary.server.auditable.BaseTimeEntity;
import emotion.diary.server.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "diary",  orphanRemoval = true)
    private List<DiaryImage> diaryImageList;
}
