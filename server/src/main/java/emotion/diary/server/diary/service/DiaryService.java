package emotion.diary.server.diary.service;

import emotion.diary.server.diary.entity.Diary;
import emotion.diary.server.diary.entity.DiaryImage;
import emotion.diary.server.diary.repository.DiaryImageRepository;
import emotion.diary.server.diary.repository.DiaryRepository;
import emotion.diary.server.member.entity.Member;
import emotion.diary.server.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Transactional
@RequiredArgsConstructor
@Service
public class DiaryService {

    @Value("${path.file.home}")
    private String homePath;

    @Value("${path.file.image.diary}")
    private String diaryImagePath;

    private final DiaryRepository diaryRepository;
    private final DiaryImageRepository diaryImageRepository;

    public void createDiary(Diary diary, MultipartFile[] diaryImages) throws IOException {
        Member m = Member.builder().memberId(1L).build();
        diary.setMember(m);

        Diary savedDiary = diaryRepository.save(diary);

        for(int i = 0; i < diaryImages.length; i++){
            String url = saveDiaryImage(diaryImages[i], savedDiary.getDiaryId());
            diaryImageRepository.save(DiaryImage.builder().diary(savedDiary).url(url).build());
        }
    }

    private String saveDiaryImage(MultipartFile image, Long diaryId) throws IOException {

        String middlePath = "/" + diaryImagePath + diaryId;
        String totalPath = homePath + middlePath;

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        FileUtil.saveFile(totalPath, fileName, image);

        String dbSaveUrl = middlePath + "/"+ fileName;
        return dbSaveUrl;
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
