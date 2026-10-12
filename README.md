# Customer Notifications — release-analysis demo

A deliberately small Spring Boot 3 / Java 17 service for demonstrating a commit-diff summarizer. The API accepts customer notifications, publishes each created notification to Kafka, and lists notifications by customer; the demo's lookup storage is in-memory.

## Change signals in this demo

- `src/main/resources/application.properties` includes delivery timeout/retry settings and `notification.topic.name`, so rules can identify a config change and Kafka-topic change. `NotificationPublisher` sends each new notification as a JSON Kafka message, keyed by customer ID.
- `dynamo.txt` describes the demo `CustomerNotification` table, key layout, index, and TTL. The analyzer attached to this workspace currently matches the filename `dynamo.txt` (not `dynamodb.txt`) or an added diff line that references `dynamo.txt`.
- The Java API and its tests provide a few meaningful code changes without making the release diff too large.

## Run

Requires Java 17, Maven 3.6+, and Kafka available at `localhost:9092` (or set `KAFKA_BOOTSTRAP_SERVERS`):

```bash
mvn spring-boot:run
```

Create a notification with `POST /api/notifications` and JSON body `{"customerId":"cust-42","message":"Your order has shipped"}`. List notifications with `GET /api/notifications/customer/cust-42`.

The producer writes to the topic configured by `notification.topic.name` (`customer-notifications` by default). The JSON message contains the notification ID, customer ID, message, and creation timestamp. Start a Kafka broker before posting notifications.

Run tests with `mvn test`.
