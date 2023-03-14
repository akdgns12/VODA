package com.project.voda.dto;

import com.project.voda.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignUpResponseDto {
    private Long userSeq;
    private String nickname;
    private String email;
    private String accessToken;
    private String refreshToken;

    @Builder
    private UserSignUpResponseDto(User user){
        this.userSeq = user.getUserSeq();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
