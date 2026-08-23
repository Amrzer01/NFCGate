from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from app.database import get_db
from app.models import models
from app.schemas import schemas
from app.api.dependencies import get_current_admin

router = APIRouter()

@router.get("/", response_model=list[schemas.UserOut])
def read_users(skip: int = 0, limit: int = 100, db: Session = Depends(get_db), current_admin: models.User = Depends(get_current_admin)):
    users = db.query(models.User).offset(skip).limit(limit).all()
    return users

@router.post("/{user_id}/disable")
def disable_user(user_id: int, db: Session = Depends(get_db), current_admin: models.User = Depends(get_current_admin)):
    user = db.query(models.User).filter(models.User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    user.status = models.UserStatus.DISABLED
    db.commit()
    
    # Audit log
    audit = models.AuditLog(
        admin_id=current_admin.id,
        action="disabled_user",
        target_type="user",
        target_id=str(user.id)
    )
    db.add(audit)
    db.commit()
    
    return {"message": f"User {user_id} disabled successfully"}

@router.post("/{user_id}/activate")
def activate_user(user_id: int, db: Session = Depends(get_db), current_admin: models.User = Depends(get_current_admin)):
    user = db.query(models.User).filter(models.User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    user.status = models.UserStatus.ACTIVE
    db.commit()
    
    audit = models.AuditLog(
        admin_id=current_admin.id,
        action="activated_user",
        target_type="user",
        target_id=str(user.id)
    )
    db.add(audit)
    db.commit()
    
    return {"message": f"User {user_id} activated successfully"}
