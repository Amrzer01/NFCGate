from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from contextlib import asynccontextmanager

from .database import engine, Base
from .api import auth, admin_users, admin_devices, user_me

# Create tables if they don't exist
Base.metadata.create_all(bind=engine)

@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup logic
    yield
    # Shutdown logic

app = FastAPI(
    title="NFC Control Platform API",
    description="Role-based management for NFCGate testing environments",
    version="1.0.0",
    lifespan=lifespan
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Routers
app.include_router(auth.router, prefix="/auth", tags=["Authentication"])
app.include_router(user_me.router, prefix="/me", tags=["User Profile"])
app.include_router(admin_users.router, prefix="/admin/users", tags=["Admin - Users"])
app.include_router(admin_devices.router, prefix="/admin/devices", tags=["Admin - Devices"])

@app.get("/health")
def health_check():
    return {"status": "ok", "api": "online"}
