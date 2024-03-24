package emotion.diary.server.diary.entity;

import emotion.diary.server.auditable.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryImageId;

    @ManyToOne
    @JoinColumn(name="DIARY_ID")
    private Diary diary;

    private String url;

}
