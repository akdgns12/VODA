package com.project.voda.service;

import com.project.voda.domain.Calendar;
import com.project.voda.domain.Diary;
import com.project.voda.domain.User;
import com.project.voda.dto.CalendarListResponseDto;
import com.project.voda.dto.DiaryListResponseDto;
import com.project.voda.repository.CalendarRepository;
import com.project.voda.repository.DiaryRepository;
import com.project.voda.repository.UserRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CalendarServiceImpl implements CalendarService {

  private final CalendarRepository calendarRepository;
  private final UserRepository userRepository;
  private final DiaryRepository diaryRepository;

  // 한달 감정
  @Override
  public List<CalendarListResponseDto> getCalendarAndEmotion(Long id, LocalDate date) {
    LocalDate fromDate = LocalDate.of(date.getYear(), date.getMonthValue(), 1); // 선택된 달의 1일
    int endOfMonth = YearMonth.of(date.getYear(),date.getMonthValue()).atEndOfMonth().getDayOfMonth(); // 선택된 달의 마지막 날짜
    LocalDate toDate = LocalDate.of(date.getYear(), date.getMonthValue(), endOfMonth);
    log.info("fromDate: {}, toDate: {}", fromDate, toDate);

    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      return new ArrayList<>();
    }
    log.info("find user: {}", user.get().getUserSeq());

    List<Calendar> calendars = calendarRepository.findByUserAndDayBetween(user.get(), fromDate, toDate);
    return calendars.stream()
        .map(m -> CalendarListResponseDto.builder().calendar(m).build())
        .collect(Collectors.toList());
  }

  // 하루 일기 목록
  @Override
  public List<DiaryListResponseDto> getDiarys(Long calendarSeq) {
    Optional<Calendar> calendar = calendarRepository.findById(calendarSeq);

    if (calendar.isEmpty()) {
      return new ArrayList<>();
    }
    log.info("find calendar: {}", calendar.get().getCalendarSeq());

    List<Diary> diaries = diaryRepository.findByCalendar(calendar.get());
    return diaries.stream()
        .map(m -> DiaryListResponseDto.builder().diary(m).build())
        .collect(Collectors.toList());
  }
}
