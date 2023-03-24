import sys 
import os
from sqlalchemy.orm import Session 
from sqlalchemy import update 
from sqlalchemy.sql import func 
from datetime import datetime, date 
from ..model import models, schemas

def check_exist_daily_emotion(db:Session, date:date, user_seq : int) -> models.DailyEmotion:
    daily_emotions = db.query(models.DailyEmotion).filter(models.DailyEmotion.day == date and models.DailyEmotion.user_seq == user_seq).all()
    if not daily_emotions : 
        for emotion_idx in range(5):
            daily_emotions.append(models.DailyEmotion(
                day=date,
                user_seq = user_seq, 
                emotion_idx = emotion_idx
            ))
            db.add(daily_emotions[-1])
        db.commit() 
    return daily_emotions