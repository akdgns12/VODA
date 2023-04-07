package com.project.voda.repository;

import com.project.voda.domain.Calendar;
import com.project.voda.domain.DailyEmotion;
import com.project.voda.domain.Emotion;
import com.project.voda.domain.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyEmotionRepository extends JpaRepository<DailyEmotion, Long> {

  // 주별 차트
  List<DailyEmotion> findByUserAndDayBetween(User user, LocalDate fromDate, LocalDate toDate);
  List<DailyEmotion> findByUserAndDay(User user, LocalDate day);
}
