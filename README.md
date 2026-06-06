# Student Tracker (Java)

A console application for managing students, courses, and grades. During operation, data is stored in memory (List, Map, and HashMap collections) and automatically saved to a local data.txt file, so everything is restored the next time you launch it. No external libraries or databases are required—just standard Java.

## Features

1. Add a student
2. Add a subject (course)
3. Set a grade (0–100)
4. Show the student's average grade (GPA)
5. Show the top 3 students by academic performance
6. Subject analytics (average grade for each course)
7. Exit

The program does not crash on invalid input: invalid numbers are handled via try-catch, grades outside the 0–100 range, and non-existent students/courses display an error message.

## Project Structure

```
src/
model/ — domain model (entities)
Student.java — student (id, name, group)
Course.java — subject (courseCode, courseName)
Grade.java — grade (studentId, courseCode, score)
service/ — business logic and analytics
TrackerService.java — adding, calculating GPA, analytics
DataStore.java — saving/loading data to the data.txt file
main/ — console interface
Main.java
README.md
```

- **model** — entities with private fields, getters/setters, and `toString()`.
- **service** — `TrackerService`: adding data, assigning grades, calculating GPA, and analytics (stored in `ArrayList` + `HashMap<Integer, Student>` for quick lookup by ID). `DataStore` saves everything to `data.txt` after each change and loads it at startup — standard Java only.
- **main** — menu via `Scanner` and a `while(true)` loop.

## Requirements

- JDK 17 or later (developed and tested on JDK 22).

## Building and Running

From the project root:

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

After launching, select a menu item (1–7) and follow the prompts.

## Usage example

```
Student Tracker 
1. Add student
...
Choose an option: 1
Enter student id: 1
Enter student name: Alice
Enter student group: CS-1
Student added: Student{id=1, name='Alice', group='CS-1'}
```
