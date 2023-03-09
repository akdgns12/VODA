package com.project.voda.dto;

import com.project.voda.domain.Calendar;
import com.project.voda.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Long user_seq;
    private String nickname;
    private String email;
    private String modelId;
    private boolean deleteYn;
    private List<Calendar> calendars = new ArrayList<>();

    @Builder
    private UserDto(User user){
        this.user_seq = user.getUserSeq();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.modelId = user.getModelId();
        this.deleteYn = user.isDeleteYn();
        this.calendars = user.getCalendars();
    }
}
