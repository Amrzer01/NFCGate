import enum
from datetime import datetime
from sqlalchemy import Column, Integer, String, Boolean, Enum, ForeignKey, DateTime, JSON
from sqlalchemy.orm import relationship
from app.database import Base

class Role(str, enum.Enum):
    ADMIN = "ADMIN"
    USER = "USER"

class UserStatus(str, enum.Enum):
    ACTIVE = "ACTIVE"
    DISABLED = "DISABLED"
    PENDING = "PENDING"

class DeviceStatus(str, enum.Enum):
    PENDING = "PENDING"
    APPROVED = "APPROVED"
    REVOKED = "REVOKED"
    BLOCKED = "BLOCKED"

class SessionStatus(str, enum.Enum):
    CREATED = "CREATED"
    WAITING = "WAITING"
    ACTIVE = "ACTIVE"
    ENDED = "ENDED"
    FAILED = "FAILED"

class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    email = Column(String, unique=True, index=True, nullable=False)
    password_hash = Column(String, nullable=False)
    name = Column(String, nullable=False)
    role = Column(Enum(Role), default=Role.USER, nullable=False)
    status = Column(Enum(UserStatus), default=UserStatus.ACTIVE, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)

    devices = relationship("Device", back_populates="owner")

class Device(Base):
    __tablename__ = "devices"

    id = Column(String, primary_key=True, index=True) # E.g., DEV_8F31
    user_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    device_name = Column(String, nullable=False) # E.g., Samsung S23
    device_model = Column(String, nullable=False)
    platform = Column(String, default="Android")
    app_version = Column(String, nullable=False)
    public_key = Column(String, nullable=False)
    status = Column(Enum(DeviceStatus), default=DeviceStatus.PENDING, nullable=False)
    last_seen = Column(DateTime, default=datetime.utcnow)
    created_at = Column(DateTime, default=datetime.utcnow)

    owner = relationship("User", back_populates="devices")

class Session(Base):
    __tablename__ = "sessions"

    id = Column(Integer, primary_key=True, index=True)
    reader_device_id = Column(String, ForeignKey("devices.id"), nullable=False)
    tag_device_id = Column(String, ForeignKey("devices.id"), nullable=False)
    status = Column(Enum(SessionStatus), default=SessionStatus.CREATED, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)
    started_at = Column(DateTime, nullable=True)
    ended_at = Column(DateTime, nullable=True)

class AuditLog(Base):
    __tablename__ = "audit_logs"

    id = Column(Integer, primary_key=True, index=True)
    admin_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    action = Column(String, nullable=False) # e.g. "approved_device"
    target_type = Column(String, nullable=False) # e.g. "device"
    target_id = Column(String, nullable=False)
    metadata_json = Column(JSON, nullable=True)
    created_at = Column(DateTime, default=datetime.utcnow)
