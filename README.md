# Чат NothingMessanger

## Описание

Это веб-приложение позволяет пользователям регистрироваться, входить в систему, обмениваться сообщениями и публиковать заметки. Приложение использует JavaScript для фронтенда и взаимодействует с сервером через REST API и WebSocket для обмена сообщениями в реальном времени.

## Основные функции

- Регистрация нового пользователя
- Вход в систему
- Загрузка изображений профиля
- Отображение ленты сообщений
- Обмен сообщениями в реальном времени

## Технологии

- HTML
- CSS
- JavaScript
- WebSocket
- REST API
- Spring boot

## Перед запуском
- В application.property пропишите свой datasourse
- Запустите init.sql
- Создайте директорию FileSystem\Users
- Укажите в переменной "FOLDER_PATH" в FileDataService полный путь до директории
- Пример: "C:\\Users\\Ваш_Пользователь\\IdeaProjects\\chat-v3\\src\\main\\resources\\static\\FileSystem\\Users";