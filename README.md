# Portfolio Web Application (Full-Stack)
This is my **personal portfolio website**, consisting of **frontend (Vue.js)** and **backend (Java Spring Boot)** components.  
It demonstrates my skills as a **Full-Stack Developer**, including building REST APIs, integrating with databases, handling frontend interactivity, and deploying applications. 
You can view the live version here: [https://www.svitlana-kashkina.com](https://www.svitlana-kashkina.com)

---

## Features

### Backend
- REST API for managing portfolio projects
- Contact form messages saved in PostgreSQL
- Structured architecture:
  - `controller/` — REST endpoints
  - `service/` — business logic
  - `repository/` — database access (PostgreSQL integration)
  - `entity/` — JPA entities
  - `dto/` — data transfer objects
  - `exception/` — custom exceptions
  - `config/` — configuration (includes database, Redis, Kafka settings)
  - `redis/` — Redis configuration and caching
  - `kafka/` — Kafka producers and consumers
  - `docker/` — Dockerfile and Docker Compose YAML for containerization
- Global exception handling
- Logging setup
- Email notifications using Spring Mail
- Caching and messaging: Redis & Kafka
- File storage with Amazon S3 (AWS)
- Docker support for containerization (Dockerfile & Docker Compose)
- Unit testing with JUnit

### Frontend
- Responsive UI using **Vue.js**
- JavaScript (ES6+) for dynamic functionality
- Axios for API communication with backend
- Global exception handling and error messages
- Loading spinners for asynchronous operations
- Component-based architecture for reusable UI elements
- Routing using Vue Router
- Form validations and interactive portfolio components

---

## Technologies Used

### Backend
- Java 17
- Spring Boot
- Hibernate / Spring Data JPA
- PostgreSQL
- Lombok
- Spring Mail
- Redis
- Kafka
- Docker
- Maven
- Amazon S3 (AWS)
- IntelliJ IDEA

### Frontend
- JavaScript (ES6+)
- Vue.js 3
- Vue Router
- Axios
- HTML / CSS
- Global exception handling
- Spinner component
- Visual Studio Code

### Tools & Platforms
- GitHub (version control)
- Maven (backend build tool)
- Docker (containerization)
- PostgreSQL (database)
- AWS S3 (cloud storage)
- IntelliJ IDEA, Visual Studio Code

---

## Prerequisites

- Java 17
- Maven
- PostgreSQL
- Node.js & npm (for frontend)

---
## Links

- **Frontend Repository:** [GitHub Frontend](https://github.com/SvitlanaKashkina/portfolio-frontend-vue-javascript)
- **Backend Repository:** [GitHub Backend](https://github.com/SvitlanaKashkina/portfolio-backend-java-springboot)
- **Live Demo / Portfolio Website:** [https://www.svitlana-kashkina.com](https://www.svitlana-kashkina.com)
