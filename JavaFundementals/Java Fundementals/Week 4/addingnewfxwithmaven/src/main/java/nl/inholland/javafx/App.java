package nl.inholland.javafx;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage window) throws Exception {
        window.setHeight(200);
        window.setWidth(300);
        window.setTitle("mandatory login application with password condition");

        //Create a layout to be used, in this case a gridpane
        GridPane myGrid = new GridPane();
        myGrid.setPadding(new Insets(10,10,10,10));
        myGrid.setVgap(10);
        myGrid.setHgap(8);

        //the username label and textfield
        Label userLabel = new Label("Username:");
        myGrid.add(userLabel,0,0);
        TextField userName = new TextField();
        userName.setPromptText("username");
        myGrid.add(userName,1,0);

        //The password label and field
        Label passwordLabel = new Label("Password:");
        myGrid.add(passwordLabel,0,1);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("enter password");
        myGrid.add(passwordField,1,1);

        //The secret label that reveals the password
        Label secretLabel = new Label();
        secretLabel.setVisible(false);
        myGrid.add(secretLabel,0,3);

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("customButton");
        loginButton.setId("specialButton");

        loginButton.setVisible(false);
        myGrid.add(loginButton,1,2);

        //create a StringProperty
        StringProperty passwordProperty = passwordField.textProperty();

        //add the listener to this property
        passwordProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                String oldValue, String newValue) {
                loginButton.setVisible(checkPassword(passwordField.getText()));
            }
        });

        //bind the listener to the text property of the new label
        secretLabel.textProperty().bind(passwordProperty);

        //let the button do something
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(userName.getText());
            }
        });

        // Set grid background color //
        myGrid.styleProperty().set("-fx-background-color: #0099FF");

        //add the grid to the scene, the scene to the stage, and show it
        Scene scene = new Scene(myGrid);

        // Add stylesheet
        scene.getStylesheets().add("style.css");
        // Scene
        window.setScene(scene);
        window.show();
    }

    public boolean checkPassword(String password){
        char[] chars = password.toCharArray();
        boolean isCorrectLength = false;
        boolean containsDigit = false;
        boolean containsLetter = false;
        boolean containsSpecial = false;

        if (password.length() >= 8){
            isCorrectLength = true;
        }
        for(char c : chars){
            if (!Character.isDigit(c) && !Character.isLetter(c)){
                containsSpecial = true;
            }
            if(Character.isDigit(c)){
                containsDigit = true;
            }
            if(Character.isLetter(c)){
                containsLetter = true;
            }
        }
        return (isCorrectLength && containsDigit && containsLetter && containsSpecial);
    }
}

