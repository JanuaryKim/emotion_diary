package emotion.diary.server.diary.mapper;

import emotion.diary.server.diary.dto.DiaryDTO;
import emotion.diary.server.diary.dto.DiaryPageResponseDTO;
import emotion.diary.server.diary.entity.Diary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiaryMapper {

    @Mapping(target = "regDate", source = "regDate", dateFormat = "yyyy-MM-dd HH:mm") //String 인 날짜 데이터 -> LocalDateTime
    public Diary diaryDTOPostToDiary(DiaryDTO.Post postDTO);

    @Mapping(target = "regDate", source = "regDate", dateFormat = "yyyy-MM-dd HH:mm") //String 인 날짜 데이터 -> LocalDateTime
    public Diary diaryDTOPutToDiary(DiaryDTO.Put putDTO);

    @Mapping(target = "regDate", source = "dto.regDate", dateFormat = "yyyy-MM-dd HH:mm") //String 인 날짜 데이터 -> LocalDateTime
    @Mapping(target = "images", source = "dto.diaryImageList")
    public DiaryDTO.Response diaryToDiaryDTOResponse(Diary dto);

    public List<DiaryDTO.Response> diaryPageToDiaryDTOResponseList(List<Diary> diaryList);

    default DiaryPageResponseDTO diaryDTOResponseListToDiaryPageDTO(Long diaryTotalCount, List<DiaryDTO.Response> diaryDTOResponseList){

        return DiaryPageResponseDTO.builder().diaryTotalCount(diaryTotalCount).diaryList(diaryDTOResponseList).build();
    }
}
