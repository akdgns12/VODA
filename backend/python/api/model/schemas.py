from typing import List,Optional 
from pydantic import BaseModel 
from datetime import datetime, date 
class calendar :
    calendar_seq : int
    mod_dtm : datetime 
    reg_dtm : datetime 
    day : date 
    emotion_idx : int 
    user_seq : int 

class daily_emotion :
    pass

class diary :
    pass 

class emotion :
    pass 

class sentence :
    pass 

class training_sentence :
    pass 

class user : 
    pass 