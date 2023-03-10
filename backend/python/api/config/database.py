from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

import os
from dotenv import load_dotenv

load_dotenv(verbose=True)

user = os.getenv("DB_USER")
password = os.getenv("DB_PASSWORD")
host = os.getenv("DB_SERVER")
port = os.getenv("DB_PORT")
database = os.getenv("DB")

MYSQL_DB = f"mysql+pymysql://{user}:{password}@{host}:{port}/{database}?charset=utf8"

# Create the SQLAlchemy engine
engine = create_engine(
    MYSQL_DB, encoding="utf-8", pool_size=20, max_overflow=0
)

# Create a SessionLocal class(not database session yet, will be)
SessionLocal = sessionmaker(
    autocommit=False, autoflush=False, bind=engine
)

Base = declarative_base()

# Dependency
def get_db():
    db = SessionLocal()
    try:
        return db
    finally:
        db.close()