package com.project.voda.service;

import com.project.voda.dto.UserSignUpRequestDto;
import com.project.voda.dto.UserSignUpResponseDto;

public interface UserService {
 // 회원 정보 찾기
 UserSignUpResponseDto findByEmail(String email);

 // 회원 가입
 void create(UserSignUpRequestDto signUpRequestDto);

}
