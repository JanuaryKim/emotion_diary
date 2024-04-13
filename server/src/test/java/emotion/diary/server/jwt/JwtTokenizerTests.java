package emotion.diary.server.jwt;

import emotion.diary.server.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = { //JwtTokenizer만 빈으로 등록
        JwtTokenizer.class
})
public class JwtTokenizerTests {

    @Autowired
    JwtTokenizer jwtTokenizer;

    @Autowired
    ApplicationContext ac;

    @BeforeEach
    void should_PrintToRegisteredBean_Try(){
        String[] beans = ac.getBeanDefinitionNames();

        for (String bean: beans) {
            System.out.println("bean: " + bean);
        }
    }

    @Test
    void should_PassVerifyJWT_When_Try(){

//        //given
//        Member member = Member.builder().memberId(1L).email("january@google.com").build();
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("memberId", member.getMemberId());
//        claims.put("memberEmail", member.getEmail());
//        String subject = "access_token";
//        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
//
//        //when
//        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, jwtTokenizer.getSecretKey());
//
//        //then
//        Map<String, Object> getClaim = jwtTokenizer.getClaims(accessToken, jwtTokenizer.getSecretKey()).getBody();
//        assertEquals(getClaim.get("memberEmail"), member.getEmail());
    }
}
