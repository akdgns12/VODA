package com.project.voda.service;

import com.project.voda.domain.User;
import com.project.voda.dto.KakaoProfileDto;
import com.project.voda.dto.OAuthTokenDto;
import com.project.voda.dto.UserDto;
import com.project.voda.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    public String tokenRequest(String code) {

        // POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // 이 때 필요한 라이브러리가 RestTemplate, 얘를 쓰면 http 요청을 편하게 할 수 있다.
        RestTemplate rt = new RestTemplate();

        // HTTP POST를 요청할 때 보내는 데이터(body)를 설명해주는 헤더도 만들어 같이 보내줘야 한다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // body 데이터를 담을 오브젝트인 MultiValueMap를 만들어보자
        // body는 보통 key, value의 쌍으로 이루어지기 때문에 자바에서 제공해주는 MultiValueMap 타입을 사용한다.
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "6b962da1630b8dc319eeca83a8061511");
        params.add("client_secret","NUZ9Hgye75UTSZNQnZjmDV0sy71XXPNG");
        params.add("redirect_uri", "http://localhost:8080/user/login/oauth/kakao");
        params.add("code", code);

        // 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
        // kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 된다.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
                HttpMethod.POST, // 요청할 방식
                kakaoTokenRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );

        return "카카오 토큰 요청 완료 : 토큰 요청에 대한 응답 : "+response;
    }

    // token으로 유저 정보 가져오기
//    @Override
//    public KakaoProfileDto userInfoRequest(OAuthTokenDto oauthTokenDto) {
//        ///유저정보 요청
//        RestTemplate restTemplate = new RestTemplate();
//
//        //HttpHeader
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + oauthTokenDto.getAccess_token());
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpHeader와 HttpBody 담기기
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
//
//        return restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, KakaoProfileDto.class).getBody();
//    }


}
