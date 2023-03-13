package com.project.voda.controller;

import com.project.voda.dto.KakaoProfileDto;
import com.project.voda.dto.OAuthTokenDto;
import com.project.voda.dto.UserSignUpRequestDto;
import com.project.voda.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
@Api(tags = "유저 API")
public class UserController {

  private final UserService userService;

  @ApiOperation(value = "소셜 로그인")
  @GetMapping("/login/oauth/kakao")
  public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code){
    Map<String, Object> resultMap = new HashMap<>();
    HttpStatus status = null;

    try{
      // 토큰 가져오기
      OAuthTokenDto oAuthTokenDto = userService.tokenRequest(code);
      // 유저 정보 가져오기
      KakaoProfileDto kakaoProfile = userService.userInfoRequest(oAuthTokenDto);
      String email = kakaoProfile.getKakao_account().getEmail();
      if(userService.findByEmail(email) == null){
        // db에 없는 회원이라면 회원가입 form으로 이동
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }else{ // 2. 저장됐다면 바로 메인 페이지로
        resultMap.put("이미 등록된 유저 email", email);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
      }
    }catch (Exception e){
      return exceptionHandling(e);
    }
  }

  @ApiOperation(value = "회원 가입", notes = "닉네임을 입력받고 회웝가입 하는 API")
  @PostMapping("/signup")
  public ResponseEntity<?> join(UserSignUpRequestDto signUpRequestDto){
    log.info("회원가입 Data : ",signUpRequestDto);

    try{
      userService.create(signUpRequestDto);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      return exceptionHandling(e);
    }
  }

  private ResponseEntity<?> exceptionHandling(Exception e) {
    Map<String, String> map = new HashMap<>();
    map.put("message", e.getMessage());
    return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
