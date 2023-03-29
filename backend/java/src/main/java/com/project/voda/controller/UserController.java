package com.project.voda.controller;

import com.project.voda.dto.*;
import com.project.voda.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
@Api(tags = "유저 API")
public class UserController {

  private final UserService userService;
  @Value("${spring.security.oauth2.client.registration.kakao.client_id}") private String clientId;
  @Value("${spring.security.oauth2.client.registration.kakao.client_secret}") private String clientSecret;
  @Value("${spring.security.oauth2.client.registration.kakao.redirect_uri}") private String redirectUri;

  @ApiOperation(value = "소셜 로그인")
  @GetMapping("/login/oauth/kakao/{code}")
   public ResponseEntity<?> kakaoCallback(@RequestParam String code){
    log.info(code);
    try{
      // 토큰 가져오기
      OAuthTokenDto oAuthTokenDto = tokenRequest(code);
      KakaoProfileDto kakaoProfile = userInfoRequest(oAuthTokenDto.getAccess_token());
      String email = kakaoProfile.getKakao_account().getEmail();
      UserSignUpResponseDto userSignUpResponseDto = userService.findByEmail(email);
      if(userSignUpResponseDto == null){
        // db에 없는 회원이라면 회원가입 form으로 이동
        return new ResponseEntity<>(oAuthTokenDto.getAccess_token(), HttpStatus.NO_CONTENT);
      }else{ // 2. 저장됐다면 바로 메인 페이지로
        userSignUpResponseDto.setAccessToken(oAuthTokenDto.getAccess_token());
        userSignUpResponseDto.setRefreshToken(oAuthTokenDto.getRefresh_token());
        return new ResponseEntity<>(userSignUpResponseDto, HttpStatus.OK);
      }
    }catch (Exception e){
      return exceptionHandling(e);
    }
  }

  @ApiOperation(value = "회원 가입", notes = "닉네임과 access_token을 전달받고 회원가입 하는 API")
  @PostMapping("/signup")
  public ResponseEntity<?> join(@RequestBody UserSignUpRequestDto signUpRequestDto, @RequestParam String access_token){
    log.info("회원가입 Data : ",signUpRequestDto);
    try{
      KakaoProfileDto kakaoProfile = userInfoRequest(access_token);
      signUpRequestDto.setEmail(kakaoProfile.getKakao_account().getEmail());
      userService.create(signUpRequestDto);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      return exceptionHandling(e);
    }
  }

  // 인가 code로 token 가져오기
  public OAuthTokenDto tokenRequest(String code) {
    RestTemplate restTemplate = new RestTemplate();
    System.out.println("tokenRequest code:" + code);
    //HttpHeader
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    //HttpBody
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", clientId); //clientId 는 프로퍼티에 정의해놨음
    body.add("client_secret", clientSecret);
    body.add("redirect_uri", redirectUri);
    body.add("code", code);

    System.out.println(redirectUri);
    //HttpHeader와 HttpBody 담기기
    HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers); // params : body
    System.out.println(kakaoTokenRequest);
    return restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, OAuthTokenDto.class).getBody();
  }

  // access_token으로 유저 정보 가져오기
  public KakaoProfileDto userInfoRequest(String access_token) {
    ///유저정보 요청
    RestTemplate restTemplate = new RestTemplate();

    //HttpHeader
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + access_token);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    //HttpHeader와 HttpBody 담기기
    HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

    return restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, kakaoProfileRequest, KakaoProfileDto.class).getBody();
  }

  private ResponseEntity<?> exceptionHandling(Exception e) {
    Map<String, String> map = new HashMap<>();
    map.put("message", e.getMessage());
    return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
