package com.project.voda.service;

import com.project.voda.domain.*;
import com.project.voda.repository.CalendarRepository;
import com.project.voda.repository.DailyEmotionRepository;
import com.project.voda.repository.DiaryRepository;
import com.project.voda.repository.SentenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final DailyEmotionRepository dailyEmotionRepository;
    private final CalendarRepository calendarRepository;

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
        int[] emotionSubCnt = new int[5];
        sentences.stream().forEach(x->emotionSubCnt[x.getEmotion().getEmotionIdx()]++);

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
}
