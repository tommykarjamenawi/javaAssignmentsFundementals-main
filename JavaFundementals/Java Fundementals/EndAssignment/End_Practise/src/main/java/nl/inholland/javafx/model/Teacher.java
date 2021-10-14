package nl.inholland.javafx.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Teacher extends Person {
    static final String TEACHER_FORMAT = "%-5s %-15s %-15s %-15s %-5s";
    public double salary;

    public Teacher(String firstName, String lastName, LocalDate birthdate, double salary, String username, String password) {
        super(firstName, lastName, birthdate, username, password);
        this.salary = salary;
        levelOfAccess = LevelOfAccess.Editor;
    }

    public Teacher(int id, String firstName, String lastName, LocalDate birthdate, double salary, String username, String password) {
        super(id, firstName, lastName, birthdate, username, password);
        this.salary = salary;
        levelOfAccess = LevelOfAccess.Editor;
    }

    @Override
    public String toString() {
        return String.format(TEACHER_FORMAT,
                id,
                firstName,
                lastName,
                birthdate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                getAge());
    }
}