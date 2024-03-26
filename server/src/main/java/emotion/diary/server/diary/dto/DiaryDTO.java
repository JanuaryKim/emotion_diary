package emotion.diary.server.diary.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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

        private String regDate;

        private short emotion;

        private String content;

        private List<String> urls;
    }
}
