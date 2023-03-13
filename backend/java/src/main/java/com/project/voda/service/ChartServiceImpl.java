package com.project.voda.service;

import com.project.voda.domain.Calendar;
import com.project.voda.domain.DailyEmotion;
import com.project.voda.domain.Emotion;
import com.project.voda.domain.User;
import com.project.voda.dto.ChartWeekResponseDto;
import com.project.voda.repository.CalendarRepository;
import com.project.voda.repository.DailyEmotionRepository;
import com.project.voda.repository.EmotionRepository;
import com.project.voda.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChartServiceImpl implements ChartService {

  private final DailyEmotionRepository dailyEmotionRepository;
  private final EmotionRepository emotionRepository;
  private final CalendarRepository calendarRepository;
  private final UserRepository userRepository;


  // 주별 차트
  @Override
  public List<ChartWeekResponseDto> getChartWeek(Long userSeq, LocalDate toDate) {
    Optional<User> user = userRepository.findById(userSeq);
    if (user.isEmpty()) {
      return new ArrayList<>();
    }

    List<ChartWeekResponseDto> response = new ArrayList<>();

    for (Emotion emotion : emotionRepository.findAll()) {
      List<Integer> data = new ArrayList<>();
      LocalDate fromDate = toDate.minusDays(6);
      log.info("emotion: {}, fromDate: {}, toDate: {}", emotion.getName(), fromDate, toDate);
      for (LocalDate day = fromDate; day.isBefore(toDate.plusDays(1)); day = day.plusDays(1)) {
        Optional<Calendar> calendar = calendarRepository.findByUserAndDay(user.get(), day);
        if (calendar.isEmpty()) {
          data.add(0);
        } else {
          Optional<DailyEmotion> dailyEmotion = dailyEmotionRepository.findByCalendarAndEmotion(calendar.get(), emotion);
          if (dailyEmotion.isEmpty()) {
            data.add(0);
          } else {
            data.add(dailyEmotion.get().getCnt());
          }
        }
      }
      log.info("label: {}, data: {}", emotion.getName(), data);
      response.add(ChartWeekResponseDto.builder().label(emotion.getName()).data(data).build());
    }

    return response;
  }
}
