package com.project.voda.service;

import com.project.voda.domain.User;
import com.project.voda.dto.KakaoProfileDto;
import com.project.voda.dto.OAuthTokenDto;
import com.project.voda.dto.UserDto;
import com.project.voda.dto.UserSignUpRequestDto;
import com.project.voda.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Builder
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    
    // Email로 회원정보 가져오기
    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return UserDto.builder().user(user).build();
    }

    // 인가 code로 token 가져오기
    @Override
    public OAuthTokenDto tokenRequest(String code) {
        RestTemplate restTemplate = new RestTemplate();

        //HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "4afbb9b3768b16849eeb3117d5771293"); //clientId 는 프로퍼티에 정의해놨음
        body.add("client_secret", "ahySHtODda84juW8ZEnZbY0qKSDrVdCE");
        body.add("redirect_uri", "http://localhost:8080/user/login/oauth/kakao");
        body.add("code", code);

        //HttpHeader와 HttpBody 담기기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers); // params : body

        return restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, OAuthTokenDto.class).getBody();
    }

    // token으로 유저 정보 가져오기
    @Override
    public KakaoProfileDto userInfoRequest(OAuthTokenDto oauthTokenDto) {
        ///유저정보 요청
        RestTemplate restTemplate = new RestTemplate();

        //HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthTokenDto.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody 담기기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        return restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, kakaoProfileRequest, KakaoProfileDto.class).getBody();
    }

    // 회원가입
    @Override
    public void create(UserSignUpRequestDto signUpRequestDto) {
        User user = signUpRequestDto.toEntity();

        userRepository.save(user);
    }
}
