package emotion.diary.server.diary.dto;

import lombok.*;
import java.util.List;

public class DiaryDTO {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post{

        private String regDate;

        private short emotion;

        private String content;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Put{

        private String regDate;

        private short emotion;

        private String content;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{

        private Long diaryId;

        private String regDate;

        private short emotion;

        private String content;

        private List<DiaryImageDTO.Response> images;
    }
}
