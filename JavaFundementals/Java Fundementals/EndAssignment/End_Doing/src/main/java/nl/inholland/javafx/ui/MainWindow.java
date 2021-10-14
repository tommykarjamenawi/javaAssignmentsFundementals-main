package nl.inholland.javafx.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.*;

import java.awt.*;
import java.io.File;

public class MainWindow {
    private final Stage window;
    public MainWindow(Person user) {
        window = new Stage();
        // Set Window properties
        //window.setMaxHeight(800);
        //window.setMaxWidth(1200);
        window.setTitle("Fantastic Cinema -- -- purchase tickets -- username: " + user.getUserName());
        //window.setResizable(false); // disable ability to resize the stage

        // Set container
        BorderPane container = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);

        // call class NavigationBar top get the correct menuBar
        NavigationBar navigationBar = new NavigationBar();
        MenuBar menuBar = navigationBar.getMenuBar(user);

        // Add attributes
        Label purchaseLabel = new Label("Purchase Tickets");
        purchaseLabel.setId("tester");

        Database db = new Database();
        RoomTableView roomTableView = new RoomTableView(db.getMovies(), db.getMovies1());
        TableView<Room> tableview1 = roomTableView.getRoom1View();
        TableView<Room> tableview2 = roomTableView.getRoom2View();

        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(menuBar, purchaseLabel);

        //HBox hbox = new HBox(10);
        gridPane.getChildren().addAll(tableview1, tableview2);
        gridPane.setConstraints(tableview1, 0, 3);
        gridPane.setConstraints(tableview2, 1, 3);


        // Add components to its container
        container.setTop(vbox);
        container.setCenter(gridPane);


        // Set scene
        Scene scene = new Scene(container);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.sizeToScene();

        // Show window
        window.show();
    }
}


