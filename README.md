
# Spring Boot 3 + Spring Security 6 + JWT

Базовый шаблон REST API приложения с реализованным механизмом регистрации и авторизации с использованием JSON Web Tokens (JWT). 

Проект построен по слоистой архитектуре (Controller -> Service -> Repository) и готов к масштабированию.

## Технологический стек
* **Java 17 / 21**
* **Spring Boot 3.2+** (Web, Data JPA, Security)
* **Spring Security 6**
* **JWT** (библиотека `io.jsonwebtoken` v0.12.5)
* **PostgreSQL** (База данных)
* **Lombok** (Для сокращения boilerplate-кода)
* **Maven**

##  Как запустить проект

1. Убедитесь, что у вас установлен и запущен PostgreSQL.
2. Создайте пустую базу данных (например, `reg_auth_db`).
3. Откройте файл `src/main/resources/application.properties` (или `.yml`) и настройте подключение к БД и секретный ключ:

```properties
# Подключение к БД
spring.datasource.url=jdbc:postgresql://localhost:5432/reg_auth_db
spring.datasource.username=твой_логин
spring.datasource.password=твой_пароль
spring.jpa.hibernate.ddl-auto=update

# Настройки JWT
# Секретный ключ (должен быть длинным, это пример)
jwt.secret=984hg493gh0439hg0439hg0439hg0439hg0439hg0439hgfdsfdsfdsfdfdsfsd
# Время жизни токена (здесь 1 час = 3600000 мс)
jwt.lifetime=3600000
```
4. Запустите класс `RegAuthEduApplication.java` в вашей IDE.

---

## API Эндпоинты (Как тестировать в Postman)

### 1. Регистрация нового пользователя (Открытый доступ)
* **Метод:** `POST`
* **URL:** `http://localhost:8080/auth/register`
* **Body (JSON):**
```json
{
  "username": "ivan_ivanov",
  "email": "ivan@mail.ru",
  "password": "supersecretpassword"
}
```

### 2. Вход (Аутентификация) и получение JWT
* **Метод:** `POST`
* **URL:** `http://localhost:8080/auth/login`
* **Body (JSON):**
```json
{
  "username": "ivan_ivanov",
  "password": "supersecretpassword"
}
```
* **Ответ сервера (Успех):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIi..."
}
```

### 3. Доступ к защищенному ресурсу (Авторизация)
* **Метод:** `GET`
* **URL:** `http://localhost:8080/api/hello`
* **Headers:** 
  * Ключ: `Authorization`
  * Значение: `Bearer ТВОЙ_СКОПИРОВАННЫЙ_ТОКЕН`
* **Ответ сервера:**
```text
"Привет, ivan_ivanov! Ты успешно прошел авторизацию по токену!"
```

---

## Структура проекта

* `controller/` — REST-контроллеры (прием запросов от клиента).
* `dto/` — Data Transfer Objects (объекты для безопасной передачи данных без паролей).
* `entity/` — Сущности базы данных (`User`, `Role`).
* `repository/` — Интерфейсы для работы с БД (Spring Data JPA).
* `security/` — Вся магия защиты:
  * `SecurityConfig` — правила доступа и отключение сессий.
  * `JwtUtil` — генерация и проверка токенов.
  * `JwtFilter` — фильтр, перехватывающий запросы для проверки токена.
  * `CustomUserDetailsService` — связка Spring Security с нашей БД.
* `service/` — Бизнес-логика (хэширование паролей, сохранение юзеров).

---
