package nl.inholland.javafx.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class ManageShowings {
    private Database dataBase;
    private GridPane topPane;
    private GridPane centerPane;
    private GridPane bottomPane;
    private GridPane addingPane;
    private HBox errorHBox;

    private Movie selectedcbMovie;
    private String text;
    private Room selectedMovie;
    private Label lblErrorMessage;

    private Label lblMovieTitleHeader;
    private ComboBox<Movie> cbMovie;
    private Label lblStartTimeHeader;
    private DatePicker datePicker;
    private TextField hourPicker;

    private Label lblRoomHeader;
    private ChoiceBox<String> cbRoom;
    private Label lblEndTimeHeader;
    private Label lblEndTime;
    private Button btnAddShowing;

    private Label lblNoOfSeatsHeader;
    private Label lblNoOfSeats;
    private Label lblPriceHeader;
    private Label lblPrice;
    private Button btnClearAddShowingsField;

    public ManageShowings(Person user, Database db){
        dataBase = db;
        Stage window = new Stage();
        window.setTitle("Fantastic Cinema -- -- manage showings -- username: " + user.getUserName());
        lblErrorMessage = new Label("");

        // Make container
        BorderPane container = new BorderPane();
        // Make wrappers to put inside the container
        topPane = new GridPane();
        centerPane = new GridPane();
        centerPane.setHgap(10);
        centerPane.setVgap(5);
        bottomPane = new GridPane();
        addingPane = new GridPane();
        addingPane.setVisible(false);
        addingPane.setHgap(40);
        addingPane.setVgap(10);
        errorHBox = new HBox(10);
        setDefaultPurchaseInfo(); // initialize the adding field

        errorHBox.getChildren().add(lblErrorMessage);
        // call class NavigationBar top get the correct menuBar
        NavigationBar navigationBar = new NavigationBar();
        MenuBar menuBar = navigationBar.getMenuBar(user, window, dataBase);
        Label lblManageShowings = new Label("Manage showings");


        topPane.add(menuBar, 1, 0);
        topPane.add(lblManageShowings, 1, 1);

        // text above the tableviews
        Label lblRoom1 = new Label("Room 1");
        Label lblRoom2 = new Label("Room 2");

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

        // Tableview click events
        room1.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedMovie = (Room)room1.getSelectionModel().getSelectedItem();
                int index = room1.getSelectionModel().getSelectedIndex();
                cbMovie.getItems().clear();
                for (Movie movie : dataBase.getMovies()){
                    cbMovie.getItems().add(movie);
                }
                cbMovie.getSelectionModel().select(index);
                text = "Room 1";
                cbRoom.setValue(text);
                lblNoOfSeats.setText("200");
                addingPane.setVisible(true);
                addComponentsAddingField();
            }
        });
        room2.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedMovie = (Room)room2.getSelectionModel().getSelectedItem();
                int index = room2.getSelectionModel().getSelectedIndex();
                cbMovie.getItems().clear();
                ObservableList<Movie> tempList = dataBase.getMovies();
                Collections.reverse(tempList);
                for (Movie movie : tempList){
                    cbMovie.getItems().add(movie);
                }
                cbMovie.getSelectionModel().select(index);
                Collections.reverse(tempList);
                text = "Room 2";
                cbRoom.setValue(text);
                lblNoOfSeats.setText("100");
                addingPane.setVisible(true);
                addComponentsAddingField();
            }
        });
        addComponentsAddingField();

        // add content to GridPanes
        centerPane.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID, null , null)));
        centerPane.add(lblRoom1, 1, 0); centerPane.add(lblRoom2, 2, 0);
        centerPane.add(room1, 1, 1); centerPane.add(room2, 2, 1);
        bottomPane.add(addingPane, 1, 0);
        bottomPane.add(errorHBox, 1, 1);

        // add content to container
        container.setTop(topPane);
        container.setCenter(centerPane);
        container.setBottom(bottomPane);

        // listener for date
        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate before, LocalDate after) {
                if (!hourPicker.getText().isEmpty()){
                    lblEndTime.setText((datePicker.getValue().toString() + " " + hourPicker.getText()));
                }
            }
        });

        // listener for hour
        hourPicker.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!datePicker.getEditor().getText().isEmpty()){
                    lblEndTime.setText((datePicker.getValue().toString() + " " + hourPicker.getText()));
                }
            }
        });

        //add listener to comboBox
        cbMovie.valueProperty().addListener(new ChangeListener<Movie>() {
            @Override
            public void changed(ObservableValue<? extends Movie> observableValue, Movie before, Movie after) {
                selectedcbMovie = after;
                lblPrice.setText(String.valueOf(after.getPrice()));
            }
        });

        // add listener to choiceBox
        cbRoom.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if (cbRoom.getValue().equals("Room 1")){
                    lblNoOfSeats.setText("100");
                }
                else{
                    lblNoOfSeats.setText("200");
                }
            }
        });

        // Add show to a room in the tableview
        btnAddShowing.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                // To  do.......
                if (cbMovie.getValue() == null || cbRoom.getValue().isEmpty() || datePicker.getEditor().getText().isEmpty() || hourPicker.getText().isEmpty()){
                    lblErrorMessage.setText("Please fill all fields!");
                }
                else{
                    selectedcbMovie = cbMovie.getValue();
                    String startTime = (datePicker.getValue().toString() + "T" + hourPicker.getText());
                    LocalDateTime dateTime1 = LocalDateTime.parse(startTime);
                    String temp1 = dateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    String endTime = (datePicker.getValue().toString() + "T" + hourPicker.getText());
                    LocalDateTime dateTime2 = dateTime1.plusMinutes(selectedcbMovie.getDuration());
                    //LocalDateTime dateTime2 = LocalDateTime.parse(endTime);
                    String temp2 = dateTime2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    int seats = Integer.parseInt(lblNoOfSeats.getText());
                    Room tempRoom = new Room(dateTime1, dateTime2, selectedcbMovie, seats);
                    if(lblNoOfSeats.getText().equals("200")){
                        for(Room room : dataBase.room1){
                            Duration diff1 = Duration.between(room.getStart(), dateTime1);
                            Duration diff2 = Duration.between(room.getEnd(), dateTime2);
                            if (diff1.toMinutes() > 15 || diff2.toMinutes() > 15){
                                dataBase.room1.add(tempRoom);
                                room1.refresh();
                            }
                            else{
                                lblErrorMessage.setText("Showing cannot overlap in room 1!");
                            }
                        }
                    }
                    else{
                        for(Room room : dataBase.room2){
                            Duration diff1 = Duration.between(room.getStart(), dateTime1);
                            Duration diff2 = Duration.between(room.getEnd(), dateTime2);
                            if (diff1.toMinutes() > 15 || diff2.toMinutes() > 15){
                                dataBase.room2.add(tempRoom);
                                room2.refresh();
                            }
                            else{
                                lblErrorMessage.setText("Showing cannot overlap in room 2!");
                            }
                        }
                    }
                }
            }
        });

        //Clear the adding field upon click of the button
        btnClearAddShowingsField.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                removeComponentsAddingPane();
                lblErrorMessage.setText("");
                addingPane.setVisible(false);
            }
        });

        Scene scene = new Scene(container);

        // Jmetro for styling
        JMetro jMetro = new JMetro(Style.DARK);
        container.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        jMetro.setScene(scene);
        //scene.getStylesheets().add("style.css"); // apply css styling
        window.setScene(scene);

        // Show window
        window.show();
    }

    // Default Add Showings field values
    public void setDefaultPurchaseInfo(){
        lblErrorMessage = new Label("");

        lblMovieTitleHeader = new Label("Movie title");
        cbMovie = new ComboBox<Movie>();
        for (Movie movie : dataBase.getMovies()){
            cbMovie.getItems().add(movie);
        }
        lblStartTimeHeader = new Label("Start");
        datePicker = new DatePicker();
        hourPicker = new TextField("");

        lblRoomHeader = new Label("Room");
        cbRoom = new ChoiceBox<>(); cbRoom.getItems().addAll("Room 1", "Room 2");
        lblEndTimeHeader = new Label("End");
        lblEndTime = new Label("");
        btnAddShowing = new Button("Add showing");

        lblNoOfSeatsHeader = new Label("No. of seats");
        lblNoOfSeats = new Label("...");
        lblPriceHeader = new Label("Price");
        lblPrice = new Label("...");
        btnClearAddShowingsField = new Button("Clear");
    }

    // add components to the GridPane
    public void addComponentsAddingField(){
        addingPane.add(lblMovieTitleHeader, 1,0); addingPane.add(cbMovie, 2,0); addingPane.add(lblStartTimeHeader, 3,0); addingPane.add(datePicker, 4,0); addingPane.add(hourPicker, 5,0);
        addingPane.add(lblRoomHeader, 1,1); addingPane.add(cbRoom, 2,1); addingPane.add(lblEndTimeHeader, 3,1); addingPane.add(lblEndTime, 4,1); addingPane.add(btnAddShowing, 5,1);
        addingPane.add(lblNoOfSeatsHeader, 1,2); addingPane.add(lblNoOfSeats, 2,2); addingPane.add(lblPriceHeader, 3,2); addingPane.add(lblPrice, 4,2); addingPane.add(btnClearAddShowingsField, 5,2);
    }

    // remove components from the GridPane
    public void removeComponentsAddingPane(){
        addingPane.getChildren().removeAll(lblMovieTitleHeader,cbMovie, lblStartTimeHeader, datePicker, hourPicker,
                lblRoomHeader, cbRoom, lblEndTimeHeader, lblEndTime, btnAddShowing,
                lblNoOfSeatsHeader, lblNoOfSeats, lblPriceHeader, lblPrice, btnClearAddShowingsField);
    }
}