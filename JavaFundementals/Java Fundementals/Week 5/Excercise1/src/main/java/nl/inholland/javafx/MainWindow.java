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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow {
    private Stage window;
    public MainWindow(String username) {
        // set-up the window
        window = new Stage();
        window.setWidth(1024);
        window.setHeight(800);

        // add a layout node and controls
        VBox box = new VBox();
        Label welcomeLabel = new Label("Welcome " + username);
        box.getChildren().add(welcomeLabel);

        // create the scene and show the window
        Scene scene = new Scene(box);
        window.setScene(scene);
        window.show();
    }
}


