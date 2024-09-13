# Diplom_2

### Запуск проекта:
```shell
mvn clean test
```

## Объект тестирования:
API сервиса Stellar Burgers\
(https://stellarburgers.nomoreparties.site/)

## Содержание проекта:
* __Операции с аккаунтом пользователя__
  * Создание пользователя (_POST /api/auth/register_)
  * Логин пользователя (_POST /api/auth/login_)
  * Изменение данных пользователя (_POST /api/auth/user_)
* __Операции с заказом__
  * Создание заказа (_POST /api/orders_)
  * Получение списка заказов пользователя (_GET /api/orders_)

## Используемые технологии:

| **Название**          | **Версия** |
|-----------------------|------------|
| AspectJ               | 1.9.7      |
| JUnit 4               | 4.13.2     |
| REST Assured          | 5.5.0      |
| Gson                  | 2.8.9      |
| Allure                | 2.15.0     |
| Maven Surefire plugin | 3.3.1      |
| Maven Allure plugin   | 2.10.0     |
