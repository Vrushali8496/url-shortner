# 🔗 Smart URL Shortener

A full-stack URL Shortener application built using Spring Boot that allows users to generate short and customizable links with click tracking functionality.

This project demonstrates backend development, REST API design, database integration, and frontend interaction.

---

## 🚀 Features

- 🔹 Convert long URLs into short URLs instantly
- 🔹 Custom short codes (e.g. `/mygithub`)
- 🔹 Automatic random short code generation
- 🔹 Click tracking & analytics
- 🔹 HTTP 302 redirection
- 🔹 RESTful API endpoints
- 🔹 Input validation and error handling
- 🔹 Responsive UI using Bootstrap

---

## 🛠 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- H2 / MySQL Database

### Frontend
- HTML
- Bootstrap
- JavaScript

### Tools & Version Control
- Git
- GitHub
- VS Code

---

## 📌 API Endpoints

### 1️⃣ Create Short URL

**POST** `/shorten`

Request Body:
```json
{
  "url": "https://example.com",
  "customCode": "mycustom"
}
```

Response:
```json
{
  "shortUrl": "http://localhost:8080/r/mycustom"
}
```

---

### 2️⃣ Redirect to Original URL

**GET** `/r/{shortCode}`

Redirects to the original URL using HTTP 302 status.

---

### 3️⃣ Get Click Statistics

**GET** `/stats/{shortCode}`

Response:
```json
{
  "originalUrl": "https://example.com",
  "clickCount": 5
}
```

---

## 💻 How to Run Locally

1. Clone the repository:

```
git clone https://github.com/Vrushali8496/url-shortner.git
```

2. Navigate into project folder:

```
cd url-shortner
```

3. Run the Spring Boot application.

4. Open in browser:

```
http://localhost:8080
```

---

## 🧠 Project Highlights

- Implemented unique short code validation using database constraints
- Used JPA Repository for database operations
- Implemented REST APIs following best practices
- Applied proper HTTP status codes (200, 302, 400, 404)
- Integrated frontend with backend using Fetch API
- Designed clean and responsive UI

---

## 📈 Future Enhancements

- User authentication system
- Expiry date for short links
- QR code generation
- Base62 encoding instead of UUID
- Cloud deployment (Render / Railway / AWS)
- Custom domain integration

---

## 👩‍💻 Author

**Vrushali Thorat**

GitHub: https://github.com/Vrushali8496

---

⭐ If you found this project useful, consider giving it a star!
