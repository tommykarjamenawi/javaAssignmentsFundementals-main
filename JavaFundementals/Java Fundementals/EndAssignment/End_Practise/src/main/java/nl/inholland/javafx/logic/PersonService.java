package nl.inholland.javafx.logic;

import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.*;

import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private final Database db = new Database();

    public Person validateUser(String username, String password) {
        for (Person person : db.getPersons()) {
            if (person.username.equals(username) && person.getPassword().equals(password)) {
                return person;
            }
        }
        return null;
    }

    public List<String> getGroups() {
        return db.getGroups();
    }

    public List<Person> getPersons() {
        return db.getPersons();
    }

    public List<Student> getStudents() {
        return db.getStudents();
    }

    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        for (Person person : db.getPersons()) {
            if (person instanceof Teacher) {
                teachers.add((Teacher) person);
            }
        }
        return teachers;
    }

    public List<Report> getReports() {
        return db.getReports();
    }

    public boolean saveStudents(List<Student> students) {
        return db.saveStudents(students);
    }

    public boolean saveTeachers(List<Teacher> teachers) {
        return db.saveTeachers(teachers);
    }
}