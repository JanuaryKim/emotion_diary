package emotion.diary.server.java;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class BasicJavaTests {

    @Test
    void dateTest(){
        //테스트
        Calendar cal = Calendar.getInstance();

        Date today = cal.getTime();

        // Calendar 객체에 하루를 더하기
        cal.add(Calendar.DATE, 1);


        // 내일 날짜를 Date 객체로 변환
        Date tomorrow = cal.getTime();

        // 결과 출력
//        System.out.println(today.);
//        System.out.println(tomorrow);
    }

    @Test
    void dateTest2(){

        String regDate = "2024-01-22 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // String을 LocalDate 객체로 변환
        LocalDate localDate = LocalDate.parse(regDate, formatter);

        // LocalDate 객체를 Date 객체로 변환
        Date fromDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        String regDate2 = "2024-01-23 00:00:00";
        localDate = LocalDate.parse(regDate2, formatter);
        Date toDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        System.out.println(fromDate);
        System.out.println(toDate);
    }
}
