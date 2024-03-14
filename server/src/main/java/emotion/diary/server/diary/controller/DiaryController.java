package emotion.diary.server.diary.controller;

import emotion.diary.server.diary.dto.DiaryDTO;
import emotion.diary.server.diary.entity.Diary;
import emotion.diary.server.diary.mapper.DiaryMapper;
import emotion.diary.server.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class DiaryController {


    private final DiaryService diaryService;
    private final DiaryMapper diaryMapper;

    @PostMapping(value="/api/diarys")
        public ResponseEntity postDiary(@RequestPart(value = "diary-post-dto") DiaryDTO.Post diaryDTOPost){
        diaryService.createDiary(diaryMapper.diaryDTOPostToDiary(diaryDTOPost));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value="/api/diarys/{diary-id}")
    public ResponseEntity putDiary(@RequestPart(value = "diary-put-dto") DiaryDTO.Put diaryDTOPut,
                                   @PathVariable("diary-id") Long diaryId){
        Diary modifyDiary = diaryMapper.diaryDTOPutToDiary(diaryDTOPut);
        diaryService.modifyDiary(modifyDiary, diaryId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value="/api/diarys/{diary-id}")
    public ResponseEntity deleteDiary(@PathVariable("diary-id") Long diaryId){
        diaryService.removeDiary(diaryId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/api/diarys/{diary-id}")
    public ResponseEntity getDiary(@PathVariable("diary-id") Long diaryId){
        return new ResponseEntity(diaryMapper.diaryToDiaryDTOResponse(diaryService.findDiary(diaryId)), HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity test(){

        System.out.println("성공");
        return new ResponseEntity("성공입니다",HttpStatus.OK);
    }

}
