import os 
import sys 
from sqlalchemy.orm import Session 
from ..model import models, schemas
from experiments.emotion_text.test import predict 
from experiments.emotion_text import model_call

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
        emotion_idx = emotion_result 
    )
    db.add(diary)
    db.commit()
    return diary 
