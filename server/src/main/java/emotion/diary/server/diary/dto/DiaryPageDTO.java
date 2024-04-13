package emotion.diary.server.diary.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryPageDTO {

    private Long diaryTotalCount;

    private List<DiaryDTO.Response> diaryList;
}
