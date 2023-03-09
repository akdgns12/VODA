package com.project.voda.service;

import com.project.voda.domain.Calendar;
import com.project.voda.domain.Diary;
import com.project.voda.domain.Emotion;
import com.project.voda.domain.User;
import com.project.voda.dto.CalendarListResponseDto;
import com.project.voda.repository.CalendarRepository;
import com.project.voda.repository.EmotionRepository;
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

  // 한달 감정
  @Override
  public List<CalendarListResponseDto> getCalendarAndEmotion(Long id, LocalDate date) {
    LocalDate fromDate = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
    int endOfMonth = YearMonth.of(date.getYear(),date.getMonthValue()).atEndOfMonth().getDayOfMonth();
    LocalDate toDate = LocalDate.of(date.getYear(), date.getMonthValue(), endOfMonth);
    log.info("fromDate: {}, toDate: {}", fromDate, toDate);

    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      return new ArrayList<>();
    }

    List<Calendar> calendar = calendarRepository.findByUserAndDayBetween(user.get(), fromDate, toDate);

    return calendar.stream()
        .map(m -> CalendarListResponseDto.builder().calendar(m).build())
        .collect(Collectors.toList());
  }
}
