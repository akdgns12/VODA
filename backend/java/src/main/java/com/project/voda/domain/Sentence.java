package com.project.voda.domain;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.*;

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

  @ManyToOne(fetch = LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "diary_seq", nullable = false)
  private Diary diary;

  @Column(name = "emotion_idx", nullable = false)
  private int emotionIdx;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "mod_yn")
  @ColumnDefault("0")
  private boolean modYn;

}
