import sys 
import os
from sqlalchemy.orm import Session 
from sqlalchemy import update 
from sqlalchemy.sql import func 
from datetime import datetime, date 
from ..model import models, schemas

def check_exist_calendar(db:Session, date:date, user_seq : int) -> int:
    calendar = db.query(models.Calendar).filter(models.Calendar.day == date and models.Calendar.user_seq == user_seq).one_or_none()
    if not calendar : 
        calendar = models.Calendar(
            day = date,
            user_seq = user_seq 
        )
        db.add(calendar)
        db.commit() 
    return calendar 