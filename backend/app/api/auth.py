from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from datetime import timedelta

from app.database import get_db
from app.models import models
from app.schemas import schemas
from app.auth import utils

router = APIRouter()

@router.post("/login", response_model=schemas.Token)
def login(request: schemas.LoginRequest, db: Session = Depends(get_db)):
    user = db.query(models.User).filter(models.User.email == request.email).first()
    if not user or not utils.verify_password(request.password, user.password_hash):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Incorrect email or password",
            headers={"WWW-Authenticate": "Bearer"},
        )
    if user.status != models.UserStatus.ACTIVE:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Account is disabled or pending"
        )
        
    access_token_expires = timedelta(minutes=utils.ACCESS_TOKEN_EXPIRE_MINUTES)
    access_token = utils.create_access_token(
        data={"sub": user.email}, expires_delta=access_token_expires
    )
    refresh_token = utils.create_refresh_token(data={"sub": user.email})
    
    return {"access_token": access_token, "refresh_token": refresh_token, "token_type": "bearer", "user": user}

@router.post("/register", response_model=schemas.UserOut)
def register(user: schemas.UserCreate, db: Session = Depends(get_db)):
    db_user = db.query(models.User).filter(models.User.email == user.email).first()
    if db_user:
        raise HTTPException(status_code=400, detail="Email already registered")
    
    hashed_password = utils.get_password_hash(user.password)
    # Default to USER, can set ADMIN manually or via special flow if needed.
    # To demonstrate the Admin login, if email contains 'admin', we will set it to ADMIN role
    role = models.Role.ADMIN if "admin" in user.email.lower() else models.Role.USER
    
    new_user = models.User(
        email=user.email,
        name=user.name,
        password_hash=hashed_password,
        role=role,
        status=models.UserStatus.ACTIVE
    )
    db.add(new_user)
    db.commit()
    db.refresh(new_user)
    return new_user
