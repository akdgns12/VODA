package com.project.voda.service;

import com.project.voda.dto.CalendarListResponseDto;
import com.project.voda.dto.DiaryListResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface CalendarService {

  // 한달 감정
  List<CalendarListResponseDto> getCalendarAndEmotion(Long id, LocalDate date);

  // 하루 일기 목록
  List<DiaryListResponseDto> getDiarys(Long calendarSeq);

}
