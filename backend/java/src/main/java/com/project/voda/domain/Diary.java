package com.project.voda.domain;


import static javax.persistence.FetchType.LAZY;

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
import lombok.Setter;

@Entity
@Table(name = "diary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "diary_seq")
  private Long diarySeq;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "emotion_idx")
  private Emotion emotion;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "voice_seq")
  private Voice voice;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "calendar_seq")
  private Calendar calendar;

  @Column(name = "content")
  private String content;

}
