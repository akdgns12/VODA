import uvicorn
from fastapi import FastAPI
from fastapi.exceptions import RequestValidationError
from fastapi.middleware.cors import CORSMiddleware
from starlette.exceptions import HTTPException
from typing import Callable
from api.config.database import engine
from model import models

models.Base.metadata.create_all(bind=engine)

def set_cors(application:FastAPI) :
    origins =["*"]
    application.middleware(
        CORSMiddleware,
        allow_origins=origins,
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"]
    )

def get_application() -> FastAPI:
    application = FastAPI() 
    application.include_router(api_router)
    set_cors(application) 
    return application 

app = get_application()

if __name__ == "__main__":
    uvicorn.run(
        "main:app",
        host="0.0.0.0",
        port="8081",
        reload=True,
        debug=False,
    )