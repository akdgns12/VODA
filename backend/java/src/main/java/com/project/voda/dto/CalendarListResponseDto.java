package com.project.voda.dto;

import com.project.voda.domain.Calendar;
import com.project.voda.domain.Emotion;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalendarListResponseDto {

  private Long calendarSeq;
  private String emotionImgUrl;

  @Builder
  private CalendarListResponseDto(Calendar calendar) {
    this.calendarSeq = calendar.getCalendarSeq();
    this.emotionImgUrl = calendar.getEmotion().getImgUrl();
  }

}
