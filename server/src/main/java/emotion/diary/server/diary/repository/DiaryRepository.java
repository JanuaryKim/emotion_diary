package emotion.diary.server.diary.repository;

import emotion.diary.server.diary.entity.Diary;
import emotion.diary.server.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findAllByRegDateBetweenAndMember(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable, Member member);
    Page<Diary> findAllByRegDateBetweenAndMemberAndEmotionLessThanEqual(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable, Member member, Integer emotion); //good 감정 일기만
    Page<Diary> findAllByRegDateBetweenAndMemberAndEmotionGreaterThan(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable, Member member, Integer emotion); //bad 감정 일기만

}
