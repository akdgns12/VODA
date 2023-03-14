package com.project.voda.service;

import com.project.voda.domain.DailyEmotion;
import com.project.voda.domain.User;
import com.project.voda.dto.ChartMonthRepositoryDto;
import com.project.voda.dto.ChartWeekResponseDto;
import com.project.voda.repository.DailyEmotionRepository;
import com.project.voda.repository.EmotionRepository;
import com.project.voda.repository.UserRepository;
import io.swagger.models.auth.In;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
  private final UserRepository userRepository;


  // 주별 차트
  @Override
  public List<ChartWeekResponseDto> getChartWeek(Long userSeq, LocalDate toDate) {
    LocalDate fromDate = toDate.minusDays(6);
    log.info("fromDate: {}, toDate: {}", fromDate, toDate);

    List<ChartWeekResponseDto> response = new ArrayList<>();

    Optional<User> user = userRepository.findById(userSeq);
    if (user.isEmpty()) {
      return new ArrayList<>();
    }
    List<DailyEmotion> dailyEmotions = dailyEmotionRepository.findByUserAndDayBetween(user.get(), fromDate, toDate);

    emotionRepository.findAll().forEach(emotion -> {
      List<Integer> data = new ArrayList<>();

      List<DailyEmotion> week = dailyEmotions.stream()
          .filter(m -> m.getEmotion().getEmotionIdx() == emotion.getEmotionIdx())
          .collect(Collectors.toList());

      Stream.iterate(fromDate, day -> day.isBefore(toDate.plusDays(1)), day -> day.plusDays(1))
          .map(day -> week.stream().filter(m -> m.getDay().isEqual(day)).findFirst())
          .forEach(dailyEmotion -> {
            if (dailyEmotion.isEmpty())
              data.add(0);
            else
              data.add(dailyEmotion.get().getCnt());
          });

      log.info("label: {}, data: {}", emotion.getName(), data);
      response.add(ChartWeekResponseDto.builder().label(emotion.getName()).data(data).build());
    });

    return response;
  }

  // 월별 차트
  @Override
  public ChartMonthRepositoryDto getChartMonth(Long userSeq, LocalDate date) {
    LocalDate fromDate = LocalDate.of(date.getYear(), date.getMonthValue(), 1); // 선택된 달의 1일
    int endOfMonth = YearMonth.of(date.getYear(),date.getMonthValue()).atEndOfMonth().getDayOfMonth(); // 선택된 달의 마지막 날짜
    LocalDate toDate = LocalDate.of(date.getYear(), date.getMonthValue(), endOfMonth);
    log.info("fromDate: {}, toDate: {}", fromDate, toDate);

    List<String> labels = new ArrayList<>();
    List<Integer> data = new ArrayList<>();

    Optional<User> user = userRepository.findById(userSeq);
    if (user.isEmpty()) {
      return null;
    }
    List<DailyEmotion> dailyEmotions = dailyEmotionRepository.findByUserAndDayBetween(user.get(), fromDate, toDate);

    emotionRepository.findAll().forEach(emotion -> {
      labels.add(emotion.getName());
      data.add(dailyEmotions.stream()
          .filter(m -> m.getEmotion().getEmotionIdx() == emotion.getEmotionIdx())
          .map(m -> m.getCnt())
          .reduce(0, Integer::sum));
    });

    log.info("labels: {}, data: {}", labels, data);
    return ChartMonthRepositoryDto.builder().labels(labels).data(data).build();
  }
}
