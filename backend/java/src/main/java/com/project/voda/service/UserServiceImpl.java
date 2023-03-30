package com.project.voda.service;

import com.project.voda.domain.User;
import com.project.voda.dto.UserSignInRequestDto;
import com.project.voda.dto.UserSignInResponseDto;
import com.project.voda.dto.UserSignUpRequestDto;
import com.project.voda.dto.UserSignUpResponseDto;
import com.project.voda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    // Email로 회원정보 가져오기
    @Override
    public UserSignInResponseDto findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) return null;
        return UserSignInResponseDto.builder().user(user.get()).build();
    }

    // 회원가입
    @Override
    public void create(UserSignUpRequestDto signUpRequestDto) {
        User user = signUpRequestDto.toEntity();

        userRepository.save(user);
    }
}
