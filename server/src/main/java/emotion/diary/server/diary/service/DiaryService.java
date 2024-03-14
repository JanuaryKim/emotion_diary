package emotion.diary.server.diary.service;

import emotion.diary.server.diary.entity.Diary;
import emotion.diary.server.diary.repository.DiaryRepository;
import emotion.diary.server.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public void createDiary(Diary diary){
        Member m = Member.builder().memberId(1L).build();
        diary.setMember(m);
        diaryRepository.save(diary);
    }

    public void modifyDiary(Diary diary, Long modifyDiaryId) {
        Diary findDiary = verifyExistsDiary(modifyDiaryId);
        //수정 요청 유저와 소유 유저 동일 검증 필요
        findDiary.setContent(diary.getContent());
        findDiary.setEmotion(diary.getEmotion());
        findDiary.setRegDate(diary.getRegDate());
    }

    public void removeDiary(Long removeDiaryId){

        Diary removeDiary = verifyExistsDiary(removeDiaryId);
        diaryRepository.delete(removeDiary);
    }

    public Diary findDiary(Long getDiaryId){

        Diary findDiary = verifyExistsDiary(getDiaryId);
        return findDiary;
    }

    private Diary verifyExistsDiary(Long diaryId){
        return diaryRepository.findById(diaryId).orElseThrow(()-> new RuntimeException());
    }
}
