from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from app.database import get_db
from app.models import models
from app.schemas import schemas
from app.api.dependencies import get_current_admin

router = APIRouter()

@router.get("/", response_model=list[schemas.DeviceOut])
def read_devices(skip: int = 0, limit: int = 100, db: Session = Depends(get_db), current_admin: models.User = Depends(get_current_admin)):
    devices = db.query(models.Device).offset(skip).limit(limit).all()
    return devices

@router.post("/{device_id}/approve")
def approve_device(device_id: str, db: Session = Depends(get_db), current_admin: models.User = Depends(get_current_admin)):
    device = db.query(models.Device).filter(models.Device.id == device_id).first()
    if not device:
        raise HTTPException(status_code=404, detail="Device not found")
    device.status = models.DeviceStatus.APPROVED
    db.commit()
    
    audit = models.AuditLog(
        admin_id=current_admin.id,
        action="approved_device",
        target_type="device",
        target_id=device_id
    )
    db.add(audit)
    db.commit()
    
    return {"message": f"Device {device_id} approved successfully"}

@router.post("/{device_id}/revoke")
def revoke_device(device_id: str, db: Session = Depends(get_db), current_admin: models.User = Depends(get_current_admin)):
    device = db.query(models.Device).filter(models.Device.id == device_id).first()
    if not device:
        raise HTTPException(status_code=404, detail="Device not found")
    device.status = models.DeviceStatus.REVOKED
    db.commit()
    
    audit = models.AuditLog(
        admin_id=current_admin.id,
        action="revoked_device",
        target_type="device",
        target_id=device_id
    )
    db.add(audit)
    db.commit()
    
    return {"message": f"Device {device_id} revoked successfully"}
