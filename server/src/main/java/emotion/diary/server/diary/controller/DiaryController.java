package emotion.diary.server.diary.controller;


import emotion.diary.server.diary.dto.DiaryDTO;
import emotion.diary.server.diary.entity.Diary;
import emotion.diary.server.diary.entity.DiaryImage;
import emotion.diary.server.diary.mapper.DiaryMapper;
import emotion.diary.server.diary.service.DiaryService;
import jakarta.validation.constraints.NotNull;
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

        String memberId = principal.getName();
        diaryService.createDiary(diaryMapper.diaryDTOPostToDiary(diaryDTOPost), diaryImages, memberId);
        return new ResponseEntity(HttpStatus.CREATED);
    }



    @PutMapping(value="/api/diarys/{diary-id}")
    public ResponseEntity putDiary(@RequestPart(value = "diary-put-dto") DiaryDTO.Put diaryDTOPut,
                                   @RequestPart(value = "diary-images", required = false) MultipartFile[] diaryImages,
                                   @RequestParam(value = "delete-image-ids", required = false, defaultValue = "") Long[] deleteImageIds,
                                   @PathVariable("diary-id") Long diaryId) throws IOException {

        //! 수정 요청 유저와 일기 작석자가 동일한지 검증 필요
        diaryImages = diaryImages == null ? new MultipartFile[0] : diaryImages; //값이 아예 안 넘어 와 null 경우 대비
        Diary modifyDiary = diaryMapper.diaryDTOPutToDiary(diaryDTOPut);
        diaryService.modifyDiary(modifyDiary, diaryId); //기본 데이터 수정
        diaryService.modifyDiaryImage(diaryId, diaryImages, deleteImageIds); //이미지 관련 수정
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value="/api/diarys/{diary-id}")
    public ResponseEntity deleteDiary(@PathVariable("diary-id") Long diaryId){
        diaryService.removeDiary(diaryId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/api/diarys/{diary-id}")
    public ResponseEntity getDiary(@PathVariable("diary-id") Long diaryId){
        Diary findDiary = diaryService.findDiary(diaryId);
        List<String> urls = findDiary.getDiaryImageList().stream().map(DiaryImage::getUrl).collect(Collectors.toList());
        return new ResponseEntity(diaryMapper.diaryToDiaryDTOResponse(findDiary,urls), HttpStatus.OK);
    }

    @GetMapping(value = "/api/diarys")
    public ResponseEntity getDiaryAll(
            @RequestParam(name="page", defaultValue = "1") @Positive Integer page,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size,
            @RequestParam(name = "date") String date,
            Principal principal){
        Page<Diary> diaryPageData = diaryService.findAllDiary(page, size, date, principal.getName());
        return new ResponseEntity(
                diaryMapper.diaryDTOResponseListToDiaryPageDTO(
                        diaryPageData.getTotalElements(),
                        diaryMapper.diaryPageToDiaryDTOResponseList(diaryPageData.getContent())
                )
                ,HttpStatus.OK);
    }


    @GetMapping(value = "/")
    public ResponseEntity test(){

        System.out.println("성공");
        return new ResponseEntity("성공입니다",HttpStatus.OK);
    }



}
