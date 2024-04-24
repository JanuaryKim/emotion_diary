package emotion.diary.server.diary.controller;


import emotion.diary.server.diary.dto.DiaryDTO;
import emotion.diary.server.diary.dto.DiaryPageDTO;
import emotion.diary.server.diary.entity.Diary;
import emotion.diary.server.diary.entity.DiaryImage;
import emotion.diary.server.diary.mapper.DiaryMapper;
import emotion.diary.server.diary.service.DiaryService;
import emotion.diary.server.exception.BusinessException;
import emotion.diary.server.exception.ExceptionCode;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;
    private final DiaryMapper diaryMapper;

    @PostMapping(value="/api/diarys")
        public ResponseEntity postDiary(@RequestPart(value = "diary-post-dto") DiaryDTO.Post diaryDTOPost,
                                        @RequestPart(value = "diary-images",required = false) MultipartFile[] diaryImages,
                                         Principal principal
                                        ) throws IOException {

        //삭제
        if(diaryImages == null)
            throw new BusinessException(ExceptionCode.NOT_EXISTS_DIARY);

        String memberId = principal.getName();
        diaryImages = diaryImages == null ? new MultipartFile[0] : diaryImages; //값이 아예 안 넘어 와 null 경우 대비
        diaryService.createDiary(diaryMapper.diaryDTOPostToDiary(diaryDTOPost), diaryImages, memberId);
        return new ResponseEntity(HttpStatus.CREATED);
    }



    @PutMapping(value="/api/diarys/{diary-id}")
    public ResponseEntity putDiary(@RequestPart(value = "diary-put-dto") DiaryDTO.Put diaryDTOPut,
                                   @RequestPart(value = "diary-images", required = false) MultipartFile[] diaryImages,
                                   @PathVariable("diary-id") Long diaryId) throws IOException {

        //! 수정 요청 유저와 일기 작석자가 동일한지 검증 필요
        diaryImages = diaryImages == null ? new MultipartFile[0] : diaryImages; //값이 아예 안 넘어 와 null 경우 대비
        Diary modifyDiary = diaryMapper.diaryDTOPutToDiary(diaryDTOPut);
        diaryService.modifyDiary(modifyDiary, diaryId); //기본 데이터 수정
        diaryService.modifyDiaryImage(diaryId, diaryImages); //이미지 관련 수정
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value="/api/diarys/{diary-id}")
    public ResponseEntity deleteDiary(@PathVariable("diary-id") Long diaryId){
        diaryService.removeDiary(diaryId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/api/diarys/{diary-id}")
    public ResponseEntity getDiary(@PathVariable("diary-id") Long diaryId,
                                   Principal principal){
        Diary findDiary = diaryService.findDiary(diaryId, principal.getName());
        List<String> urls = findDiary.getDiaryImageList().stream().map(DiaryImage::getUrl).collect(Collectors.toList());
        return new ResponseEntity(diaryMapper.diaryToDiaryDTOResponse(findDiary), HttpStatus.OK);
    }

    @GetMapping(value = "/api/diarys")
    public ResponseEntity getDiaryAll(
            @RequestParam(name="page", defaultValue = "1") @Positive Integer page,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size,
            @RequestParam(name = "date") String date,
            @RequestParam(name = "sort", defaultValue = "latest") String sort,
            @RequestParam(name = "emotion", defaultValue = "all") String emotion,

            Principal principal){
        Page<Diary> diaryPageData = diaryService.findAllDiary(page, size, date, principal.getName(), sort, emotion);
        DiaryPageDTO response = diaryMapper.diaryDTOResponseListToDiaryPageDTO(
                diaryPageData.getTotalElements(),
                diaryMapper.diaryPageToDiaryDTOResponseList(diaryPageData.getContent())
        );
        return new ResponseEntity(response
                ,HttpStatus.OK);
    }


    @GetMapping(value = "/")
    public ResponseEntity test(){

        System.out.println("성공");
        return new ResponseEntity("성공입니다",HttpStatus.OK);
    }



}
