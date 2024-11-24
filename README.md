# Разработка Системы Управления Задачами
### Описание проекта
Сервис для управления задачами, поддерживающий создание, редактирование, удаление и просмотр задач. Система включает аутентификацию пользователей с помощью JWT токенов и ролевую систему для пользователей и администраторов.
### Стек технологий
- Java 17 — основной язык программирования.
- Spring Boot 3.1.4 — фреймворк для разработки приложения.
- Spring Security — для аутентификации и авторизации с использованием JWT.
- PostgreSQL — база данных.
- Swagger & OpenAPI — документация и тестирование API.
- Docker — для контейнеризации и локального запуска сервиса.
### Эндпоинты
**Аутентификация и регистрация**

POST /api/users/register — регистрация нового пользователя.

Параметры запроса: email, password.

Ответ: 201 — успешно создано.

POST /api/users/login — аутентификация пользователя и получение JWT токена.

Параметры запроса: email, password.

Ответ: 200 — JWT токен.

**Управление задачами**

GET /api/tasks — получение задач с фильтрацией и пагинацией.

Параметры запроса:

authorEmail: email автора задачи.

assigneeEmail: email исполнителя задачи.

page: страница (по умолчанию 0).

size: количество задач на странице (по умолчанию 10).

Ответ: 200 — список задач.

GET /api/tasks/{id} — получение задачи по id.

Параметры запроса: id задачи.
Ответ: 200 — информация о задаче.
POST /api/tasks — создание новой задачи.

Параметры запроса: title, description, priority, status, assigneeEmail.

Ответ: 201 — задача создана.

PUT /api/tasks/{id} — редактирование существующей задачи.

Параметры запроса: id, title, description, priority, status, assigneeEmail.

Ответ: 200 — задача обновлена.

DELETE /api/tasks/{id} — удаление задачи.

Параметры запроса: id задачи.

Ответ: 204 — задача удалена.

POST /api/tasks/{id}/comment — добавление комментария к задаче.

Параметры запроса: comment.

Ответ: 200 — комментарий добавлен.

### Документация API с использованием Swagger
После запуска проекта Swagger UI доступен по следующему адресу:

http://localhost:8080/swagger-ui.html

### Инструкции по локальному запуску
1. Клонирование репозитория
- Клонируйте проект на вашу машину командой git clone
- Переход в директорию cd task-management-system
2. Настройка окружения
- Убедитесь, что у вас установлен Java 17+.
- Установите PostgreSQL и создайте базу данных.
- psql -U postgres
- CREATE DATABASE taskdb;
3. Настройка приложения
Создайте файл application.properties в директории src/main/resources со следующими параметрами:
- spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
- spring.datasource.username=your_username
- spring.datasource.password=your_password
- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.show-sql=true
- spring.jpa.properties.hibernate.format_sql=true

4. Запуск через Maven
Для запуска проекта на локальной машине с использованием Maven, выполните команду:

./mvnw spring-boot:run

Приложение будет доступно по адресу: http://localhost:8080.
