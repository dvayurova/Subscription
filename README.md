# User Subscription Service

Микросервис для управления пользователями и их подписками на цифровые сервисы.

## Стек технологий

- Java 17
- Spring Boot 3
- PostgreSQL
- Docker, Docker Compose
- SLF4J для логирования

## Как запустить

1. Соберите jar-файл:
```bash
./mvnw clean package
```

2. Запустите с помощью Docker Compose:
```bash
docker-compose up --build
```

Приложение будет доступно на `http://localhost:8080`.

## Конфигурация базы данных

База данных PostgreSQL настраивается в `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://db:5432/mydb
    username: postgres
    password: postgres
```

## REST API

### Пользователи

- `POST /users` — создать пользователя
- `GET /users/{id}` — получить информацию о пользователе
- `PUT /users/{id}` — обновить данные пользователя
- `DELETE /users/{id}` — удалить пользователя

### Подписки

- `POST /users/{id}/subscriptions` — добавить подписку пользователю
- `GET /users/{id}/subscriptions` — получить подписки пользователя
- `DELETE /users/{id}/subscriptions/{subId}` — удалить подписку

### ТОП-3 популярных подписок

- `GET /subscriptions/top` — получить самые популярные подписки.
