# DevCollab - Backend API

Production-ready REST API for a team project management platform.

## Tech Stack
- Java 17 + Spring Boot 4
- Spring Security + JWT Authentication
- MongoDB + Spring Data
- Docker (multi-stage build)
- GitHub Actions CI
- Deployed on Railway + MongoDB Atlas

## Architecture
Controller → Service → Repository → MongoDB

## Key Features
- JWT authentication with BCrypt password hashing
- Role-based access control (ADMIN / MEMBER)
- DTO pattern with input validation
- Global exception handling
- Dockerized with multi-stage Maven build

## Local Setup

### Prerequisites
- Java 17
- Maven
- MongoDB running locally or Atlas URI

### Run locally
```bash
# Clone the repo
git clone https://github.com/ashan24019/Dev-Collab.git

# Set environment variables
export SPRING_MONGODB_URI=mongodb://localhost:27017/devcollab
export JWT_SECRET=your-secret-here

# Run
mvn spring-boot:run
```

## API Endpoints

### Auth
- `POST /api/auth/register` — Register new user
- `POST /api/auth/login` — Login, returns JWT token

### Projects (requires auth)
- `GET /api/projects` — Get all projects
- `POST /api/projects` — Create project
- `GET /api/projects/{id}` — Get project by ID
- `PATCH /api/projects/{id}/members/{userId}` — Add member (ADMIN only)

### Tasks (requires auth)
- `GET /api/tasks/project/{projectId}` — Get tasks by project
- `POST /api/tasks` — Create task
- `PATCH /api/tasks/{id}` — Update task
- `DELETE /api/tasks/{id}` — Delete task (ADMIN only)

## Live Demo
Backend API: https://dev-collab-production-bb15.up.railway.app
Frontend: https://dev-collab-frontend-production.up.railway.app