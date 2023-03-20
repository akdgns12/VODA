package com.project.voda.dto;

import com.project.voda.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignUpRequestDto {
    private String email;
    private String nickname;

    public User toEntity(){
        return User.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
}
