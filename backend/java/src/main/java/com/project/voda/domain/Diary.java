package com.project.voda.domain;


import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.project.voda.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "diary")
@Getter
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

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "calendar_seq", nullable = false)
  private Calendar calendar;

  @Column(name = "voice_url", nullable = false)
  private String voiceUrl;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "delete_yn")
  @ColumnDefault("0")
  private boolean deleteYn;

  @JsonIgnore
  @OneToMany(mappedBy = "diary", orphanRemoval = true)
  private List<Sentence> sentences = new ArrayList<>();

}
