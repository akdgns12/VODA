from fastapi import APIRouter
from .controller import (
    diary,
)

router = APIRouter()
router.include_router(diary.router, tags=["diary"])