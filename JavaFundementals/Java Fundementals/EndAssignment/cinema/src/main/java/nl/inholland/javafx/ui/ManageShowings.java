package nl.inholland.javafx.ui;

import javafx.stage.Stage;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.Person;

public class ManageShowings {
    private final Stage window;
    private Database dataBase;

    public ManageShowings(Person user, Database db){
        dataBase = db;
        window = new Stage();
        window.setTitle("Fantastic Cinema -- -- Manage showings -- username: " + user.getUserName());
    }
}
