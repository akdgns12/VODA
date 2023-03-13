package com.project.voda.repository;

import com.project.voda.domain.Calendar;
import com.project.voda.domain.DailyEmotion;
import com.project.voda.domain.Emotion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyEmotionRepository extends JpaRepository<DailyEmotion, Long> {

  // 주별 차트
  Optional<DailyEmotion> findByCalendarAndEmotion(Calendar calendar, Emotion emotion);

}
