package com.project.voda.dto;

import lombok.Data;

@Data
public class KakaoProfileDto {
    public KakaoAccount kakao_account;

    @Data
    public class KakaoAccount {
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
    }
}

