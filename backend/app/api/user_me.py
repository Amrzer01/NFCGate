from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app.database import get_db
from app.models import models
from app.schemas import schemas
from app.api.dependencies import get_current_user

router = APIRouter()

@router.get("/", response_model=schemas.UserOut)
def read_users_me(current_user: models.User = Depends(get_current_user)):
    return current_user

@router.get("/devices", response_model=list[schemas.DeviceOut])
def read_own_devices(current_user: models.User = Depends(get_current_user), db: Session = Depends(get_db)):
    devices = db.query(models.Device).filter(models.Device.user_id == current_user.id).all()
    return devices

@router.post("/devices", response_model=schemas.DeviceOut)
def register_device(device: schemas.DeviceCreate, current_user: models.User = Depends(get_current_user), db: Session = Depends(get_db)):
    db_device = models.Device(
        id=device.id,
        user_id=current_user.id,
        device_name=device.device_name,
        device_model=device.device_model,
        platform=device.platform,
        app_version=device.app_version,
        public_key=device.public_key,
        status=models.DeviceStatus.PENDING
    )
    db.add(db_device)
    db.commit()
    db.refresh(db_device)
    return db_device
