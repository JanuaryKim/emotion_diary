package emotion.diary.server.diary.service;

import emotion.diary.server.diary.entity.Diary;
import emotion.diary.server.diary.entity.DiaryImage;
import emotion.diary.server.diary.repository.DiaryImageRepository;
import emotion.diary.server.diary.repository.DiaryRepository;
import emotion.diary.server.member.entity.Member;
import emotion.diary.server.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class DiaryService {

    @Value("${path.file.home}")
    private String homePath;

    @Value("${path.file.image.diary}")
    private String diaryImagePath;

    @Value("${image.upload.max-count}")
    private Integer imageUploadMaxCnt;

    private final DiaryRepository diaryRepository;
    private final DiaryImageRepository diaryImageRepository;

    public void createDiary(Diary diary, MultipartFile[] diaryImages, String memberId) throws IOException {

        diary.setMember(Member.builder().memberId(memberId).build());
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

    public void modifyDiaryImage(Long diaryId, MultipartFile[] diaryImages, Long[] deleteImageIds) throws IOException {
        Diary diary = verifyExistsDiary(diaryId);

        //애초에 요청이 잘못들어 온 경우 확인
        if(
                imageUploadMaxCnt < (diary.getDiaryImageList().size() - deleteImageIds.length + diaryImages.length) ||
                0 > (diary.getDiaryImageList().size() - deleteImageIds.length + diaryImages.length)
        )
            throw new RuntimeException();

        //기존 이미지 삭제
        removeDiaryImageFile(removeDiaryImageData(deleteImageIds));

        //추가 이미지 등록
        for(int i = 0; i < diaryImages.length; i++){
            String url = saveDiaryImage(diaryImages[i], diary.getDiaryId());
            diaryImageRepository.save(DiaryImage.builder().diary(diary).url(url).build());
        }
    }

    private List<String> removeDiaryImageData(Long[] deleteImageIds){
        List<String> paths = new ArrayList<>();
        for(Long id : deleteImageIds){
            DiaryImage removeDiaryImage = verifyExistsDiaryImage(id);
            diaryImageRepository.delete(removeDiaryImage);
            paths.add(removeDiaryImage.getUrl());
        }
        return paths;
    }

    private void removeDiaryImageFile(List<String> filePaths) throws IOException {
        for(String path : filePaths){
            String totalPath = homePath + path;
            FileUtil.deleteFile(totalPath);
        }
    }


    public void removeDiary(Long removeDiaryId){

        Diary removeDiary = verifyExistsDiary(removeDiaryId);
        diaryRepository.delete(removeDiary);
    }

    public Diary findDiary(Long getDiaryId, String memberId){
        Diary findDiary = verifyExistsDiary(getDiaryId);
        verifyPermissionCheck(findDiary, memberId);
        return findDiary;
    }

    private void verifyPermissionCheck(Diary diary, String memberId) {

        if(!diary.getMember().getMemberId().equals(memberId)) throw new RuntimeException();
    }

    private Diary verifyExistsDiary(Long diaryId){
        return diaryRepository.findById(diaryId).orElseThrow(()-> new RuntimeException());
    }

    private DiaryImage verifyExistsDiaryImage(Long diaryImageId){
        return diaryImageRepository.findById(diaryImageId).orElseThrow(()-> new RuntimeException());
    }

    public Page<Diary> findAllDiary(Integer page, Integer size, String strDate, String memberId, String sort, String emotion){
        LocalDate date = LocalDate.parse(strDate+"-01"); //기준 날짜, 클라로부터 년-월 까지 넘어오기 때문에 임의의 1일을 설정,
        LocalDate firstDate = date.withDayOfMonth(1); //해당 달의 첫째날
        LocalDate lastDate = date.withDayOfMonth(date.lengthOfMonth()); //해당 달의 마지막날

        LocalDateTime fromDate = LocalDateTime.of(firstDate,LocalTime.of(0,0,0)); //LocalDate -> LocalDateTime
        LocalDateTime toDate = LocalDateTime.of(lastDate,LocalTime.of(23,59,59));



        Page<Diary> diaryPage = null;
        if(emotion.equals("good")){
            diaryPage = diaryRepository.findAllByRegDateBetweenAndMemberAndEmotionLessThanEqual(
                    fromDate,
                    toDate,
                    PageRequest.of(page - 1,
                            size,
                            Sort.by(sort.equals("latest") ? Sort.Direction.DESC : Sort.Direction.ASC, "regDate").and(Sort.by(Sort.Direction.DESC, "diaryId"))
                    ),
                    Member.builder().memberId(memberId).build(),3

                    );
        }else if(emotion.equals("bad")){
            diaryPage = diaryRepository.findAllByRegDateBetweenAndMemberAndEmotionGreaterThan(
                    fromDate,
                    toDate,
                    PageRequest.of(page - 1,
                            size,
                            Sort.by(sort.equals("latest") ? Sort.Direction.DESC : Sort.Direction.ASC, "regDate").and(Sort.by(Sort.Direction.DESC, "diaryId"))
                    ),
                    Member.builder().memberId(memberId).build(),3

            );
        }else{ //all
            diaryPage = diaryRepository.findAllByRegDateBetweenAndMember(
                    fromDate,
                    toDate,
                    PageRequest.of(page - 1,
                            size,
                            Sort.by(sort.equals("latest") ? Sort.Direction.DESC : Sort.Direction.ASC, "regDate").and(Sort.by(Sort.Direction.DESC, "diaryId"))
                    ),
                    Member.builder().memberId(memberId).build()
            );
        }

        return diaryPage;
    }

}
