package emotion.diary.server.diary.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import java.util.List;

public class DiaryDTO {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post{

        @NotBlank(message = "날짜는 Null, 빈문자열 혹은 공백일 수 없습니다 ")
        private String regDate;

        @NotNull(message = "감정 Null 일 수 없습니다")
        @Min(1)
        @Max(5)
        private Short emotion;

        @NotBlank(message = "날짜는 Null, 빈문자열 혹은 공백일 수 없습니다 ")
        @Length(max = 150, min = 1)
        private String content;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Put{

        @NotBlank(message = "날짜는 Null, 빈문자열 혹은 공백일 수 없습니다 ")
        private String regDate;

        @NotNull(message = "감정 Null 일 수 없습니다")
        @Min(1)
        @Max(5)
        private Short emotion;


        @NotBlank(message = "날짜는 Null, 빈문자열 혹은 공백일 수 없습니다 ")
        @Length(max = 150, min = 1)
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

        private Short emotion;

        private String content;

        private List<DiaryImageDTO.Response> images;
    }
}
