import os 
import sys 
from sqlalchemy.orm import Session 
from ..model import models, schemas
from experiments.emotion_text.test import predict 
from experiments.emotion_text import model_call
from kiwipiepy import Kiwi
from ser import ser_model
from ser.speech_to_text import stt

import numpy as np
import uuid
from dotenv import load_dotenv
# AIMODEL = model_call.model_call()
VOICE_WEIGHT = int(os.getenv("VOICE_WEIGHT"))
def add_diary(
        db : Session, 
        AIMODEL : model_call,
        calendar : models.Calendar, 
        text_content : str,
        voice_url : str, 
    ) -> int:
    """
    데이터베이스에 다이어리를 추가합니다. 
        Args:
            db : DB Session
            AIMODEL : KoBERTModel 
            calendar : Calendar entity
            text_content : STT Result 
            voice_url : voice file path (S3 or file path)
        Return : 
            diary : diary entity 
    """
   
    emotion_result = AIMODEL.predict(text_content)
    diary = models.Diary(
        content = text_content ,
        delete_yn = False ,
        voice_url = voice_url,
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
        voice_result:int
):
    """
    나누어진 문장을 데이터베이스에 추가합니다. 
        Args : 
            db : DB Session
            AIMODEL : KoBERTModel 
            diary : Diary entity
            daily_emotions : daily_emotions entity 
            text_content : STT Result 
            voice_result : voice emotion - classification result 
        Return :
            sentece_emotions : sentence with emotion result 
            emotions : emotion cnt list 
    """
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
    best_emotion_ind = voice_result
    emotions[voice_result] += VOICE_WEIGHT # + 음성 결과 
    for ind,emotion in enumerate(emotions):
        daily_emotions[ind].cnt += emotion
        if best_emotion_cnt <= emotion: 
            best_emotion_cnt = emotion 
            best_emotion_ind = ind 
    diary.emotion_idx = best_emotion_ind
    daily_emotions[voice_result] -= VOICE_WEIGHT
    emotions[voice_result] -= VOICE_WEIGHT # - 음성 결과
    db.commit()
    return sentence_emotions, emotions

def speech_emotion(SER, voice_file):
    """
    음성파일을 uuid기반으로 저장하고 저장된 음성을 기반으로 감정을 분류합니다. 
        Args : 
            SER : Sound Emotion Recognition Model
            voice_file : voice file path
        Return : 
            ind : voice emotion result index
            file_name : uuid - file id
    """
    # labels = ["중립","행복","슬픔","화남","놀람"]
    labels = ["슬픔","놀람","화남","중립","행복"]
    change_ind = {0:3, 1:4, 2:0, 3:2, 4:1}
    result = [0 for _ in range(5)] 
    content = voice_file.file.read() 
    file_name = f"./voice_file/{str(uuid.uuid4())}.wav"
    with open(file_name,"wb") as fp :
        fp.write(content)

    speech_emotions = SER.predict_file(file_name)
    print(speech_emotions)
    for elem in speech_emotions:
        # ind = elem.index(max(elem))
        ind = np.argmax(elem)
        ind = change_ind[ind]
        result[ind] += 1 

    ind = result.index(max(result))
    print(f"음성에서 {labels[ind]}이 느껴집니다.")
    return ind,file_name.split('/')[-1]