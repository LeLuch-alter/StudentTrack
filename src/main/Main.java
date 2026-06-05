package main;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import model.Course;
import model.Student;
import service.TrackerService;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TrackerService service = new TrackerService();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addStudent();
                case "2" -> addCourse();
                case "3" -> assignGrade();
                case "4" -> showStudentGpa();
                case "5" -> showTopThree();
                case "6" -> showCourseAnalytics();
                case "7" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Unknown option. Please choose 1-7.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Student Tracker =====");
        System.out.println("1. Add student");
        System.out.println("2. Add course");
        System.out.println("3. Assign grade");
        System.out.println("4. Show student GPA");
        System.out.println("5. Show top-3 students");
        System.out.println("6. Course analytics");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addStudent() {
        Integer id = readInt("Enter student id: ");
        if (id == null) {
            return;
        }
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter student group: ");
        String group = scanner.nextLine().trim();
        service.addStudent(new Student(id, name, group));
    }

    private static void addCourse() {
        System.out.print("Enter course code (e.g. INF-101): ");
        String code = scanner.nextLine().trim();
        System.out.print("Enter course name: ");
        String name = scanner.nextLine().trim();
        service.addCourse(new Course(code, name));
    }

    private static void assignGrade() {
        Integer studentId = readInt("Enter student id: ");
        if (studentId == null) {
            return;
        }
        System.out.print("Enter course code: ");
        String code = scanner.nextLine().trim();
        Integer score = readInt("Enter score (0-100): ");
        if (score == null) {
            return;
        }
        service.assignGrade(studentId, code, score);
    }

    private static void showStudentGpa() {
        Integer id = readInt("Enter student id: ");
        if (id == null) {
            return;
        }
        double gpa = service.getStudentGpa(id);
        System.out.printf(Locale.US, "GPA of student %d: %.2f%n", id, gpa);
    }

    private static void showTopThree() {
        var top = service.getTopThreeStudents();
        if (top.isEmpty()) {
            System.out.println("No students yet.");
            return;
        }
        System.out.println("Top-3 students by GPA:");
        int rank = 1;
        for (Student student : top) {
            System.out.printf(Locale.US, "%d. %s - GPA %.2f%n",
                    rank++, student.getName(), service.getStudentGpa(student.getId()));
        }
    }

    private static void showCourseAnalytics() {
        Map<Course, Double> averages = service.getCourseAverages();
        if (averages.isEmpty()) {
            System.out.println("No courses yet.");
            return;
        }
        System.out.println("Average score per course:");
        for (Map.Entry<Course, Double> entry : averages.entrySet()) {
            Course course = entry.getKey();
            System.out.printf(Locale.US, "%s (%s) - average %.2f%n",
                    course.getCourseName(), course.getCourseCode(), entry.getValue());
        }
    }

    private static Integer readInt(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Error: please enter a valid number.");
            return null;
        }
    }
}
