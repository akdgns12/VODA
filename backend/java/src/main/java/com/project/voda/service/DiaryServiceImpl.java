package com.project.voda.service;

import com.project.voda.domain.*;
import com.project.voda.domain.Calendar;
import com.project.voda.dto.DiaryDetailResponseDto;
import com.project.voda.dto.SentenceDto;
import com.project.voda.repository.CalendarRepository;
import com.project.voda.repository.DailyEmotionRepository;
import com.project.voda.repository.DiaryRepository;
import com.project.voda.util.EmotionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@ConfigurationProperties
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final DailyEmotionRepository dailyEmotionRepository;
    private final CalendarRepository calendarRepository;
    private final int EMOTION_CNT = EmotionEnum.CNT.getCnt();
    private final String[] EMOTION_NAMES = EmotionEnum.EMOTION.getContent();
    private final String[] EMOTION_IMG_PATHS = EmotionEnum.IMGPATH.getContent();

    @Override
    public Boolean removeDiary(Long diarySeq) {
        Optional<Diary> optionalDiary = diaryRepository.findById(diarySeq);
        if(optionalDiary.isEmpty()){
            return false;
        }
        Diary diary = optionalDiary.get();
        List<Sentence> sentences = diary.getSentences();
        Calendar calendar = diary.getCalendar();
        List<DailyEmotion> dailyEmotions = dailyEmotionRepository.findByUserAndDay(calendar.getUser(),calendar.getDay());
        int[] emotionSubCnt = new int[EMOTION_CNT];
        sentences.forEach(x->emotionSubCnt[x.getEmotionIdx()]++);

        int maxCnt = 0;
        Emotion maxEmotion = null;
        for(DailyEmotion dailyEmotion : dailyEmotions){
            dailyEmotion.subCnt(emotionSubCnt[dailyEmotion.getEmotion().getEmotionIdx()]);
            if(dailyEmotion.getCnt() >= maxCnt){
                maxEmotion = dailyEmotion.getEmotion();
                maxCnt = dailyEmotion.getCnt();
            }
        }
        diaryRepository.delete(diary);

        if(maxCnt == 0 ){
            for(DailyEmotion dailyEmotion : dailyEmotions){
                dailyEmotionRepository.delete(dailyEmotion);
            }
            calendarRepository.delete(calendar);
        }else {
            calendar.updateEmotion(maxEmotion);
        }

        /**
         * voice 파일 삭제
         */
        return true;
    }

    @Override
    public DiaryDetailResponseDto getDiaryDetail(Long diarySeq) {
        Optional<Diary> optionalDiary = diaryRepository.findById(diarySeq);
        if(optionalDiary.isEmpty()) return null;

        Diary diary = optionalDiary.get();
        List<Sentence> sentences = diary.getSentences();
        if(sentences.isEmpty()) return null;

        int[] emotionCnt = new int[EMOTION_CNT];
        List<SentenceDto> sentenceDtos = new ArrayList<>();
        sentences.forEach(sentence->{
           emotionCnt[sentence.getEmotionIdx()] += 1;
           sentenceDtos.add(SentenceDto.builder()
                           .sentence(sentence)
                           .emotionName(EMOTION_NAMES[sentence.getEmotionIdx()])
                           .emotionImagePath(EMOTION_IMG_PATHS[sentence.getEmotionIdx()])
                           .build());
        });

        return DiaryDetailResponseDto.builder()
                .sentences(sentenceDtos)
                .emotionMain(diary.getEmotion().getName())
                .emotionCnt(emotionCnt)
                .build();
    }
}
