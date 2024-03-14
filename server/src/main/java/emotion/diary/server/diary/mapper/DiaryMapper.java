package emotion.diary.server.diary.mapper;

import emotion.diary.server.diary.dto.DiaryDTO;
import emotion.diary.server.diary.entity.Diary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiaryMapper {

    @Mapping(target = "regDate", source = "regDate", dateFormat = "yyyy-MM-dd HH:mm") //String 인 날짜 데이터 -> LocalDateTime
    public Diary diaryDTOPostToDiary(DiaryDTO.Post postDTO);

    @Mapping(target = "regDate", source = "regDate", dateFormat = "yyyy-MM-dd HH:mm") //String 인 날짜 데이터 -> LocalDateTime
    public Diary diaryDTOPutToDiary(DiaryDTO.Put putDTO);

    @Mapping(target = "regDate", source = "regDate", dateFormat = "yyyy-MM-dd HH:mm") //String 인 날짜 데이터 -> LocalDateTime
    public DiaryDTO.Response diaryToDiaryDTOResponse(Diary dto);
}
