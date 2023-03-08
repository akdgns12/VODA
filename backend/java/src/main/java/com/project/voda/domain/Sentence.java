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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sentence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sentence extends BaseTimeEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sentence_seq")
  private Long sentenceSeq;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "emotion_idx")
  private Emotion emotion;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "diary_seq")
  private Diary diary;

  @Column(name = "content")
  private String content;

  @Column(name = "mod_yn")
  private boolean modYn;


}
