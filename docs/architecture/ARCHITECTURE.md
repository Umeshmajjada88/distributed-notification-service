# System Architecture

```text
                Client
                   │
                   ▼
       Notification Controller
                   │
                   ▼
        Notification Service
         │                 │
         ▼                 ▼
 Notification Table    Outbox Table
                            │
                            ▼
              Outbox Publisher Scheduler
                            │
                            ▼
                     Kafka Producer
                            │
                 notification.v1.created
                            │
         ─────────────────────────────────
                            │
                     Kafka Consumer
                            │
                            ▼
                 Delivery Dispatcher
              ┌────────┼─────────┐
              ▼        ▼         ▼
           Email      SMS      Push
```

The application follows:

- Layered Architecture
- Domain-Oriented Package Structure
- Transactional Outbox Pattern
- Event-Driven Architecture
- Strategy Pattern
- Repository Pattern