package nl.inholland.javafx.model;

import java.time.LocalDate;

public class Manager extends Person {

    public Manager(int id, String firstName, String lastName, LocalDate birthdate, String username, String password) {
        super(id, firstName, lastName, birthdate, username, password);
        levelOfAccess = LevelOfAccess.Admin;
    }

    @Override
    public String toString() {
        return "Manager " + firstName + " " + lastName;
    }
}