package nl.inholland.javafx.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.*;
import java.util.Collections;

public class MainWindow {
    private final Stage window;
    private String text;
    private Room selectedMovie;
    Label titleHeader;
    Label roomHeader;
    Label movieHeader;
    Label selectedMovieHeader;

    public MainWindow(Person user, Database db) {
        window = new Stage();
        // Set Window properties
        window.setTitle("Fantastic Cinema -- -- purchase tickets -- username: " + user.getUserName());

        // Set container
        BorderPane container = new BorderPane();
        // Set wrapper
        VBox vbox = new VBox(10);
        HBox hBox = new HBox(10);
        VBox vboxBottom = new VBox(5);
        vboxBottom.setId("bottomLayout");
        vboxBottom.setStyle("-fx-background-color: #843939;");
        HBox insidVbox = new HBox(10);
        HBox insidVbox1 = new HBox(10);

        // call class NavigationBar top get the correct menuBar
        NavigationBar navigationBar = new NavigationBar();
        MenuBar menuBar = navigationBar.getMenuBar(user);

        // Add label
        Label purchaseLabel = new Label("Purchase Tickets");
        purchaseLabel.setId("tester");
        Label room1Label = new Label("room 1");
        Label room2Label = new Label("room 2");
        room1Label.setId("room1Label");
        room2Label.setId("room2Label");
        HBox aboveTable = new HBox(40);

        RoomTable roomTable = new RoomTable(db.getMovies(), 200);
        TableView room1 = roomTable.getTableViewRoom();
        room1.setMinWidth(650);

        ObservableList<Movie> movies = db.getMovies();
        Collections.reverse(movies); // reverse the list
        RoomTable roomTable2 = new RoomTable(movies, 100);
        TableView room2 = roomTable2.getTableViewRoom();
        Collections.reverse(movies); // reverse the list again to make the order default again
        room2.setMinWidth(650);

        // add objects to the wrappers
        vbox.getChildren().addAll(menuBar, purchaseLabel);
        hBox.getChildren().addAll(room1, room2);

        // Tableview click events
        room1.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedMovie = (Room)room1.getSelectionModel().getSelectedItem();
                text = "Room 1";
                titleHeader = new Label("Room");
                roomHeader = new Label(text);
                movieHeader = new Label("Movie title");
                selectedMovieHeader = new Label(selectedMovie.getTitle());
                insidVbox.getChildren().addAll(titleHeader, roomHeader, movieHeader, selectedMovieHeader);
            }
        });
        room2.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedMovie = (Room)room2.getSelectionModel().getSelectedItem();
                text = "Room 2";
                titleHeader = new Label("Room");
                roomHeader = new Label(text);
                movieHeader = new Label("Movie title");
                selectedMovieHeader = new Label(selectedMovie.getTitle());
                insidVbox.getChildren().addAll(titleHeader, roomHeader, movieHeader, selectedMovieHeader);
            }
        });

        aboveTable.getChildren().addAll(room1Label, room2Label);
        vboxBottom.getChildren().addAll(insidVbox, insidVbox1);

        GridPane gridPane = new GridPane();
        gridPane.add(room1Label, 1, 1);
        gridPane.add(room2Label, 2, 1);
        gridPane.add(room1, 1, 2);
        gridPane.add(room2, 2, 2);
        gridPane.setHgap(10);
        // make a border around the center
        gridPane.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID, null , null)));


        VBox center = new VBox(10);
        center.getChildren().addAll(aboveTable, hBox);
        insidVbox.setBorder(new Border(new BorderStroke(Color.DARKBLUE, BorderStrokeStyle.SOLID, null , null)));
        center.setBackground(new Background(new BackgroundFill(Color.web("#DFBF0AFF"), null, Insets.EMPTY)));

        // add wrapper to container
        container.setTop(vbox);
        container.setCenter(gridPane);
        container.setBottom(insidVbox);

        Scene scene = new Scene(container);
        scene.getStylesheets().add("style.css"); // apply css styling
        window.setScene(scene);

        // Show window
        window.show();
    }
}


