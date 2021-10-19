package nl.inholland.javafx.ui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

        // Make container
        BorderPane container = new BorderPane();
        // Make wrapper
        VBox topVbox = new VBox(10);
        GridPane centerPane = new GridPane();
        GridPane bottomPane = new GridPane();
    }
}
