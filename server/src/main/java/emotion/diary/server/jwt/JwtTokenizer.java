package emotion.diary.server.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Getter
@Component
public class JwtTokenizer {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;


    // access 토큰 생성
    public String
    generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String secretKey) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    // refresh 토큰 생성
    public String generateRefreshToken(String subject, Date expiration, String secretKey) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    // 검증 후 Claims 반환
    public Jws<Claims> getClaims(String jws, String secretKey) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }

    // 토큰 유효기간 계산
    private Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    public Date getAccessTokenExpiration(){
        return getTokenExpiration(this.accessTokenExpirationMinutes);
    }

    public Date getRefreshTokenExpiration(){
        return getTokenExpiration(this.getRefreshTokenExpirationMinutes());
    }
}
