# Distributed Notification Service

A production-grade distributed notification platform built using Java 21 and Spring Boot.

The system supports asynchronous notification delivery using Kafka, the Transactional Outbox Pattern, PostgreSQL, Redis, and Docker.

---

## Tech Stack

- Java 21
- Spring Boot 3.x
- PostgreSQL
- Apache Kafka
- Redis
- Flyway
- Docker Compose
- Swagger/OpenAPI
- JUnit 5
- Mockito

---

## Features

- REST APIs for notification management
- Transactional Outbox Pattern
- Kafka-based asynchronous processing
- Email / SMS / Push notification channels
- Retry mechanism
- Dead Letter Queue (DLQ)
- Redis-based idempotency
- Health checks
- Dockerized infrastructure

---

## Project Structure

```text
backend
├── common
├── config
├── domain
│   ├── notification
│   ├── outbox
│   ├── delivery
│   ├── preference
│   └── template
└── infrastructure
    ├── kafka
    ├── email
    ├── sms
    └── push
```

---

## Current Status

Current Milestone:

**M1 – Foundation & Transactional Outbox**

Completed:

- Project Setup
- PostgreSQL
- Flyway
- Swagger
- Notification Module
- Transactional Outbox
- Kafka Infrastructure

---

## Upcoming

- Kafka Consumer
- Delivery Engine
- Retry Engine
- Dead Letter Queue
- Redis Idempotency
- Monitoring
- GitHub Actions
