package service;

import model.Course;
import model.Grade;
import model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Saves and loads all data to a plain text file using only the standard library.
// One line per record, fields separated by a tab:
//   STUDENT <id> <name> <group>
//   COURSE  <courseCode> <courseName>
//   GRADE   <studentId> <courseCode> <score>
public class DataStore {
    private static final Path FILE = Path.of("data.txt");
    private static final String SEP = "\t";

    public void save(List<Student> students, List<Course> courses, List<Grade> grades) {
        List<String> lines = new ArrayList<>();
        for (Student s : students) {
            lines.add(String.join(SEP, "STUDENT", String.valueOf(s.getId()), s.getName(), s.getGroup()));
        }
        for (Course c : courses) {
            lines.add(String.join(SEP, "COURSE", c.getCourseCode(), c.getCourseName()));
        }
        for (Grade g : grades) {
            lines.add(String.join(SEP, "GRADE", String.valueOf(g.getStudentId()), g.getCourseCode(),
                    String.valueOf(g.getScore())));
        }
        try {
            Files.write(FILE, lines);
        } catch (IOException e) {
            System.out.println("Warning: could not save data: " + e.getMessage());
        }
    }

    public void load(List<Student> students, List<Course> courses, List<Grade> grades) {
        if (!Files.exists(FILE)) {
            return;
        }
        try {
            for (String line : Files.readAllLines(FILE)) {
                if (line.isBlank()) {
                    continue;
                }
                String[] p = line.split(SEP, -1);
                switch (p[0]) {
                    case "STUDENT" -> students.add(new Student(Integer.parseInt(p[1]), p[2], p[3]));
                    case "COURSE" -> courses.add(new Course(p[1], p[2]));
                    case "GRADE" -> grades.add(new Grade(Integer.parseInt(p[1]), p[2], Integer.parseInt(p[3])));
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: could not load data: " + e.getMessage());
        }
    }
}
