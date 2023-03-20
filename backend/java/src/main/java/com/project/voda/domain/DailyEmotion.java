package com.project.voda.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.project.voda.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "daily_emotion")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyEmotion extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "daily_emotion_seq")
  private Long dailyEmotionSeq;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_seq", nullable = false)
  private User user;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "emotion_idx")
  private Emotion emotion;

  @Column(name = "day", nullable = false)
  private LocalDate day;

  @Column(name = "cnt")
  @ColumnDefault(value = "0")
  private int cnt;

}
