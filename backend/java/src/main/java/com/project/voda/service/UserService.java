package com.project.voda.service;

import com.project.voda.dto.KakaoProfileDto;
import com.project.voda.dto.OAuthTokenDto;
import com.project.voda.dto.UserDto;

public interface UserService {
   // 회원 정보 찾기
    UserDto findByEmail(String email);
    // 토큰 가져오기
    String tokenRequest(String code);
    
    // 유저 정보 가져오기
//    KakaoProfileDto userInfoRequest(OAuthTokenDto oauthTokenDto);
  // 회원 가입
    
  // 로그인
  // 회원 정보 수정

  // 회원 탈퇴
}
