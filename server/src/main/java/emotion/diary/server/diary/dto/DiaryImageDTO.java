package emotion.diary.server.diary.dto;

import lombok.*;

public class DiaryImageDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{

        private Long diaryImageId;

        private String originalFileName;

        private String url;
    }


}
