package com.project.voda.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // swagger는 security 무시
    @Bean
    public WebSecurityCustomizer configure() {
        return web -> { web.ignoring()
                .antMatchers(
                        "/v2/api-docs/**"
                        , "/swagger.json"
                        , "/swagger-ui.html/**"
                        , "/swagger-resources/**"
                        , "/webjars/**"
                );
        };
    }

    // HttpSecurity 설정
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable() // security 기본 로그인 사용 X
                // cors허용
                .cors().and().cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable() // csrf 보안 설정 비활성화
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 X
                .and()
                .authorizeRequests() // 보호된 리소스 URI에 접근할 수 있는 권한 설정
                // 로그인, 회원가입 접근 허용
                .antMatchers( "/**/signup").permitAll()
                // swagger 접근 허용
                .antMatchers(
                        "/v2/api-docs/**"
                        , "/swagger.json"
                        , "/swagger-ui.html/**"
                        , "/swagger-resSources/**"
                        , "/webjars/**"
                ).permitAll();
                /**
                 * swagger 테스트 할때는 밑줄을 주석 처리하면 됩니다.
                 */
//                .anyRequest().authenticated(); // 다른 경로는 인증필요

        return http.build();
    }

    // CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

//         configuration.setAllowedOriginPatterns();
        // 추후 경로에 맞게 수정 필요
        configuration.addAllowedOrigin("http://localhost:8081");
//        configuration.addAllowedOrigin("http://3.34.141.63:3000");
//        configuration.addAllowedOrigin("http://3.34.141.63:443");
//        configuration.addAllowedOrigin("https://ggati.site");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        //configuration.setExposedHeaders(""); //

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
