package nl.inholland.javafx.ui;

import javafx.collections.ObservableList;
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

import javax.swing.tree.RowMapper;
import java.util.Collections;

public class MainWindow {
    private final Stage window;
    public MainWindow(Person user) {
        window = new Stage();
        // Set Window properties
        window.setTitle("Fantastic Cinema -- -- purchase tickets -- username: " + user.getUserName());

        // Set container
        BorderPane container = new BorderPane();
        // Set wrapper
        VBox vbox = new VBox(10);
        HBox hBox = new HBox(10);

        // call class NavigationBar top get the correct menuBar
        NavigationBar navigationBar = new NavigationBar();
        MenuBar menuBar = navigationBar.getMenuBar(user);

        // Add label
        Label purchaseLabel = new Label("Purchase Tickets");
        purchaseLabel.setId("tester");

        // create database with list of movies and accounts
        Database db = new Database();

        RoomTable roomTable = new RoomTable(db.getMovies(), 200);
        TableView room1 = roomTable.getTableViewRoom();
        room1.setMinWidth(900);

        ObservableList<Movie> movies = db.getMovies();
        Collections.reverse(movies);

        RoomTable roomTable2 = new RoomTable(movies, 100);
        TableView room2 = roomTable2.getTableViewRoom();
        room2.setMinWidth(900);

        // add objects to the wrappers
        vbox.getChildren().addAll(menuBar, purchaseLabel);
        hBox.getChildren().addAll(room1, room2);


        // add wrapper to container
        container.setTop(vbox);
        container.setCenter(hBox);

        Scene scene = new Scene(container);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);

        // Show window
        window.show();
    }
}


