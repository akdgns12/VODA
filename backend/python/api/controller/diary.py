from fastapi import APIRouter, File, Form, UploadFile
from datetime import datetime, date 
from ..config.database import get_db 
from ..service import calendar_service 
from ..service import diary_service
from experiments.emotion_text.model_call import model_call
router = APIRouter()
AIMODEL = model_call()
@router.post("/dairy")
def post_diary(
    voice_file:UploadFile = File(...),
    date : date = Form(...),
    text_content : str = Form(...),
    id : int = Form(...)
): 
     db = get_db() 
     calendar = calendar_service.check_exist_calendar(db,date,id)
     diary = diary_service.add_diary(db,AIMODEL,calendar,text_content,"asd")
     return {"result" : diary.emotion_idx}
