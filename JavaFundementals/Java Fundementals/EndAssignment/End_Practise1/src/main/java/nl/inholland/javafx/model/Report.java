package nl.inholland.javafx.model;

import java.time.format.DateTimeFormatter;

public class Report {
    static final String REPORT_FORMAT = "%-5s %-15s %-15s %-15s %-5s %-10s %-10s %-10s %-10s %-10s";

    Student student;
    int java;
    int cSharp;
    int python;
    int php;

    public Report(Student student, int java, int cSharp, int python, int php) {
        this.student = student;
        this.java = java;
        this.cSharp = cSharp;
        this.python = python;
        this.php = php;
    }

    public int retakes() {
        int retakes = 0;
        for (int grade : new int[]{java, cSharp, python, php}) {
            if (grade < 55) {
                retakes++;
            }
        }

        return retakes;
    }

    public String showStudentWithGrades() {
        return String.format(REPORT_FORMAT,
                student.id,
                student.firstName,
                student.lastName,
                student.birthdate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                student.getAge(),
                student.group,
                java, cSharp, python, php);
    }

    @Override
    public String toString() {
        return String.format("\n" +
                        "Report of student %s %s\n\n" +
                        "%-20s %s\n" +
                        "%-20s %s\n" +
                        "%-20s %s\n" +
                        "%-20s %s\n\n" +
                        "%15s\n\n" +
                        "%-20s %s\n" +
                        "%-20s %s\n" +
                        "%-20s %s\n" +
                        "%-20s %s\n\n" +
                        "%15s\n\n" +
                        "%-20s %s\n" +
                        "%-20s %s\n",
                student.firstName, student.lastName,
                "Student Id:", student.id,
                "First Name:", student.firstName,
                "Last Name:", student.lastName,
                "Age:", student.getAge(),
                "COURSES",
                "Java:", java,
                "CSharp:", cSharp,
                "Python:", python,
                "PHP:", php,
                "RESULTS",
                "Results:", retakes() == 0 ? "Passed" : "Not Passed",
                "Retakes:", retakes());
    }
}
