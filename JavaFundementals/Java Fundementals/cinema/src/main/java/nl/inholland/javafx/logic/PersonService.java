package nl.inholland.javafx.logic;

import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.*;

import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private final Database db = new Database();

    public Person validateUser(String username, String password) {
        for (Person person : db.getPersons()) {
            if (person.getUserName().equals(username) && person.getPassword().equals(password)) {
                return person;
            }
        }
        return null;
    }

    public List<Person> getPersons() {
        return db.getPersons();
    }

    public List<Person> getTeachers() {
        List<Person> persons = new ArrayList<>();
        for (Person person : db.getPersons()) {
            if (person instanceof Person) {
                persons.add((Person) person);
            }
        }
        return persons;
    }


    public boolean savePersons(List<Person> persons) {
        return db.savePersons(persons);
    }
}