# Симуляція роботи аеропорту

Цей проєкт є симуляцією роботи аеропорту, написаною на Java. Програма моделює прибуття літаків з пасажирами (сім'ями) та їх розподіл по автобусах відповідно до пунктів призначення.

## Опис проєкту

Проєкт демонструє роботу з багатопотоковістю в Java, де кожен літак обробляється в окремому потоці. Основна логіка полягає в наступному:

1. Створюються літаки з пасажирами (сім'ями)
2. Кожен літак обробляється в окремому потоці
3. Сім'ї розподіляються по автобусах відповідно до їх пунктів призначення
4. Автобуси мають обмежену місткість
5. Після завершення роботи всіх потоків виводиться статистика

## Структура проєкту

Проєкт складається з наступних класів:

- **Dispatcher** - головний клас програми, який запускає симуляцію
- **Controller** - клас-контролер, який керує створенням та розподілом літаків, автобусів та сімей
- **Plane** - клас, що представляє літак з пасажирами (реалізує інтерфейс Runnable)
- **Bus** - клас, що представляє автобус для перевезення пасажирів до певного пункту призначення
- **Family** - запис (record), що представляє сім'ю пасажирів

## Як запустити проєкт

### Вимоги
- Java JDK 24 або новіше
- Будь-яке середовище розробки, що підтримує Java (IntelliJ IDEA, Eclipse, VS Code тощо)

### Запуск з командного рядка
1. Клонуйте репозиторій:
   ```
   git clone https://github.com/zave52/java_airport
   ```

2. Перейдіть до директорії проєкту:
   ```
   cd java_airport
   ```

3. Скомпілюйте проєкт:
   ```
   javac -d out/production/java_airport src/Airport/*.java
   ```

4. Запустіть програму:
   ```
   java -cp out/production/java_airport Airport.Dispatcher
   ```

### Запуск з IDE
1. Відкрийте проєкт у вашому IDE
2. Знайдіть клас `Dispatcher.java` в пакеті `Airport`
3. Запустіть метод `main` цього класу

## Функціональність

- Створення літаків з випадковою кількістю сімей
- Багатопотокова обробка літаків
- Розподіл сімей по автобусах відповідно до їх пунктів призначення
- Автоматичне створення нових автобусів, коли існуючі заповнюються
- Виведення детальної статистики після завершення симуляції

## Приклад виводу

Після запуску програми ви побачите інформацію про:
- Літаки та сім'ї на кожному літаку
- Автобуси та сім'ї в кожному автобусі
- Загальну статистику (кількість літаків, пасажирів, автобусів тощо)
