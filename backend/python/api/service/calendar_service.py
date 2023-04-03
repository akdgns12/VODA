import sys 
import os
from sqlalchemy.orm import Session 
from sqlalchemy import update 
from sqlalchemy.sql import func 
from datetime import datetime, date 
from ..model import models, schemas

def check_exist_calendar(db:Session, date:date, user_seq : int) -> models.Calendar:
    calendar = db.query(models.Calendar).filter(models.Calendar.day == date and models.Calendar.user_seq == user_seq).one_or_none()
    if not calendar : 
        calendar = models.Calendar(
            day = date,
            user_seq = user_seq 
        )
        db.add(calendar)
        db.commit() 
    return calendar 

def update_best_emotion(db:Session, calendar : models.Calendar, daily_emotions : list) ->models.Calendar:
    max_idx = 3
    max_cnt = 0
    
    for idx,daily_emotion in enumerate(daily_emotions) :
        if daily_emotion.cnt >= max_cnt :
            max_cnt = daily_emotion.cnt 
            max_idx = idx 
    calendar.emotion_idx = max_idx
    db.commit()