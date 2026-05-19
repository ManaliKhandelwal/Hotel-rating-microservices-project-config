#Hotel Rating System — Microservices Architecture

A production-grade, distributed **Hotel Rating application** built with **Java 17**, **Spring Boot 3.x**, and **Spring Cloud**. Designed to demonstrate real-world microservices patterns including service discovery, centralized configuration, API gateway routing, and JWT-based authentication.

---

## Architecture Overview

```
                        ┌─────────────────┐
                        │  Client Request  │
                        └────────┬────────┘
                                 │
                        ┌────────▼────────┐
                        │   API Gateway   │  ← Single entry point, request routing
                        └────────┬────────┘
                                 │
              ┌──────────────────▼──────────────────┐
              │        Service Registry (Eureka)      │  ← Service discovery & registration
              └──────────────────┬──────────────────┘
                                 │
        ┌────────────┬───────────┼───────────┬────────────┐
        │            │           │            │            │
   ┌────▼────┐ ┌─────▼────┐ ┌───▼────┐ ┌────▼────┐ ┌────▼────┐
   │  User   │ │  Hotel   │ │ Rating │ │  Auth   │ │ Config  │
   │ Service │ │ Service  │ │Service │ │ Service │ │ Server  │
   └─────────┘ └──────────┘ └────────┘ └─────────┘ └─────────┘
```

Each service is **independently deployable**, communicates via **Feign Client**, and registers itself automatically with Eureka on startup.

---

## Services

| Service | Port | Responsibility |
|---|---|---|
| **ConfigServer** | 8888 | Centralized config management for all services |
| **ServiceRegistry** | 8761 | Eureka-based service discovery & registration |
| **ApiGateway** | 8080 | Routes all client requests to downstream services |
| **UserService** | 8081 | User management and profile operations |
| **HotelService** | 8082 | Hotel data — CRUD operations and hotel details |
| **RatingService** | 8083 | Hotel ratings, reviews, and aggregations |
| **AuthService** | 8084 | JWT-based authentication and authorization |

---

## Key Features

- **Microservices Architecture** — Independent, loosely coupled services for horizontal scalability
- **Service Discovery** — Automatic registration and lookup via Eureka Server
- **Centralized Configuration** — All service configs managed from a single Config Server; environment-agnostic
- **API Gateway** — Single entry point with routing, load balancing, and filter support
- **Inter-service Communication** — Feign Client for declarative, type-safe HTTP calls between services
- **JWT Authentication** — Stateless, token-based security via Auth Service
- **Request Logging** — `RequestLoggingFilter` logs method, URI, and exceptions on every request
- **Structured Logging** — SLF4J + Logback throughout all services

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x, Spring Cloud |
| Service Discovery | Netflix Eureka |
| API Gateway | Spring Cloud Gateway |
| Inter-service Calls | Feign Client |
| Configuration | Spring Cloud Config Server |
| Security | JWT, Spring Security |
| Database | MySQL |
| Build Tool | Maven 3.8+ |
| Logging | SLF4J, Logback |

---
##  Getting Started

> **Important:** Services must be started in the order below. ConfigServer and ServiceRegistry must be fully up before starting any other service.

### Step 1 — Start Config Server
```bash
cd ConfigServer
mvn spring-boot:run
```
> Runs on `http://localhost:8888` — wait until you see `Started ConfigServerApplication`

### Step 2 — Start Service Registry

> Eureka dashboard available at `http://localhost:8761`

### Step 3 — Start Core Microservices
start all independent servic(UserService / HotelService / RatingService / AuthService

### Step 4 — Start API Gateway
> All client requests go through `http://localhost:8080`

---
## API Reference

All requests go through the API Gateway at `http://localhost:8080`.

### User Service
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/users` | Create a new user |
| `GET` | `/users/{id}` | Get user by ID |
| `GET` | `/users` | Get all users |

### Hotel Service
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/hotels` | Add a new hotel |
| `GET` | `/hotels/{id}` | Get hotel by ID |
| `GET` | `/hotels` | Get all hotels |

### Rating Service
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/ratings` | Submit a hotel rating |
| `GET` | `/ratings/hotels/{hotelId}` | Get all ratings for a hotel |
| `GET` | `/ratings/users/{userId}` | Get all ratings by a user |

### Auth Service
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/login` | Authenticate and get JWT token |
| `GET` | `/auth/validate` | Validate an existing token |

---

## Logging

All services use **SLF4J + Logback** for structured logging.

The `RequestLoggingFilter` (on API Gateway) captures:
- HTTP method and URI for every incoming request
- Exception details if a request fails
- Response status codes

Log output example:
```
INFO  [ApiGateway] Incoming request: GET /hotels/123
INFO  [HotelService] Fetching hotel with id: 123
ERROR [ApiGateway] Exception on GET /ratings/999 — Hotel not found
```

---

## Startup Checklist

Before hitting any endpoint, verify all services are registered on Eureka:

1. Open `http://localhost:8761`
2. Confirm these services appear under **Instances currently registered with Eureka**:
   - `USER-SERVICE`
   - `HOTEL-SERVICE`
   - `RATING-SERVICE`
   - `AUTH-SERVICE`
   - `API-GATEWAY`

---
## Author

**Manali Khandelwal**  
Java Backend Developer | Spring Boot · Microservices · REST APIs  
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?logo=linkedin)](https://linkedin.com/in/manali-khandelwal)
