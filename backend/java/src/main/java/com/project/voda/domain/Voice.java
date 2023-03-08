package com.project.voda.domain;

import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "voice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voice extends BaseTimeEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "voice_seq")
  private Long voiceSeq;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "emotion_idx")
  private Emotion emotion;

  @Column(name = "url")
  private String url;

  @JsonIgnore
  @OneToOne(mappedBy = "voice", fetch = LAZY)
  private Diary diary;

}
