package com.project.voda.controller;

import com.project.voda.dto.KakaoProfileDto;
import com.project.voda.dto.OAuthTokenDto;
import com.project.voda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";

  private final UserService userService;

  @GetMapping("/login/oauth/kakao")
  public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code){
    Map<String, Object> resultMap = new HashMap<>();
    HttpStatus status = null;
    // 토큰 가져오기
    String token = userService.tokenRequest(code);
    logger.info("token : ", token);
    System.out.println(token);

    // 유저 정보 가져오기
//    KakaoProfileDto kakaoProfile = userService.userInfoRequest(oAuthToken);
//    System.out.println(kakaoProfile);

    try{

    }catch (Exception e){

    }

    String response = "성공적으로 카카오 로그인 API 코드를 불러왔습니다.";
    resultMap.put("response : ", response);
    resultMap.put("code : ", code);
    status = HttpStatus.OK;

    return new ResponseEntity<Map<String, Object>>(resultMap, status);
  }
}
