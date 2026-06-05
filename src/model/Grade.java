package model;

public class Grade {
    private int studentId;
    private String courseCode;
    private int score;

    public Grade(int studentId, String courseCode, int score) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.score = score;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Grade{studentId=" + studentId + ", courseCode='" + courseCode + "', score=" + score + "}";
    }
}
