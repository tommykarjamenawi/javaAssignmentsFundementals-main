package nl.inholland.javafx.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Student extends Person {
    static final String STUDENT_FORMAT = "%-5s %-15s %-15s %-15s %-5s %-10s";
    public String group;

    public Student(String firstName, String lastName, LocalDate birthdate, String group, String username, String password) {
        super(firstName, lastName, birthdate, username, password);
        this.group = group;
        levelOfAccess = LevelOfAccess.Basic;
    }

    public Student(int id, String firstName, String lastName, LocalDate birthdate, String group, String username, String password) {
        super(id, firstName, lastName, birthdate, username, password);
        this.group = group;
        levelOfAccess = LevelOfAccess.Basic;
    }

    @Override
    public String toString() {
        return String.format(STUDENT_FORMAT,
                id,
                firstName,
                lastName,
                birthdate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                getAge(),
                group);
    }
}
