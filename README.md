Инструкция по запуску автотестов и генерации отчетов:

1) Авторизация на сайте demo.reportportal.io

2) Нажатие по аватарке в нижнем левом углу, переход в раздел "Profile" и оттуда в раздел "API Keys" перейти

3) Создайте API ключ на сайте

4) В корне проекта создайте файл .env, в него добавьте строку:

TOKEN=ваш_API_ключ

Замените "ваш_API_ключ" на полученный API ключ.

5) Запуск автотестов:

Запустите автотесты с помощью команды:

./gradlew test
Либо щелкните правой кнопкой мыши по папке src и выберите Run в контекстном меню.

6) Генерация и просмотр отчета в Allure:

Для генерации отчета и автоматического открытия его в браузере используйте команду:

./gradlew allureServe
Отчет откроется в браузере.

