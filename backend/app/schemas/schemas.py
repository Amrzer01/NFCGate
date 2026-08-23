from pydantic import BaseModel, EmailStr
from typing import Optional, List
from datetime import datetime
from .models import Role, UserStatus, DeviceStatus, SessionStatus

# User Schemas
class UserBase(BaseModel):
    email: EmailStr
    name: str

class UserCreate(UserBase):
    password: str

class UserOut(UserBase):
    id: int
    role: Role
    status: UserStatus
    created_at: datetime
    
    class Config:
        from_attributes = True

# Device Schemas
class DeviceBase(BaseModel):
    id: str
    device_name: str
    device_model: str
    platform: str
    app_version: str
    public_key: str

class DeviceCreate(DeviceBase):
    pass

class DeviceOut(DeviceBase):
    user_id: int
    status: DeviceStatus
    last_seen: datetime
    
    class Config:
        from_attributes = True

# Auth Schemas
class Token(BaseModel):
    access_token: str
    refresh_token: str
    token_type: str = "bearer"
    user: UserOut

class LoginRequest(BaseModel):
    email: str
    password: str
