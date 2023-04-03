from fastapi import APIRouter, File, Form, UploadFile, status
from datetime import datetime, date 
from ..config.database import get_db 
from ..service import calendar_service , diary_service, daily_emotion_service
from experiments.emotion_text.model_call import model_call
from ser import ser_model
from ser import speech_to_text as stt
router = APIRouter()
AIMODEL = model_call()
SER = ser_model.EmotionRecognizer()

@router.post("/dairy", status_code=status.HTTP_201_CREATED)
def post_diary(
    voice_file:UploadFile = File(...),
    date : date = Form(...),
    # text_content : str = Form(...),
    id : int = Form(...)
): 
     db = get_db() 
     ind,file_name = diary_service.speech_emotion(SER, voice_file)
     text_content = stt.stt(file_name)
     calendar = calendar_service.check_exist_calendar(db,date,id)
     daily_emotions = daily_emotion_service.check_exist_daily_emotion(db,date,id)
     diary = diary_service.add_diary(db,AIMODEL,calendar,text_content,file_name)
     sentences,emotions_cnt = diary_service.add_sentence(db,AIMODEL,diary,daily_emotions,text_content,ind)
     calendar_service.update_best_emotion(db,calendar,daily_emotions)
     labels = ["슬픔","놀람","화남","중립","행복"]
     result = {
        "sentences": sentences, 
        "calendar_seq":calendar.calendar_seq, 
        "diary_seq" : diary.diary_seq,
        "emotion_idx" : diary.emotion_idx,
        "emotion_name" : labels[diary.emotion_idx],
        "voice_url" : diary.voice_url,
        "labels" : labels,
        "data" : emotions_cnt
        }

     return result