package nl.inholland.javafx.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.inholland.javafx.model.Person;

public class Dashboard {
    Stage window;

    public Dashboard(Person user) {
        window = new Stage();

        // Set Window properties
        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Dashboard");

        // Set container
        BorderPane container = new BorderPane();
        //container.styleProperty().set("-fx-background-color: #182128");
        // Create components
        Label welcomeLabel = new Label(String.format("Welcome %s", user.firstName));
        NavigationMenu menu = new NavigationMenu();

        // Add attributes
        welcomeLabel.setFont(new Font(50));

        // Add components to its container
        container.setTop(menu.getMenu(window, user));
        container.setCenter(welcomeLabel);

        // Set scene
        window.setScene(new Scene(container));

        // Show window
        window.show();
    }
}


