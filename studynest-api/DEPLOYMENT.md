# 🚀 StudyNest Deployment Guide

This guide explains how to deploy **StudyNest - Study Center Seat Booking Portal** (`studynest-api`) using various deployment options.

---

## 🛠️ Method 1: Deploy on Render.com (Recommended Free Cloud Hosting)

Render provides free hosting for Docker containers and web services.

### Steps:
1. **Push to GitHub / GitLab**:
   Create a new repository on GitHub and push the `studynest-api` folder code:
   ```bash
   git init
   git add .
   git commit -m "Initial commit for StudyNest"
   git remote add origin https://github.com/YOUR_USERNAME/studynest-api.git
   git push -u origin main
   ```

2. **Deploy on Render**:
   - Go to [Render Dashboard](https://dashboard.render.com).
   - Click **New +** -> **Web Service**.
   - Connect your GitHub repository (`studynest-api`).
   - Select **Docker** as the Runtime.
   - Click **Create Web Service**.

Render will automatically build using the included `Dockerfile` and give you a live HTTPS URL (e.g. `https://studynest-api.onrender.com`)!

---

## 🚂 Method 2: Deploy on Railway.app

1. Sign up on [Railway.app](https://railway.app).
2. Click **New Project** -> **Deploy from GitHub Repo**.
3. Select `studynest-api`.
4. Railway detects `Dockerfile` automatically and deploys the project instantly.

---

## 🐳 Method 3: Deploy using Docker Compose (Local / Server / VPS)

If you have Docker installed on your machine or VPS (AWS / DigitalOcean / Linode):

```bash
# Navigate to studynest-api directory
cd studynest-api

# Build and start container in detached mode
docker-compose up -d --build
```
Access the application at `http://localhost:8080`.

---

## 💻 Method 4: Local Run (Spring Boot)

Run using Maven Wrapper:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```
Open your browser at `http://localhost:8080`.
