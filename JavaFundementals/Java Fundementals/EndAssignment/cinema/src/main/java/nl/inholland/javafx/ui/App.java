package nl.inholland.javafx.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.logic.PersonService;
import nl.inholland.javafx.model.Person;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    // startup method
    @Override
    public void start(Stage stage) throws Exception {
        if (db == null){db = getDatabase();}
        fillLoginScreen(stage);
    }

    // when logged out this method will be called
    public void start(Stage window, Database dataBase) throws Exception {
        db = dataBase;
        fillLoginScreen(window);
    }

    public Database db;
    public Stage screen;

    public Database getDatabase(){
        return new Database();
    }

    // Fill the scene
    public void fillLoginScreen(Stage window){
        screen = window;
        PersonService personService = new PersonService();
        // Set Window properties
        window.setHeight(200);
        window.setWidth(300);
        window.setTitle("Login Screen");

        // Set grid
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(8);

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

        // Login button click event
        loginButton.setOnAction(actionEvent -> {
            if (!userInput.getText().isEmpty() && !passwordInput.getText().isEmpty()) {
                Person user = personService.validateUser(userInput.getText(), passwordInput.getText());
                if (user != null) {
                    new MainWindow(user, db);
                    window.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Bad Credentials! Try again.").show();
                    userInput.clear();
                    passwordInput.clear();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Please provide a username and password.").show();
            }
        });

        // Add components to grid
        gridPane.add(userLabel, 0, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(userInput, 1, 0);
        gridPane.add(passwordInput, 1, 1);
        gridPane.add(loginButton, 1, 2);

        // Set scene
        Scene scene = new Scene(gridPane);
        //add JMetro
        JMetro jMetro = new JMetro(Style.DARK);
        gridPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        jMetro.setScene(scene);

        window.setScene(scene);

        // Show window
        window.show();
    }
}

