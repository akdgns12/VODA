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
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "sentence")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sentence extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sentence_seq")
  private Long sentenceSeq;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "diary_seq", nullable = false)
  private Diary diary;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "emotion_idx", nullable = false)
  private Emotion emotion;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "mod_yn")
  @ColumnDefault("0")
  private boolean modYn;

}
