package nl.inholland.javafx.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.inholland.javafx.logic.PersonService;
import nl.inholland.javafx.model.Person;

public class App extends Application {
    private final PersonService personService = new PersonService();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {

        // Set Window properties
        window.setHeight(200);
        window.setWidth(300);
        window.setTitle("Login Screen");

        // Set grid
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(8);
        //gridPane.styleProperty().set("-fx-background-color: #182128");

        // Create components
        Label userLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        TextField userInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Log in");

        // Add attributes
        userInput.setPromptText("Enter your username...");
        passwordInput.setPromptText("Enter your password...");
        loginButton.setDefaultButton(true);

        // When button is clicked
        loginButton.setOnAction(actionEvent -> {
            if (!userInput.getText().isEmpty() && !passwordInput.getText().isEmpty()) {
                Person user = personService.validateUser(userInput.getText(), passwordInput.getText());
                if (user != null) {
                    new Dashboard(user);
                    window.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "The username and password do not match! Try again.").show();
                    userInput.clear();
                    passwordInput.clear();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Please fill in a username and password.").show();
            }
        });

        // Add components to grid
        gridPane.add(userLabel, 0, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(userInput, 1, 0);
        gridPane.add(passwordInput, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.getStylesheets().add("src/main/java/nl/inholland/javafx/files/css/style.css");
        // Set scene
        Scene scene = new Scene(gridPane);
        //scene.getStylesheets().add("src/main/java/nl/inholland/javafx/files/css/style.css");
        window.setScene(scene);

        // Show window
        window.show();
    }
}

