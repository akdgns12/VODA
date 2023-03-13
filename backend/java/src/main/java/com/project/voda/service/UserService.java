package com.project.voda.service;

import com.project.voda.dto.KakaoProfileDto;
import com.project.voda.dto.OAuthTokenDto;
import com.project.voda.dto.UserDto;
import com.project.voda.dto.UserSignUpRequestDto;

public interface UserService {
 // 회원 정보 찾기
 UserDto findByEmail(String email);

 // 토큰 가져오기
 OAuthTokenDto tokenRequest(String code);

 // 유저 정보 가져오기
 KakaoProfileDto userInfoRequest(OAuthTokenDto oauthTokenDto);

 // 회원 가입
 void create(UserSignUpRequestDto signUpRequestDto);

}
