# AutoLoaderDispatch

## Обзор
AutoLoaderDispatch - это система для автоматизации вызова погрузчиков на производстве и складах. Система позволяет операторам быстро оформлять заявки на вызов погрузчиков, а алгоритм распределяет заявки на ближайшие свободные машины.

## Технологии
- **Backend**: Java, Spring Boot, Spring Data JPA, Flyway, WebSockets
- **Database**: PostgreSQL
- **Frontend**: Angular
- **Infrastructure**: Docker, Docker Compose

## Функциональность
- Оформление заявок на погрузку
- Поиск ближайшего свободного погрузчика
- WebSockets для реального времени обновления статусов
- Управление складами и пользователями

## Запуск в Docker
```sh
docker-compose up --build -d
```

## Запросы API
### 1. Погрузчики
- `GET /api/loaders` – Список погрузчиков
- `POST /api/loaders` – Добавить погрузчика

### 2. Заявки
- `GET /api/requests` – Получить все заявки
- `POST /api/requests` – Создать заявку
- `PUT /api/requests/{id}/status` – Обновить статус

## План разработки
1. Реализовать WebSockets
2. Добавить аутентификацию (JWT)
3. Доработать алгоритм распределения погрузчиков

