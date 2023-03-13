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
    nickname = Column(String)
    mod_dtm = Column(DateTime(timezone=True), default=func.now())
    reg_dtm = Column(DateTime(timezone=True), onupdate=func.now())

class Calendar(Base):
    __table_args__={"extend_existing" : True}
    __tablename__ = "calendar"
    calendar_seq = Column(Integer, primary_key=True, autoincrement=True)
    day = Column(Date)
    user_seq = Column(Integer, ForeignKey("user.user_seq"))
    emotion_idx = Column(Integer, ForeignKey('emotion.emotion_seq'))
    mod_dtm = Column(DateTime(timezone=True), default=func.now())
    reg_dtm = Column(DateTime(timezone=True), onupdate=func.now())

    user = relationship("user", backref="user")
    emotion = relationship("emotion", backref="emotion")

class Diary(Base):
    __table_args__={"extend_existing": True }
    __tablename__ = "diary"
    diary_seqs = Column(Integer, primary_key= True, autoincrement=True )
    content = Column(String)
    delete_yn = Column(Boolean)
    voice_url = Column(String)
    calendar_seq = Column(Integer,ForeignKey('calendar.calendar_seq'))
    emotion_idx = Column(Integer, ForeignKey('emotion.emotion_idx'))
    mod_dtm = Column(DateTime(timezone=True), default=func.now())
    reg_dtm = Column(DateTime(timezone=True), onupdate=func.now())
    
    calendar = relationship("calendar", backref="calendar")
    emotion = relationship("emotion", backref="emotion")

class DailyEmotion(Base):
    __table_args__ = {"extend_existing": True}
    __tablename__="daily_emotion"
    daily_emotion_seq = Column(Integer,primary_key=True, autoincrement=True )
    cnt = Column(Integer)
    calendar_seq = Column(Integer,ForeignKey("calendar.calendar_seq"))
    emotion_idx = Column(Integer, ForeignKey("emotion.emotion_idx"))
    mod_dtm = Column(DateTime(timezone=True), default=func.now())
    reg_dtm = Column(DateTime(timezone=True), onupdate=func.now())

    calendar = relationship("calendar", backref="calendar") 
    emotion = relationship("emotion", backref="emotion")

class Emotion(Base):
    __table_args__ = {"extend_existing" :True }
    __tablename__ ="emotion"
    emotion_idx = Column(Integer,primary_key=True, autoincrement=True)
    img_url = Column(String)
    name = Column(String, nullable= False)
    mod_dtm = Column(DateTime(timezone=True), default=func.now())
    reg_dtm = Column(DateTime(timezone=True), onupdate=func.now())

class Sentence(Base):
    __table_args__={"extend_existing" : True}
    __tablename__ = "sentence"
    sentence_seq = Column(Integer, primary_key=True, autoincrement = True)
    content = Column(String)
    mod_yn = Column(Boolean, default=False)
    diary_seq = Column(Integer, ForeignKey('diary.diary_seq')) 
    emotion_idx = Column(Integer,ForeignKey('emotion.emotion_idx'))
    mod_dtm = Column(DateTime(timezone=True), default=func.now())
    reg_dtm = Column(DateTime(timezone=True), onupdate=func.now())

    diary = relationship("diary", backref="diary")
    emotion = relationship("emotion", backref="emotion")


class TrainingSentence(Base):
    __table_args__={"extend_existing": True}
    __tablename__="training_sentence"
    training_sentence_idx = Column(Integer, primary_key=True, autoincrement=True)
    content = Column(String, nullable=False)
    emotion_idx = Column(String, ForeignKey("emotion.emotion_idx"))
    mod_dtm = Column(DateTime(timezone=True), default=func.now())
    reg_dtm = Column(DateTime(timezone=True), onupdate=func.now())

    emotion = relationship("emotion", backref="emotion")
