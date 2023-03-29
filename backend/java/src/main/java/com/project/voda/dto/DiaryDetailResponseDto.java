package com.project.voda.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class DiaryDetailResponseDto {
    List<SentenceDto> sentenceDtos ;
    String emotionMain;
    int[] emotionCnt;

    @Builder
    public DiaryDetailResponseDto(List<SentenceDto> sentences, String emotionMain, int[] emotionCnt){
        this.emotionCnt = emotionCnt;
        this.emotionMain = emotionMain;
        this.sentenceDtos = sentences;
    }
}
