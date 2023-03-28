package com.project.voda.dto;

import com.project.voda.domain.Calendar;
import com.project.voda.domain.Emotion;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalendarListResponseDto {

  private Long calendarSeq;
  private LocalDate date;
  private String emotionImgUrl;

  @Builder
  private CalendarListResponseDto(Calendar calendar) {
    this.calendarSeq = calendar.getCalendarSeq();
    this.date = calendar.getDay();
    this.emotionImgUrl = calendar.getEmotion().getImgUrl();
  }

}
