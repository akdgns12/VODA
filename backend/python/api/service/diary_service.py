import os 
import sys 
from sqlalchemy.orm import Session 
from ..model import models, schemas
from experiments.emotion_text.test import predict 
from experiments.emotion_text import model_call
from kiwipiepy import Kiwi

# AIMODEL = model_call.model_call()

def add_diary(
        db : Session, 
        AIMODEL : model_call,
        calendar : models.Calendar, 
        text_content : str,
        voice_url : str, 
    ) -> int:
   
    emotion_result = AIMODEL.predict(text_content)
    diary = models.Diary(
        content = text_content ,
        delete_yn = False ,
        voice_url = "test",
        calendar_seq = calendar.calendar_seq,
        emotion_idx = 3 
    )
    
    db.add(diary)
    db.commit()
    return diary 

def add_sentence(
        db: Session, 
        AIMODEL: model_call,
        diary: models.Diary, 
        daily_emotions : models.DailyEmotion,
        text_content:str,
):
    kiwi = Kiwi()
    sentences = kiwi.split_into_sents(text_content)
    sentence_emotions = []
    emotions = [0 for _ in range(5)]
    for sentence in sentences : 
        sentence_emotions.append((sentence.text, int(AIMODEL.predict(sentence.text if sentence.text[-1] =='.' else sentence.text+'.'))))
        save_sentence = models.Sentence(
            diary_seq = diary.diary_seq,
            content = sentence.text,
            emotion_idx = sentence_emotions[-1][1], 
        )
        emotions[sentence_emotions[-1][1]] +=1 
        db.add(save_sentence)
    best_emotion_cnt = 0 
    best_emotion_ind = 3
    for ind,emotion in enumerate(emotions):
        daily_emotions[ind].cnt += emotion
        if best_emotion_cnt <= emotion: 
            best_emotion_cnt = emotion 
            best_emotion_ind = ind 
    diary.emotion_idx = best_emotion_ind
    db.commit()
    return sentence_emotions, emotions
