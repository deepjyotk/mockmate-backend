# Mock Mate Backend Service

Mock Mate is a peer-to-peer mock interview platform that enables users to book slots, get matched with peers, and practice both behavioral and software engineering interviews. This repository contains the backend service for Mock Mate, built using **Spring Boot**, **PostgreSQL**, **Docker**, and **AWS (ECS)**.

## 🚀 Features
- **User Authentication** (Registration, Login, Forgot Password)
- **Slot Booking System** (Behavioral & Technical Interviews)
- **Peer Matching Algorithm** (Intelligent matching based on difficulty level)
- **Video Interview Integration** (Using Zego UI Toolkit)
- **Post-Interview Feedback** (Users provide feedback and review past interviews)
- **WebSocket-based Real-time Communication** (For live updates)

---

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot
- **Database:** PostgreSQL
- **Containerization:** Docker
- **Deployment:** AWS ECS
- **API Documentation:** Swagger
- **Logging & Monitoring:** Spring Boot Actuator

---

## 📂 Project Structure
```
mock-mate-backend/
│── mockmate-api/       # Main backend API for authentication, booking, and interview management
│── mockmate-ws/        # WebSocket service for real-time updates and peer matching
│── deployment/         # Deployment configurations including Docker and Nginx setup
│── docker-compose.yml  # Docker setup for local development
│── README.md           # Documentation
```

---

## 🚀 Getting Started

### 1️⃣ Prerequisites
Ensure you have the following installed on your system:
- Java 17+
- PostgreSQL
- Docker
- Maven

### 2️⃣ Clone the Repository
```sh
git clone https://github.com/your-repo/mock-mate-backend.git
cd mock-mate-backend
```

### 3️⃣ Setup Environment Variables
Create an `.env` file in the `deployment/` directory with the following variables:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/mockmate
SPRING_DATASOURCE_USERNAME=your_db_username
SPRING_DATASOURCE_PASSWORD=your_db_password
JWT_SECRET=your_secret_key
AWS_ACCESS_KEY=your_aws_access_key
AWS_SECRET_KEY=your_aws_secret_key
```

### 4️⃣ Running the Application
#### **Using Docker Compose**
```sh
docker-compose up --build
```
#### **Without Docker**
```sh
mvn clean install
java -jar target/mockmate-api.jar
java -jar target/mockmate-ws.jar
```

---

## 📌 API Endpoints
| Endpoint                 | Method | Description                         |
|--------------------------|--------|-------------------------------------|
| `/auth/register`         | POST   | Register a new user                |
| `/auth/login`            | POST   | User login with JWT authentication |
| `/slots/book`            | POST   | Book a mock interview slot         |
| `/match/waiting-room`    | GET    | Fetch available interview slots    |
| `/match/pair`            | POST   | Pairs users for mock interviews    |
| `/interview/start`       | POST   | Start a video interview session    |
| `/feedback/submit`       | POST   | Submit feedback after an interview |

---

## 🔄 WebSocket Communication
The **WebSocket Service (`mockmate-ws`)** handles real-time peer matching and live interview updates.
### WebSocket Endpoints:
| Endpoint                 | Description                           |
|--------------------------|-------------------------------------|
| `/ws/connect`            | Connect to WebSocket                |
| `/ws/match`              | Receive live match updates          |
| `/ws/interview/live`     | Handle live interview interactions  |

---

## 📌 Peer Matching Algorithm
The **Peer Matching Algorithm** pairs users based on:
1. **Difficulty Preference:** Users choose **Easy (E), Medium (M), or Hard (H)**.
2. **Pairing Logic:**
    - Users with the same difficulty are paired together first.
    - If an exact match isn’t available, the next closest match is chosen.

Example:
```
Room: E1, E2, M1, H1, H2
Pairs: {E1, E2}, {M1, H1}, {H2 waits}
```

A cron job runs at fixed intervals to match pending users.

---

## 📌 Future Enhancements
✅ **WebSockets for real-time updates** (instead of short polling)
✅ **Collaborative coding editor** for software engineering interviews
✅ **In-app notifications** for slot availability & peer matching
✅ **Interview analytics dashboard** to track progress over time

---

## 🔐 Security & Authentication
- JWT-based authentication for user sessions.
- Secure API endpoints using Spring Security.
- Password hashing using **BCrypt**.

---

## 📄 License
This project is licensed under the MIT License.

---

## 🤝 Contributing
1. **Fork** the repo
2. **Create a new branch** (`feature-xyz`)
3. **Commit your changes**
4. **Submit a pull request**

We welcome contributions from the community! 🚀
