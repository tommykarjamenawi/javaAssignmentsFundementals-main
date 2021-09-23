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
                String specialChars = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
                char[] chars = secretLabel.getText().toCharArray();
                int isCorrectLength = 0;
                int containsDigit = 0;
                int containsLetter = 0;
                int containsSpecial = 0;
                for(char c : chars){
                    String s=String.valueOf(c);
                    if (secretLabel.getText().length() >= 8){
                        isCorrectLength = 1;
                    }
                    if (specialChars.contains(s)){
                        containsSpecial = 1;
                    }
                    if(Character.isDigit(c)){
                        containsDigit = 1;
                    }
                    if(Character.isLetter(c)){
                        containsLetter = 1;
                    }
                    if (((isCorrectLength == 1) && (containsDigit == 1) && (containsLetter == 1) && (containsSpecial == 1))){
                        loginButton.setVisible(true);
                    }
                    else{
                        loginButton.setVisible(false);
                    }
                }
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

        window.setScene(scene);
        window.show();
    }
}
