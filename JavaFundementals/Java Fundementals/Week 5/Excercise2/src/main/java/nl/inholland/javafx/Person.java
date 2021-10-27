package nl.inholland.javafx;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
    private String firstName;
    public String getFirstName(){
        return firstName;
    }

    private String lastName;
    public String getLastName(){
        return lastName;
    }

    private LocalDate birthDate;
    public LocalDate getBirthDate(){
        return birthDate;
    }

    public Person(String firstName, String lastName, LocalDate birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
