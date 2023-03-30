package com.project.voda.service;

import com.project.voda.dto.UserSignInResponseDto;
import com.project.voda.dto.UserSignUpRequestDto;

public interface UserService {
 // 회원 정보 찾기
 UserSignInResponseDto findByEmail(String email);

 // 회원 가입
 void create(UserSignUpRequestDto signUpRequestDto);

}
