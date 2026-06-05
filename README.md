# Student Tracker (Java)

Консольное приложение для управления студентами, курсами и оценками. Во время работы данные хранятся в памяти (коллекции `List`, `Map`, `HashMap`) и автоматически сохраняются в локальный файл `data.txt`, поэтому при следующем запуске всё восстанавливается. Внешних библиотек и баз данных не требуется — только стандартная Java.

## Возможности

1. Добавить студента
2. Добавить предмет (курс)
3. Выставить оценку (0–100)
4. Показать средний балл (GPA) студента
5. Показать топ-3 студентов по успеваемости
6. Аналитика по предметам (средний балл по каждому курсу)
7. Выход

Программа не падает при неверном вводе: некорректные числа обрабатываются через `try-catch`, оценки вне диапазона 0–100 и несуществующие студенты/курсы выводят сообщение об ошибке.

## Структура проекта

```
src/
  model/      — доменная модель (сущности)
    Student.java   — студент (id, name, group)
    Course.java    — предмет (courseCode, courseName)
    Grade.java     — оценка (studentId, courseCode, score)
  service/    — бизнес-логика и аналитика
    TrackerService.java   — добавление, расчёт GPA, аналитика
    DataStore.java        — сохранение/загрузка данных в файл data.txt
  main/       — консольный интерфейс
    Main.java
README.md
```

- **model** — сущности с приватными полями, геттерами/сеттерами и `toString()`.
- **service** — `TrackerService`: добавление данных, выставление оценок, расчёт GPA и аналитика (хранение в `ArrayList` + `HashMap<Integer, Student>` для быстрого поиска по ID). `DataStore` сохраняет всё в `data.txt` после каждого изменения и загружает при старте — только стандартная Java.
- **main** — меню через `Scanner` и цикл `while(true)`.

## Требования

- JDK 17 или новее (разработано и проверено на JDK 22).

## Сборка и запуск

Из корня проекта:

### Linux / macOS

```bash
javac -d out $(find src -name "*.java")
java -cp out main.Main
```

### Windows (PowerShell)

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out main.Main
```

После запуска выберите пункт меню (1–7) и следуйте подсказкам.

## Пример использования

```
===== Student Tracker =====
1. Add student
...
Choose an option: 1
Enter student id: 1
Enter student name: Alice
Enter student group: CS-1
Student added: Student{id=1, name='Alice', group='CS-1'}
```
