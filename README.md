Домашнє завдання "Банк" з розширеним функціоналом:
- використання spring boot та thymeleaf
- підключення spring secutiry
- підключеня мейлінга на базі google smtp
- запит до завнішнього ресурсу http://data.fixer.io/api/latest

1. Створити базу даних з таблицями "Користувачі", "Рахунки", "Транзакції", "Курси валют". Рахунок може бути в валюті: UAH, USD, EUR. 
Скорочений варіант із можливістю поповнення рахунку та зняття коштів.
2. Реєстрація нового авторизованого користувача. Обмеження доступу до всіх сторінок крім авторизації та реєстрації
3. Відправлення email підтвердження реєстрації нового авторизованого користувача.
4. Оновлення курсу валют, що підтримуються

Для запуску додати:
application.properties - spring.mail.username, spring.mail.password
Constants.java - CURRENCY_RATE_UTL, access key
