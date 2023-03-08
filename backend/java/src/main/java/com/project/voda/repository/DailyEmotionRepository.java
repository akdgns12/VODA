package com.project.voda.repository;

import com.project.voda.domain.DailyEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyEmotionRepository extends JpaRepository<DailyEmotion, Long> {

}
