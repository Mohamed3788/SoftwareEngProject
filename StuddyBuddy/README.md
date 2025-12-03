# StudyBuddy - Learning Management System

A modern, full-stack Learning Management System built with React (Vite + Tailwind CSS) frontend and Spring Boot backend.

## ✨ Features

- **Role-Based Access Control**: Separate dashboards and features for Students, Teachers, and Admins
- **Course Management**: Create, enroll, and manage courses
- **Assignments & Grading**: Submit assignments, grade submissions
- **Real-Time Discussion**: WebSocket-based course discussions
- **Module Management**: Organize course content into modules
- **File Upload**: Support for assignment submissions and course materials
- **Secure Authentication**: JWT-based authentication with role-based authorization

## 🛠️ Tech Stack

### Frontend
- **React 18** - UI library
- **Vite** - Build tool and dev server
- **Tailwind CSS** - Utility-first CSS framework
- **React Router** - Client-side routing
- **Axios** - HTTP client with interceptors

### Backend
- **Spring Boot 3.2.0** - Application framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Database
- **JWT** - Token-based authentication
- **WebSocket** - Real-time communication
- **Maven** - Build tool

## 📋 Prerequisites

- **Java 17** or higher
- **Node.js 18+** and npm
- **PostgreSQL 12+**

## 🚀 Getting Started (Windows)

### 1. Database Setup

1. **Install PostgreSQL** (if not already installed)
   - Download from: https://www.postgresql.org/download/windows/

2. **Ensure PostgreSQL is running**
   ```powershell
   # Check if PostgreSQL service is running
   sc query postgresql-x64-15  # Adjust version number
   
   # Start if not running
   net start postgresql-x64-15
   ```

3. **Create Database** (if using a different database name)
   ```sql
   -- The application is configured to use "postgres" database by default
   -- If you want a separate database:
   CREATE DATABASE studybuddy_db;
   ```

### 2. Configure Database Connection

Edit `StudyBuddy\src\main\resources\application.yml`:

```yaml
datasource:
  url: jdbc:postgresql://localhost:5432/postgres  # Your database name
  username: postgres                               # Your PostgreSQL username  
  password: your_password_here                     # Your PostgreSQL password
```

**⚠️ IMPORTANT**: Change the hardcoded password before running!

### 3. Start the Backend

Open Command Prompt or PowerShell in the project folder:

```powershell
cd StudyBuddy
mvnw.cmd clean spring-boot:run
```

Wait for the message: `Started StudyBuddyApplication in X.XXX seconds`

Backend runs on: **http://localhost:8080**

### 4. Start the Frontend

Open a **NEW** terminal window:

```powershell
# Install dependencies (first time only)
npm install

# Start development server
npm run dev
```

Frontend runs on: **http://localhost:3000**

### 5. Access the Application

Open your browser and navigate to: **http://localhost:3000**

## 👤 User Roles & Accounts

### Creating Your First Admin Account

1. Navigate to **Sign Up → Admin**
2. Enter your details
3. Use admin secret: `@dmin` (default)
4. Login with your created credentials

### Creating Student/Teacher Accounts

- **Students**: Sign Up → Student
- **Teachers**: Sign Up → Teacher

## 📁 Project Structure

```
aitlhcen/
├── src/                          # Frontend (React + Vite)
│   ├── api/                     # API client functions
│   ├── components/              # Reusable components
│   ├── context/                 # React Context (Auth)
│   ├── hooks/                   # Custom React hooks
│   ├── layouts/                 # Layout components
│   ├── pages/                   # Page components
│   │   ├── admin/              # Admin pages
│   │   ├── student/            # Student pages
│   │   ├── teacher/            # Teacher pages
│   │   ├── course/             # Course pages
│   │   └── common/             # Shared pages
│   ├── App.jsx                  # Main App component
│   ├── main.jsx                 # Entry point
│   └── index.css                # Tailwind CSS
│
├── StudyBuddy/                  # Backend (Spring Boot)
│   ├── src/main/java/           # Java source code
│   │   └── com/university/lms/StudyBuddy/
│   │       ├── admin/          # Admin module
│   │       ├── assignment/     # Assignments & submissions
│   │       ├── auth/           # Authentication & JWT
│   │       ├── config/         # Spring configuration
│   │       ├── course/         # Course management
│   │       ├── dashboard/      # Dashboard events
│   │       ├── discussion/     # Discussion/WebSocket
│   │       ├── file/           # File upload/storage
│   │       ├── grades/         # Grading system
│   │       ├── module/         # Course modules
│   │       ├── todo/           # Todo tasks
│   │       └── user/           # User management
│   └── src/main/resources/
│       └── application.yml      # Application configuration
│
├── package.json                 # Frontend dependencies
├── vite.config.js              # Vite configuration
├── tailwind.config.js          # Tailwind CSS configuration
└── README.md                    # This file
```

