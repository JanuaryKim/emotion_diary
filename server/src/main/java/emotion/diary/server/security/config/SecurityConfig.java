package emotion.diary.server.security.config;

import emotion.diary.server.jwt.JwtTokenizer;
import emotion.diary.server.security.filter.JwtVerificationFilter;
import emotion.diary.server.security.handler.AccessDeniedHandlerImpl;
import emotion.diary.server.security.handler.AuthenticationEntryPointImpl;
import emotion.diary.server.security.handler.OauthSuccessHandler;
import emotion.diary.server.security.oauth2.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final OauthSuccessHandler oauthSuccessHandler;
    private final OAuthService oauthService;
    private final JwtTokenizer jwtTokenizer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("script-src 'self'; object-src 'self';") //컨텐츠를 받아와도 되는 도메인 내용
                        )
                ) //csp 정책 지정
                .csrf(AbstractHttpConfigurer::disable) //csrf disable 안하면 변조 위험이 있는 Post, Put 같은 요청은 거부됨.
                .cors((cors) -> cors
                        .configurationSource(apiConfigurationSource())
                ) //cors 정책 지정
                .sessionManagement((s)->s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) //session 사용 안함
                .formLogin(AbstractHttpConfigurer::disable) //폼 로그인 사용 안함
                .httpBasic(AbstractHttpConfigurer::disable) //HTTP Basic 방식 사용 안함,
                .exceptionHandling((exceptionHandling) -> exceptionHandling //예외처리 설정
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()) //리소스에 대한 충분한 권한을 갖지 못한 유저가 리소스에 접근시 처리 핸들러 등록
                        .authenticationEntryPoint(new AuthenticationEntryPointImpl()) //미인증 유저가 인증이 필요한 리소스에 접근시 처리 핸들러 등록
                )
                .addFilterAfter(new JwtVerificationFilter(jwtTokenizer), OAuth2LoginAuthenticationFilter.class)
//                .authorizeHttpRequests(authorize-> authorize.anyRequest().permitAll()) //모든 요청 허용
                .authorizeHttpRequests(authorize-> {
                    authorize.requestMatchers("/api/**").hasRole("USER");
                    authorize.requestMatchers("/**").permitAll();
                })
                .oauth2Login((oauth)-> oauth.successHandler(oauthSuccessHandler).userInfoEndpoint((oauth2)-> oauth2.userService(oauthService))) //oauth로그인 활성화, 성공시 핸들러 등록, 성공한 유저의 EndPoint를 가지고 권한 설정을 위한 CustomService 등록, userInfoEndpoint는 service 등록을 위한 진입정 정도로 이해
                .build();
    }


    //cors 설정자
    CorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //ex: configuration.setAllowedOrigins(Arrays.asList("https://api.example.com"));
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Content-Type");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}