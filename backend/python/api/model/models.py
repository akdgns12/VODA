import sys 
import os
from sqlalchemy import (
    Boolean, 
    Column, 
    ForeignKey,
    Integer,
    String,
    DateTime,
    Date,
    Float, 
)
from sqlalchemy.orm import relationship 
from sqlalchemy.sql import func 

from ..config.database import Base

class User(Base):
    __table_args__={"extend_existing" : True}
    __tablename__="user"
    user_seq = Column(Integer, primary_key=True, autoincrement=True)
    delete_yn = Column(Boolean, default=False)
    email = Column(String,nullable=False)
    model_id = Column(String) 
    nickname = Column(String)
    reg_dtm = Column(DateTime(timezone=True), default=func.now())
    mod_dtm = Column(DateTime(timezone=True), onupdate=func.now())

class Calendar(Base):
    __table_args__={"extend_existing" : True}
    __tablename__ = "calendar"
    calendar_seq = Column(Integer, primary_key=True, autoincrement=True)
    day = Column(Date)
    user_seq = Column(Integer, ForeignKey("user.user_seq"), nullable=False)
    emotion_idx = Column(Integer, ForeignKey('emotion.emotion_idx'))
    reg_dtm = Column(DateTime(timezone=True), default=func.now())
    mod_dtm = Column(DateTime(timezone=True), onupdate=func.now())

    user = relationship("User", uselist=False, backref="calendar_user")
    emotion = relationship("Emotion", backref="calendar_emotion")

class Diary(Base):
    __table_args__={"extend_existing": True }
    __tablename__ = "diary"
    diary_seq = Column(Integer, primary_key= True, autoincrement=True )
    content = Column(String)
    delete_yn = Column(Boolean)
    voice_url = Column(String)
    calendar_seq = Column(Integer,ForeignKey('calendar.calendar_seq'))
    emotion_idx = Column(Integer, ForeignKey('emotion.emotion_idx'))
    reg_dtm = Column(DateTime(timezone=True), default=func.now())
    mod_dtm = Column(DateTime(timezone=True), onupdate=func.now())
    
    calendar = relationship("Calendar", backref="diary_calendar")
    emotion = relationship("Emotion", backref="diary_emotion")
    
class DailyEmotion(Base):
    __table_args__ = {"extend_existing": True}
    __tablename__="daily_emotion"
    daily_emotion_seq = Column(Integer,primary_key=True, autoincrement=True )
    cnt = Column(Integer, default=0)
    day = Column(Date, nullable = False)
    emotion_idx = Column(Integer, ForeignKey("emotion.emotion_idx"))
    reg_dtm = Column(DateTime(timezone=True), default=func.now())
    mod_dtm = Column(DateTime(timezone=True), onupdate=func.now())
    user_seq = Column(Integer, ForeignKey("user.user_seq"))
    
    emotion = relationship("Emotion", backref="daily_emotion_emotion")
    user = relationship("User", backref = "daily_emotion_user")

class Emotion(Base):
    __table_args__ = {"extend_existing" :True }
    __tablename__ ="emotion"
    emotion_idx = Column(Integer,primary_key=True, autoincrement=True)
    img_url = Column(String)
    name = Column(String, nullable= False)
    reg_dtm = Column(DateTime(timezone=True), default=func.now())
    mod_dtm = Column(DateTime(timezone=True), onupdate=func.now())

class Sentence(Base):
    __table_args__={"extend_existing" : True}
    __tablename__ = "sentence"
    sentence_seq = Column(Integer, primary_key=True, autoincrement = True)
    content = Column(String)
    mod_yn = Column(Boolean, default=False)
    diary_seq = Column(Integer, ForeignKey('diary.diary_seq')) 
    emotion_idx = Column(Integer,ForeignKey('emotion.emotion_idx'))
    reg_dtm = Column(DateTime(timezone=True), default=func.now())
    mod_dtm = Column(DateTime(timezone=True), onupdate=func.now())

    diary = relationship("Diary", backref="sentence_diary")
    emotion = relationship("Emotion", backref="sentence_emotion", uselist=False)


class TrainingSentence(Base):
    __table_args__={"extend_existing": True}
    __tablename__="training_sentence"
    training_sentence_idx = Column(Integer, primary_key=True, autoincrement=True)
    content = Column(String, nullable=False)
    emotion_idx = Column(String, ForeignKey("emotion.emotion_idx"))
    reg_dtm = Column(DateTime(timezone=True), default=func.now())
    mod_dtm = Column(DateTime(timezone=True), onupdate=func.now())

    emotion = relationship("Emotion", backref="training_sentence_emotion")
