package nl.inholland.javafx.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.*;

public class MainWindow {
    public static BorderPane container = new BorderPane();
    public MainWindow(Person user) {
        Stage window = new Stage();
        // Set Window properties
        window.setTitle("Fantastic Cinema -- -- purchase tickets -- username: " + user.getUserName());

        // call class NavigationBar top get the correct menuBar
        NavigationBar navigationBar = new NavigationBar();
        MenuBar menuBar = navigationBar.getMenuBar(user);

        // Add label
        Label purchaseLabel = new Label("Purchase Tickets");
        purchaseLabel.setId("tester");

        // create database with list of movies and accounts
        Database db = new Database();

        // add wrapper to container
        container.setTop(menuBar);
        //this method will run and add tables to container
        RoomScreens.screen();

        Scene scene = new Scene(container);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);

        // Show window
        window.show();
    }
}


