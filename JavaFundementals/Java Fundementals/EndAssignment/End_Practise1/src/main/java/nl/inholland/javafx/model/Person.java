package nl.inholland.javafx.model;

import java.time.LocalDate;
import java.time.Period;

public abstract class Person {
    private static int nextId = 1;
    private final String password;
    public int id;
    public String firstName;
    public String lastName;
    public LocalDate birthdate;
    public String username;
    public LevelOfAccess levelOfAccess;

    public Person(String firstName, String lastName, LocalDate birthdate, String username, String password) {
        this.id = nextId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
        nextId++;
    }

    public Person(int id, String firstName, String lastName, LocalDate birthdate, String username, String password) {
        this(firstName, lastName, birthdate, username, password);
        this.id = id;
        nextId = id + 1;
    }

    public int getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Person " + firstName + " " + lastName;
    }
}
