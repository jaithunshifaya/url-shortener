# 🔗 URL Shortener Application

A full-stack **URL Shortener Application** built using **Spring Boot (Backend)** and **React (Frontend)** with **JWT Authentication**, **Custom URLs**, **QR Code generation**, and **Analytics tracking**.

---

## 🚀 Tech Stack

### 🔹 Backend
- Java 17
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA
- MySQL
- Maven

### 🔹 Frontend
- React JS
- Axios
- CSS

### 🔹 DevOps
- Docker

---

## 📁 Project Structure

```bash
url-shortener/
│
├── backend/
│   ├── src/main/java/com/urlShortener/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── security/
│   │   └── UrlShortenerApplication.java
│   │
│   ├── src/main/resources/
│   │   └── application.properties
│   │
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── App.jsx
│   │   └── App.css
│   │
│   ├── public/
│   └── package.json
│
└── README.md
```

---

## 🔐 Authentication

- Uses **JWT (Bearer Token) Authentication**
- Secure APIs require token in header:

```bash
Authorization: Bearer <your_token>
```

---

## 🌐 API Endpoints

### 🔑 Authentication

| Method | Endpoint | Description |
|--------|---------|------------|
| POST   | /auth/register | Register new user |
| POST   | /auth/login | Login user |

---

### 🔗 URL Management

| Method | Endpoint | Description |
|--------|---------|------------|
| POST   | /shorten | Shorten URL |
| POST   | /custom | Create custom short URL |
| GET    | /{shortCode} | Redirect to original URL |
| GET    | /my-urls | Get user's URLs |
| DELETE | /{shortCode} | Delete URL |

---

### 📊 Advanced Features

| Method | Endpoint | Description |
|--------|---------|------------|
| POST   | /qr/{shortCode} | Generate QR code |
| POST   | /analytics/{shortCode} | Get URL analytics |

---

## ⚙️ Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

---

## ⚙️ Frontend Setup

```bash
cd frontend
npm install
npm start
```

---

## 🐳 Docker Setup

### Create Network

```bash
docker network create my-network
```

### Run MySQL

```bash
docker run -d ^
--name mysql-container ^
--network my-network ^
-e MYSQL_ROOT_PASSWORD=root ^
-e MYSQL_DATABASE=url_shortener ^
-p 3307:3306 ^
mysql:8
```

### Run Backend

```bash
docker build -t url-shortener-backend .
docker run -p 8081:8080 --network my-network url-shortener-backend
```

---

## ✨ Features

- 🔐 JWT Authentication
- 🔗 URL Shortening
- ✏️ Custom Short URLs
- 📊 URL Analytics
- 📱 QR Code Generation
- 🗑️ URL Management (Delete)
- 🌐 RESTful APIs
- 🐳 Docker Support

---

## 🚀 Future Enhancements

- ☁️ Cloud Deployment (AWS / Render / Railway)
- 🤖 AI-based URL insights & recommendations
- 📈 Advanced analytics dashboard
- 📊 Real-time click tracking visualization

---

## 👨‍💻 Author

**Jaithun Shifaya**

---
