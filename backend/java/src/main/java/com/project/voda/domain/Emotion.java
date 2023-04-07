package com.project.voda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.project.voda.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emotion")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emotion extends BaseTimeEntity {

  @Id
  @Column(name = "emotion_idx")
  private Integer emotionIdx;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "img_url")
  private String imgUrl;

}