## 🔧 Available Scripts

### Frontend

```bash
npm install          # Install dependencies
npm run dev          # Start development server (port 3000)
npm run build        # Build for production
npm run preview      # Preview production build
```

### Backend

```powershell
cd StudyBuddy
mvnw.cmd clean                    # Clean build artifacts
mvnw.cmd spring-boot:run          # Run application
mvnw.cmd clean package            # Build JAR file
mvnw.cmd test                     # Run tests
```

## 🌐 API Endpoints

Base URL: `http://localhost:8080/api`

### Authentication
- `POST /auth/signup` - Admin signup
- `POST /auth/signup-student` - Student signup
- `POST /auth/signup-teacher` - Teacher signup
- `POST /auth/login` - Login (returns JWT token)

### Courses
- `GET /courses` - Get user's courses
- `POST /courses` - Create course (Teacher/Admin)
- `GET /courses/{id}` - Get course details

### Assignments
- `GET /courses/{id}/assignments` - Get course assignments
- `POST /courses/{id}/assignments` - Create assignment (Teacher)
- `POST /assignments/{id}/submit` - Submit assignment (Student)

### And more... (See controllers for full API documentation)

## 🛡️ Security Notes

**⚠️ IMPORTANT**: This application contains several security vulnerabilities documented in `StudyBuddy/ISSUES_REPORT.md`

**DO NOT USE IN PRODUCTION** without addressing:
1. Hardcoded database password
2. Hardcoded JWT secret
3. Weak admin secret
4. File upload vulnerabilities
5. CORS configuration
6. And others...

This is a **learning/development project**. For production use, implement proper security measures.

## ⚙️ Configuration

### Frontend Proxy (Vite)

The frontend proxies `/api` requests to the backend automatically:

```javascript
// vite.config.js
server: {
  port: 3000,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

### Database Schema

Tables are auto-created on first run via Hibernate `ddl-auto: update`. Schema includes:
- Users (students, teachers, admins)
- Courses & enrollments
- Assignments & submissions
- Modules & module items
- Discussion messages
- Dashboard events
- Todo tasks

## 🐛 Troubleshooting

### Backend Won't Start

**Issue**: Port 8080 already in use
```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID)
taskkill /PID <PID> /F
```

**Issue**: Database connection failed
- Ensure PostgreSQL is running
- Check credentials in `application.yml`
- Verify database exists

### Frontend Won't Start

**Issue**: Port 3000 already in use
```powershell
# Find and kill process on port 3000
netstat -ano | findstr :3000
taskkill /PID <PID> /F
```

**Issue**: npm install fails
- Clear npm cache: `npm cache clean --force`
- Delete `node_modules` and `package-lock.json`
- Run `npm install` again

### CORS Errors

- Ensure both frontend and backend are running
- Check CORS configuration in `WebMvcConfig.java`
- Verify Vite proxy in `vite.config.js`

## 📝 Default Credentials

- **Admin Secret**: `@dmin` (configured in `AuthService.java`)
- **Database**: `postgres` / `your_password`

Change these in production!

## 🤝 Contributing

This is an educational project. Feel free to fork and improve!

## 📄 License

This project is for educational purposes.

## 🙏 Acknowledgments

- Built with Spring Boot and React
- Styled with Tailwind CSS
- Inspired by Canvas LMS

---

**Happy Learning! 📚**
