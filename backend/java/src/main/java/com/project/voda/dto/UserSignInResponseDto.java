package com.project.voda.dto;

import com.project.voda.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignInResponseDto {
    private Long userSeq;
    private String nickname;
    private String email;
    private String accessToken;
    private String refreshToken;

    @Builder
    private UserSignInResponseDto(User user){
        this.userSeq = user.getUserSeq();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.accessToken = this.getAccessToken();
        this.refreshToken = this.getRefreshToken();
    }
}
