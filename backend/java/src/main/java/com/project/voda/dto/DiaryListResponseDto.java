package com.project.voda.dto;

import com.project.voda.domain.Diary;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiaryListResponseDto {

  private Long diarySeq;
  private String content;
  private String voiceUrl;
  private int emotionIdx;
  private String emotionName;
  private String emotionImgUrl;

  @Builder
  public DiaryListResponseDto(Diary diary) {
    this.diarySeq = diary.getDiarySeq();
    this.content = diary.getContent();
    this.voiceUrl = diary.getVoiceUrl();
    this.emotionIdx = diary.getEmotion().getEmotionIdx();
    this.emotionName = diary.getEmotion().getName();
    this.emotionImgUrl = diary.getEmotion().getImgUrl();
  }

}
