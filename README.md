# Dealer Vehicle Inventory — Microservice

A **multitenant REST API** for managing dealers and their vehicle inventories, built with **Spring Boot 3.3.2** following **Clean Architecture (Hexagonal)** principles.

---

## Table of Contents

- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Running Locally](#running-locally)
- [Running with Docker](#running-with-docker)
- [Multitenancy](#multitenancy)
- [API Reference](#api-reference)
- [Postman Examples](#postman-examples)

---

## Architecture

The project strictly follows **Clean Architecture** (Ports & Adapters / Hexagonal):

```
src/main/java/com/dealersautocenter/inventory/
│
├── domain/                         # Core business — no framework dependencies
│   ├── model/                      # Pure POJOs: Dealer, Vehicle
│   ├── enums/                      # SubscriptionType, VehicleStatus
│   └── exception/                  # Business exceptions
│
├── application/                    # Use case orchestration
│   ├── port/
│   │   ├── in/                     # Input ports (interfaces): DealerUseCase, VehicleUseCase
│   │   └── out/                    # Output ports (interfaces): DealerRepositoryPort, TenantPort…
│   └── service/                    # Implementations of use cases
│
└── infrastructure/                 # Framework-specific adapters
    ├── persistence/                 # JPA entities, adapters, Spring Data repos
    ├── web/                         # REST controllers, DTOs, mappers
    ├── security/                    # Tenant filter, role aspect, resolvers
    └── config/                      # Spring bean wiring
```

### Dependency Rule
```
Web → Application ← Persistence
         ↓
       Domain  (no deps on Spring/JPA/Web)
```

---

## Tech Stack

| Component | Technology |
|---|---|
| Framework | Spring Boot 3.3.2 |
| Language | Java 17 |
| Build | Maven 3.9 |
| ORM | Spring Data JPA / Hibernate 6 |
| Database | H2 (in-memory, dev) |
| Validation | Jakarta Bean Validation |
| Security | Custom AOP + Filter (stateless) |
| Containers | Docker + Docker Compose |

---

## Prerequisites

- **Java 17+** — `java -version`
- **Maven 3.9+** — `mvn -version`
- **Docker + Docker Compose** — `docker --version`

---

## Running Locally

```bash
# 1. Clone the repository
git clone https://github.com/joelproxi/inventory-spring-app
cd inventory-spring-app/inventory

# 2. Build and run
mvn spring-boot:run

# 3. Access the app
# API base:      http://localhost:8080
# H2 Console:    http://localhost:8080/h2-console
#   JDBC URL:    jdbc:h2:mem:inventorydb
#   User: sa / Password: (empty)
```

---

## Running with Docker

```bash
# Build and start
docker-compose up --build

# Run in background
docker-compose up --build -d

# Stop
docker-compose down
```

The app will be available at `http://localhost:8080`.

To rebuild after code changes:
```bash
docker-compose up --build --force-recreate
```

---

## Multitenancy

Every request **must** include the following HTTP headers:

| Header | Required | Example | Description |
|---|---|---|---|
| `X-Tenant-Id` | ✅ Yes | `paris-dealer-01` | Tenant identifier — isolates data per tenant |
| `X-User-Role` | ⬜ No | `TENANT_USER` | User role — defaults to `TENANT_USER` |

### Available Roles

| Role | Access |
|---|---|
| `TENANT_USER` | Standard CRUD access on their tenant's data |
| `GLOBAL_ADMIN` | Access to admin endpoints (e.g., subscription stats) |

### How It Works

1. The `TenantFilter` (`OncePerRequestFilter`) reads the headers on every request.
2. The `TenantContext` (ThreadLocal) stores the current tenant ID and user role.
3. All repository queries are automatically scoped to the current tenant.
4. The `@RequireRole` AOP annotation enforces role-based access on protected endpoints.
5. The context is **always cleared** in a `finally` block to prevent memory leaks.

---

## API Reference

### Dealers

| Method | Endpoint | Body | Description |
|---|---|---|---|
| `POST` | `/dealers` | `CreateDealerRequest` | Create a new dealer |
| `GET` | `/dealers` | — | List dealers (paginated) |
| `GET` | `/dealers/{id}` | — | Get a dealer by ID |
| `PATCH` | `/dealers/{id}` | `UpdateDealerRequest` | Partially update a dealer |
| `DELETE` | `/dealers/{id}` | — | Delete a dealer |

### Vehicles

| Method | Endpoint | Query Params | Description |
|---|---|---|---|
| `POST` | `/vehicles` | — | Create a vehicle |
| `GET` | `/vehicles` | `model`, `status`, `priceMin`, `priceMax`, `subscription` | List vehicles with filters |
| `GET` | `/vehicles/{id}` | — | Get a vehicle by ID |
| `PATCH` | `/vehicles/{id}` | — | Partially update a vehicle |
| `DELETE` | `/vehicles/{id}` | — | Delete a vehicle |

### Admin

| Method | Endpoint | Required Role | Description |
|---|---|---|---|
| `GET` | `/admin/stats/subscriptions` | `GLOBAL_ADMIN` | Count dealers grouped by subscription type |

---

### Request & Response Models

#### `CreateDealerRequest`
```json
{
  "name": "Dealers Auto Center Paris",
  "email": "contact@dealers-paris.fr",
  "subscriptionType": "PREMIUM"
}
```

#### `UpdateDealerRequest`
```json
{
  "name": "New Name",
  "email": "new@email.fr",
  "subscriptionType": "BASIC"
}
```

#### `DealerResponse`
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "tenantId": "paris-dealer-01",
  "name": "Dealers Auto Center Paris",
  "email": "contact@dealers-paris.fr",
  "subscriptionType": "PREMIUM",
  "createdAt": "2026-03-28T09:00:00",
  "updatedAt": "2026-03-28T09:00:00"
}
```

#### `CreateVehicleRequest`
```json
{
  "dealerId": "550e8400-e29b-41d4-a716-446655440000",
  "model": "Tesla Model 3",
  "price": 42500.00,
  "status": "AVAILABLE"
}
```

#### `VehicleResponse`
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "tenantId": "paris-dealer-01",
  "dealerId": "550e8400-e29b-41d4-a716-446655440000",
  "model": "Tesla Model 3",
  "price": 42500.00,
  "status": "AVAILABLE",
  "createdAt": "2026-03-28T09:05:00",
  "updatedAt": "2026-03-28T09:05:00"
}
```

#### Enums

**`SubscriptionType`**: `BASIC` | `PREMIUM`

**`VehicleStatus`**: `AVAILABLE` | `SOLD`

---

### Error Responses

| HTTP Status | Scenario |
|---|---|
| `400 Bad Request` | Validation error |
| `403 Forbidden` | Insufficient role |
| `404 Not Found` | Resource not found |
| `409 Conflict` | Duplicate resource (e.g., email within tenant) |

---

## Postman Examples

### 1. Create a Dealer
```
POST http://localhost:8080/dealers
Headers:
  X-Tenant-Id: tenant-a
  X-User-Role: TENANT_USER
  Content-Type: application/json

Body:
{
  "name": "Auto Paris",
  "email": "contact@autoparis.fr",
  "subscriptionType": "PREMIUM"
}
```

### 2. List All Dealers for a Tenant
```
GET http://localhost:8080/dealers
Headers:
  X-Tenant-Id: tenant-a
```

### 3. Create a Vehicle
```
POST http://localhost:8080/vehicles
Headers:
  X-Tenant-Id: tenant-a
  Content-Type: application/json

Body:
{
  "dealerId": "<uuid-from-step-1>",
  "model": "Peugeot 308",
  "price": 28500.00,
  "status": "AVAILABLE"
}
```

### 4. Filter Vehicles
```
GET http://localhost:8080/vehicles?status=AVAILABLE&priceMax=30000
Headers:
  X-Tenant-Id: tenant-a
```

### 5. Admin — Subscription Stats
```
GET http://localhost:8080/admin/stats/subscriptions
Headers:
  X-Tenant-Id: tenant-a
  X-User-Role: GLOBAL_ADMIN
```

### 6. cURL Quick Test
```bash
# Create dealer
curl -s -X POST http://localhost:8080/dealers \
  -H "X-Tenant-Id: demo" \
  -H "Content-Type: application/json" \
  -d '{"name":"Demo Dealer","email":"demo@dealer.fr","subscriptionType":"BASIC"}' | jq .

# List dealers
curl -s http://localhost:8080/dealers \
  -H "X-Tenant-Id: demo" | jq .
```
