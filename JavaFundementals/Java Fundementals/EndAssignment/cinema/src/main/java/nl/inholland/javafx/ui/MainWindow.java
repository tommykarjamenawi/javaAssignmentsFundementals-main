package nl.inholland.javafx.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.logic.MovieLogic;
import nl.inholland.javafx.logic.RoomLogic;
import nl.inholland.javafx.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MainWindow {
    private final Stage windowStage;
    private MovieLogic movieLogic;
    private RoomLogic roomLogic;
    private String text;
    private RoomData selectedMovie;
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
    private Label purchaseLabel;
    private Label room1Label;
    private Label room2Label;
    private TextField txtTitleFilter;

    public MainWindow(Person user, Database db) {
        dataBase = db;
        movieLogic = new MovieLogic(dataBase);
        roomLogic = new RoomLogic(dataBase);
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

        makeLabels();

        // make tableviews
        RoomTable roomTable = new RoomTable(movieLogic.getMovies(), 200, dataBase);
        TableView room1 = roomTable.getTableViewRoom(200);
        room1.setMinWidth(580);
        ObservableList<Movie> moviesList = movieLogic.getMovies();
        Collections.reverse(moviesList); // reverse the list
        RoomTable roomTable2 = new RoomTable(moviesList, 100, dataBase);
        TableView room2 = roomTable2.getTableViewRoom(100);
        Collections.reverse(moviesList); // reverse the list again to make the order default again
        room2.setMinWidth(580);

        txtTitleFilter = new TextField(); // initialize filter
        // add objects to the wrappers
        topVbox.getChildren().addAll(menuBar, purchaseLabel, txtTitleFilter);

        // set default purchase info
        setDefaultPurchaseInfo();

        // modal windows closing request
        windowStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        new ExitConfirmation().closePopup(windowStage);
                    }
                });
            }
        });

        // Tableview click events
        room1.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedMovie = (RoomData)room1.getSelectionModel().getSelectedItem();
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
                selectedMovie = (RoomData)room2.getSelectionModel().getSelectedItem();
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
        bottomPane.setBackground(new Background(new BackgroundFill(Color.web("#9d9de5"), null, Insets.EMPTY)));

        // add content to container
        container.setTop(topVbox);
        container.setCenter(centerPane);
        container.setBottom(bottomPane);

        // deduct amount of seats from the tableviews List
        btnPurchaseTicket.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if (!nameInput.getText().isEmpty() && nrOfSeats.getValue() >= 1){
                    Alert.AlertType type = Alert.AlertType.CONFIRMATION;
                    Alert alert = new Alert(type, "");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(windowStage);
                    alert.getDialogPane().setHeaderText("Confirm purchase");
                    alert.getDialogPane().setContentText("Do you want complete purchase ticket(s)?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        TicketOrder order = new TicketOrder(selectedMovie, nrOfSeats.getValue(), nameInput.getText(), LocalDateTime.now());
                        if (roomHeader.getText().equals("Room 1")){
                            for(RoomData roomData : roomLogic.getLogicRoom1()){
                                if (roomData.getTitle().equals(order.getRoom().getTitle())) {
                                    if ((roomData.getSeats() - nrOfSeats.getValue()) < 0){ // if the purchase amount results in seats < 0
                                        errorMessage.setText("Not enough tickets available!");
                                    }
                                    else {
                                        roomData.setSeats((roomData.getSeats() - nrOfSeats.getValue())); // deduct nrOfSeats from the list in the DataBase object
                                        removeComponentsBottom();
                                        errorMessage.setText("");
                                        room1.refresh();// refresh the tableView
                                    }
                                }
                            }
                        }
                        else{
                            for(RoomData roomData : roomLogic.getLogicRoom2()){
                                if (roomData.getTitle().equals(order.getRoom().getTitle())) {
                                    if ((roomData.getSeats() - nrOfSeats.getValue()) < 0){ // determines if there are enough tickets available
                                        errorMessage.setText("Not enough tickets available!");
                                    }
                                    else{
                                        roomData.setSeats((roomData.getSeats() - nrOfSeats.getValue())); // deduct nrOfSeats from the list in the DataBase object
                                        removeComponentsBottom();
                                        errorMessage.setText("");
                                        room2.refresh(); // refresh the tableView
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    if (nrOfSeats.getValue() == 0){
                        errorMessage.setText("Select amount of tickets!");
                    }
                    else {
                        errorMessage.setText("Enter your name!");
                    }
                }

            }
        });

        // listener for filter textfield
        txtTitleFilter.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (txtTitleFilter.getText().length() >= 2){
                    room1.setItems(filterList(roomLogic.getLogicRoom1(), txtTitleFilter.getText()));
                    room2.setItems(filterList(roomLogic.getLogicRoom2(), txtTitleFilter.getText()));
                }
                else{
                    room1.setItems(roomLogic.getLogicRoom1());
                    room2.setItems(roomLogic.getLogicRoom2());
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
        scene.getStylesheets().add("style.css"); // apply css styling
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
        nrOfSeats.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10);
        nrOfSeats.setValue(0);
    }

    // Filter the tableview
    private ObservableList<RoomData> filterList(List<RoomData> list, String searchText){
        List<RoomData> filteredList = new ArrayList<>();
        for (RoomData roomData : list){
            if(searchFindsOrder(roomData, searchText)) filteredList.add(roomData);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindsOrder(RoomData roomData, String searchText){
        return (roomData.getTitle().toLowerCase().contains(searchText.toLowerCase()));
    }

    private void makeLabels(){
        // make labels
        purchaseLabel = new Label("Purchase Tickets");
        purchaseLabel.setId("pageHeader");
        room1Label = new Label("room 1");
        room2Label = new Label("room 2");
        room1Label.setId("room1Label");
        room2Label.setId("room2Label");
        errorMessage = new Label("");
        errorMessage.setId("lblError");
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


