package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Course;
import model.Grade;
import model.Student;

public class TrackerService {
    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Grade> grades = new ArrayList<>();
    private final Map<Integer, Student> studentsById = new HashMap<>();
    private final DataStore dataStore = new DataStore();

    public TrackerService() {
        dataStore.load(students, courses, grades);
        for (Student student : students) {
            studentsById.put(student.getId(), student);
        }
    }

    // Task 2 — business logic

    public void addStudent(Student student) {
        if (studentsById.containsKey(student.getId())) {
            System.out.println("Error: student with id " + student.getId() + " already exists.");
            return;
        }
        students.add(student);
        studentsById.put(student.getId(), student);
        save();
        System.out.println("Student added: " + student);
    }

    public void addCourse(Course course) {
        if (findCourse(course.getCourseCode()) != null) {
            System.out.println("Error: course with code " + course.getCourseCode() + " already exists.");
            return;
        }
        courses.add(course);
        save();
        System.out.println("Course added: " + course);
    }

    public void assignGrade(int studentId, String courseCode, int score) {
        if (!studentsById.containsKey(studentId)) {
            System.out.println("Error: student with id " + studentId + " does not exist.");
            return;
        }
        if (findCourse(courseCode) == null) {
            System.out.println("Error: course with code " + courseCode + " does not exist.");
            return;
        }
        if (score < 0 || score > 100) {
            System.out.println("Error: score must be between 0 and 100.");
            return;
        }
        grades.add(new Grade(studentId, courseCode, score));
        save();
        System.out.println("Grade assigned: student " + studentId + ", course " + courseCode + ", score " + score);
    }

    private void save() {
        dataStore.save(students, courses, grades);
    }

    public double getStudentGpa(int studentId) {
        if (!studentsById.containsKey(studentId)) {
            System.out.println("Error: student with id " + studentId + " does not exist.");
            return 0.0;
        }
        return calculateGpa(studentId);
    }

    // Task 3 — analytics

    public List<Student> getStudentsAboveGpa(double threshold) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (calculateGpa(student.getId()) > threshold) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> getTopThreeStudents() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparingDouble((Student s) -> calculateGpa(s.getId())).reversed());
        return sorted.subList(0, Math.min(3, sorted.size()));
    }

    public Map<Course, Double> getCourseAverages() {
        Map<Course, Double> averages = new HashMap<>();
        for (Course course : courses) {
            averages.put(course, calculateCourseAverage(course.getCourseCode()));
        }
        return averages;
    }


    private double calculateGpa(int studentId) {
        double sum = 0.0;
        int count = 0;
        for (Grade grade : grades) {
            if (grade.getStudentId() == studentId) {
                sum += scoreToGpaPoint(grade.getScore());
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    // Converts a 0-100 score into a point on the 4.0 GPA scale.
    private double scoreToGpaPoint(int score) {
        if (score >= 95) return 4.0;
        if (score >= 90) return 3.67;
        if (score >= 85) return 3.33;
        if (score >= 80) return 3.0;
        if (score >= 75) return 2.67;
        if (score >= 70) return 2.33;
        if (score >= 65) return 2.0;
        if (score >= 60) return 1.67;
        if (score >= 55) return 1.33;
        if (score >= 50) return 1.0;
        return 0.0;
    }

    private double calculateCourseAverage(String courseCode) {
        int sum = 0;
        int count = 0;
        for (Grade grade : grades) {
            if (grade.getCourseCode().equals(courseCode)) {
                sum += grade.getScore();
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) sum / count;
    }

    private Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}
