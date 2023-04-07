package com.project.voda.dto;

import com.project.voda.domain.Sentence;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SentenceDto {
    String content;
    String emotionImagePath;

    // 대표 감정 -> 이미지 관련 처리 완료되면 지우기.
    String emotionMain;
    @Builder
    public SentenceDto(Sentence sentence, String emotionName, String emotionImagePath){
        this.emotionMain = emotionName;
        this.emotionImagePath = emotionImagePath;
        this.content = sentence.getContent();
    }
}
