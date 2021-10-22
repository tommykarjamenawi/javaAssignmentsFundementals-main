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
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.*;
import java.time.LocalDateTime;
import java.util.Collections;

public class MainWindow {
    private final Stage windowStage;
    private String text;
    private Room selectedMovie;
    private Label titleHeader;
    private Label roomHeader;
    private Label movieHeader;
    private Label selectedMovieHeader;
    private Label lblStartHeader;
    private Label lblEndHeader;
    private Label lblStartTime;
    private Label lblEndTime;
    private Label lblSeatsHeader;
    private Label lblNameHeader;
    private TextField nameInput;
    private ChoiceBox<Integer> nrOfSeats;
    private Label errorMessage;
    private Database dataBase;
    private Button btnPurchaseTicket;
    private Button btnClearPurchaseField;
    private GridPane bottomPane;

    public MainWindow(Person user, Database db) {
        dataBase = db;
        windowStage = new Stage();
        // Set Window properties
        windowStage.setTitle("Fantastic Cinema -- -- purchase tickets -- username: " + user.getUserName());

        btnPurchaseTicket = new Button("Purchase");
        btnClearPurchaseField = new Button("Clear");

        // Set container
        BorderPane container = new BorderPane();

        // Set wrapper
        VBox topVbox = new VBox(10);
        bottomPane = new GridPane();
        bottomPane.setVisible(false);

        // call class NavigationBar top get the correct menuBar
        NavigationBar navigationBar = new NavigationBar();
        MenuBar menuBar = navigationBar.getMenuBar(user, windowStage, dataBase);

        // Add labels
        Label purchaseLabel = new Label("Purchase Tickets");
        purchaseLabel.setId("tester");
        Label room1Label = new Label("room 1");
        Label room2Label = new Label("room 2");
        room1Label.setId("room1Label");
        room2Label.setId("room2Label");
        errorMessage = new Label("");

        // make tableviews
        RoomTable roomTable = new RoomTable(dataBase.getMovies(), 200, dataBase);
        TableView room1 = roomTable.getTableViewRoom(200);
        room1.setMinWidth(650);
        ObservableList<Movie> moviesList = dataBase.getMovies();
        Collections.reverse(moviesList); // reverse the list
        RoomTable roomTable2 = new RoomTable(moviesList, 100, dataBase);
        TableView room2 = roomTable2.getTableViewRoom(100);
        Collections.reverse(moviesList); // reverse the list again to make the order default again
        room2.setMinWidth(650);

        // add objects to the wrappers
        topVbox.getChildren().addAll(menuBar, purchaseLabel);

        // set default purchase info
        setDefaultPurchaseInfo();

        // Tableview click events
        room1.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedMovie = (Room)room1.getSelectionModel().getSelectedItem();
                text = "Room 1";
                roomHeader.setText(text);
                selectedMovieHeader.setText(selectedMovie.getTitle());
                lblStartTime.setText(selectedMovie.getStartTime());
                lblEndTime.setText(selectedMovie.getEndTime());
                nrOfSeats.setValue(0);
                nameInput.setText("");
                bottomPane.setVisible(true);
                addComponentsBottom();
            }
        });
        room2.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedMovie = (Room)room2.getSelectionModel().getSelectedItem();
                text = "Room 2";
                roomHeader.setText(text);
                selectedMovieHeader.setText(selectedMovie.getTitle());
                lblStartTime.setText(selectedMovie.getStartTime());
                lblEndTime.setText(selectedMovie.getEndTime());
                nrOfSeats.setValue(0);
                nameInput.setText("");
                bottomPane.setVisible(true);
                addComponentsBottom();
            }
        });
        addComponentsBottom();

        // Add tableView to the gridPane that will be located at the center
        GridPane centerPane = new GridPane();
        centerPane.add(room1Label, 1, 1); centerPane.add(room2Label, 2, 1); centerPane.add(room1, 1, 2); centerPane.add(room2, 2, 2);
        bottomPane.add(errorMessage, 1, 3);
        centerPane.setHgap(10);centerPane.setVgap(3); bottomPane.setHgap(50);bottomPane.setVgap(10);

        // make a border and set background around the panes
        centerPane.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID, null , null)));
        bottomPane.setBackground(new Background(new BackgroundFill(Color.web("#6B6BC4"), null, Insets.EMPTY)));

        // add content to container
        container.setTop(topVbox);
        container.setCenter(centerPane);
        container.setBottom(bottomPane);

        // deduct amount of seats from the tableviews List
        btnPurchaseTicket.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if (!nameInput.getText().isEmpty()){
                    TicketOrder order = new TicketOrder(selectedMovie, nrOfSeats.getValue(), nameInput.getText(), LocalDateTime.now());
                    if (roomHeader.getText().equals("Room 1")){
                        for(Room room : dataBase.room1){
                            if (room.getTitle().equals(order.getRoom().getTitle())) {
                                if ((room.getSeats() - nrOfSeats.getValue()) < 0){ // if the purchase amount results in seats < 0
                                    errorMessage.setText("Not enough tickets available!");
                                }
                                else {
                                    room.setSeats((room.getSeats() - nrOfSeats.getValue())); // deduct nrOfSeats from the list in the DataBase object
                                    room1.refresh();// refresh the tableView
                                }
                            }
                        }
                    }
                    else{
                        for(Room room : dataBase.room2){
                            if (room.getTitle().equals(order.getRoom().getTitle())) {
                                if ((room.getSeats() - nrOfSeats.getValue()) < 0){ // determines if there are enough tickets available
                                    errorMessage.setText("Not enough tickets available!");
                                }
                                else{
                                    room.setSeats((room.getSeats() - nrOfSeats.getValue())); // deduct nrOfSeats from the list in the DataBase object
                                    room2.refresh(); // refresh the tableView
                                }
                            }
                        }
                    }
                }
                else{
                    if (nrOfSeats.getValue() == 0){
                        errorMessage.setText("Select desired amount of tickets!");
                    }
                    else {
                        errorMessage.setText("Enter your name!");
                    }
                }

            }
        });

        //Clear the purchase field upon click of the button
        btnClearPurchaseField.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                removeComponentsBottom();
                bottomPane.setVisible(false);
            }
        });

        Scene scene = new Scene(container);

        // Jmetro for styling
        JMetro jMetro = new JMetro(Style.DARK);
        container.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        jMetro.setScene(scene);
        //scene.getStylesheets().add("style.css"); // apply css styling
        windowStage.setScene(scene);

        // Show window
        windowStage.show();
    }

    // Default purchase field values
    public void setDefaultPurchaseInfo(){
        titleHeader = new Label("Room");
        roomHeader = new Label("");
        movieHeader = new Label("Movie title");
        selectedMovieHeader = new Label("");
        lblStartHeader = new Label("Start");
        lblEndHeader = new Label("End");
        lblStartTime = new Label("");
        lblEndTime = new Label("");
        lblSeatsHeader = new Label("No. of seats");
        lblNameHeader = new Label("Name");
        nameInput = new TextField();
        nrOfSeats = new ChoiceBox<>();
        nrOfSeats.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        nrOfSeats.setValue(0);
    }

    // add components to the GridPane
    public void addComponentsBottom(){
        bottomPane.add(titleHeader, 1,0); bottomPane.add(roomHeader, 2,0); bottomPane.add(movieHeader, 3,0); bottomPane.add(selectedMovieHeader, 4,0);
        bottomPane.add(lblStartHeader, 1,1); bottomPane.add(lblStartTime, 2,1); bottomPane.add(lblSeatsHeader, 3,1); bottomPane.add(nrOfSeats, 4,1); bottomPane.add(btnPurchaseTicket, 5,1);
        bottomPane.add(lblEndHeader, 1,2); bottomPane.add(lblEndTime, 2,2); bottomPane.add(lblNameHeader, 3,2); bottomPane.add(nameInput, 4,2); bottomPane.add(btnClearPurchaseField, 5,2);
    }

    // remove components from the GridPane
    public void removeComponentsBottom(){
        bottomPane.getChildren().removeAll(titleHeader,roomHeader, movieHeader, selectedMovieHeader,
                lblStartHeader, lblStartTime, lblSeatsHeader, nrOfSeats, btnPurchaseTicket,
                lblEndHeader, lblEndTime, lblNameHeader, nameInput, btnClearPurchaseField);
    }
}


