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
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.logic.MovieLogic;
import nl.inholland.javafx.logic.RoomLogic;
import nl.inholland.javafx.model.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class ManageShowings {
    private Database dataBase;
    private MovieLogic movieLogic;
    private RoomLogic roomLogic;
    private GridPane topPane;
    private GridPane centerPane;
    private GridPane bottomPane;
    private GridPane addingPane;
    private HBox errorHBox;
    private Movie selectedcbMovie;
    private String text;
    private RoomData selectedMovie;
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
        movieLogic = new MovieLogic(dataBase);
        roomLogic = new RoomLogic(dataBase);
        Stage window = new Stage();
        window.setTitle("Fantastic Cinema -- -- manage showings -- username: " + user.getUserName());
        lblErrorMessage = new Label("");

        // Make container
        BorderPane container = new BorderPane();
        initializeContent(); // initialize the Layout
        setDefaultPurchaseInfo(); // initialize the adding field

        errorHBox.getChildren().add(lblErrorMessage);
        // call class NavigationBar top get the correct menuBar
        NavigationBar navigationBar = new NavigationBar();
        MenuBar menuBar = navigationBar.getMenuBar(user, window, dataBase);
        Label lblManageShowings = new Label("Manage showings");
        lblManageShowings.setId("pageHeader");

        topPane.add(menuBar, 1, 0);
        topPane.add(lblManageShowings, 1, 1);

        // text above the tableviews
        Label lblRoom1 = new Label("Room 1");
        Label lblRoom2 = new Label("Room 2");

        // make tableview for room 1 and room 2
        RoomTable roomTable = new RoomTable(movieLogic.getMovies(), 200, dataBase);
        TableView room1 = roomTable.getTableViewRoom(200);
        room1.setMinWidth(650);
        ObservableList<Movie> moviesList = movieLogic.getMovies();
        Collections.reverse(moviesList); // reverse the list
        RoomTable roomTable2 = new RoomTable(moviesList, 100, dataBase);
        TableView room2 = roomTable2.getTableViewRoom(100);
        Collections.reverse(moviesList); // reverse the list again to make the order default again
        room2.setMinWidth(650);

        // Tableview click events
        room1.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedMovie = (RoomData)room1.getSelectionModel().getSelectedItem();
                int index = room1.getSelectionModel().getSelectedIndex();
                cbMovie.getItems().clear(); // remove the content of the comboBox
                for (Movie movie : movieLogic.getMovies()){
                    cbMovie.getItems().add(movie);
                }
                cbMovie.getSelectionModel().select(index); // default selection
                text = "Room 1";
                cbRoom.setValue(text);
                lblNoOfSeats.setText("200");
                addingPane.setVisible(true); // make adding field visible
                addComponentsAddingField(); // add content to the GridPane
            }
        });

        room2.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedMovie = (RoomData)room2.getSelectionModel().getSelectedItem();
                int index = room2.getSelectionModel().getSelectedIndex();
                cbMovie.getItems().clear(); // remove the content of the comboBox
                ObservableList<Movie> tempList = movieLogic.getMovies();
                Collections.reverse(tempList); // reverse the list of movies to match the click events
                for (Movie movie : tempList){
                    cbMovie.getItems().add(movie);
                }
                cbMovie.getSelectionModel().select(index); // default selection
                Collections.reverse(tempList); // reverse the reversed list to set is as normal
                text = "Room 2";
                cbRoom.setValue(text);
                lblNoOfSeats.setText("100");
                addingPane.setVisible(true); // make adding field visible
                addComponentsAddingField(); // add content to the GridPane
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

        //add listener to comboBox(display price based on selected movie)
        cbMovie.valueProperty().addListener(new ChangeListener<Movie>() {
            @Override
            public void changed(ObservableValue<? extends Movie> observableValue, Movie before, Movie after) {
                selectedcbMovie = after;
                lblPrice.setText(String.valueOf(after.getPrice()));
            }
        });

        // Check if user is filled the date/time in the correct format
        lblEndTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (hourPicker.getText().length() >= 5){
                    try
                    {
                        LocalDateTime dateTime = LocalDateTime.parse((datePicker.getValue().toString() + "T" + hourPicker.getText()));
                    }
                    catch(Exception e)
                    {
                        //new Alert(Alert.AlertType.ERROR, "Provide Correct Date or Hour format!").show();
                        lblErrorMessage.setText("Provide Correct Date or Hour format!");
                    }
                }
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
                if (cbMovie.getValue() == null || cbRoom.getValue().isEmpty() || datePicker.getEditor().getText().isEmpty() || hourPicker.getText().isEmpty()){
                    lblErrorMessage.setText("Please fill all fields!");
                }
                else{
                    selectedcbMovie = cbMovie.getValue();
                    // String to LocalDateTRime conversion.
                    String startTime = (datePicker.getValue().toString() + "T" + hourPicker.getText());
                    LocalDateTime dateTime1 = LocalDateTime.parse(startTime);
                    String temp1 = dateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    String endTime = (datePicker.getValue().toString() + "T" + hourPicker.getText());
                    LocalDateTime dateTime2 = dateTime1.plusMinutes(selectedcbMovie.getDuration());
                    String temp2 = dateTime2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    RoomData tempRoomData = new RoomData(dateTime1, dateTime2, selectedcbMovie, Integer.parseInt(lblNoOfSeats.getText()));
                    // check if inserted time overlaps other movie times in the designated room.
                    if(lblNoOfSeats.getText().equals("200")){
                        int count = 1;
                        int countFirstMovie = 1;
                        for(RoomData roomData : roomLogic.getLogicRoom1()){
                            Duration diff1 = Duration.between(roomData.getStart(), dateTime1);
                            Duration diff2 = Duration.between(roomData.getEnd(), dateTime2);
                            Duration diff3 = Duration.between(roomData.getStart(), dateTime2);
                            Duration diff4 = Duration.between(roomData.getEnd(), dateTime1);
                            if (countFirstMovie == 1){ // Check if movie is earlier than a movie in the tableview
                                if (dateTime1.isBefore(roomData.getStart()) || dateTime1.isBefore(roomData.getEnd()) || dateTime2.isBefore(roomData.getStart()) || dateTime2.isBefore(roomData.getEnd())){
                                    //new Alert(Alert.AlertType.WARNING, "Provide date/time later than a movie in the Tableview!").show();
                                    lblErrorMessage.setText("date/time must be later than an existing showing ");
                                    break;
                                }
                            }
                            if (count == roomLogic.getLogicRoom1().size() && ((diff1.toMinutes() >= 15) && (diff2.toMinutes() >= 15) && (diff3.toMinutes() >= 15) && (diff4.toMinutes() >= 15))){
                                roomLogic.addLogicRoom1(tempRoomData);
                                room1.refresh();
                            }
                            if (!(diff1.toMinutes() >= 15) || !(diff2.toMinutes() >= 15) || !(diff3.toMinutes() >= 15) || !(diff4.toMinutes() >= 15)){
                                lblErrorMessage.setText("Showing cannot overlap in room 1!");
                            }
                            count++;
                            countFirstMovie++;
                        }
                    }
                    else{
                        int count = 1;
                        for(RoomData roomData : roomLogic.getLogicRoom2()){
                            // calculate the difference between the movies
                            Duration diff1 = Duration.between(roomData.getStart(), dateTime1);
                            Duration diff2 = Duration.between(roomData.getEnd(), dateTime2);
                            Duration diff3 = Duration.between(roomData.getStart(), dateTime2);
                            Duration diff4 = Duration.between(roomData.getEnd(), dateTime1);
                            if (count == roomLogic.getLogicRoom2().size() && ((diff1.toMinutes() >= 15) && (diff2.toMinutes() >= 15) && (diff3.toMinutes() >= 15) && (diff4.toMinutes() >= 15))){
                                roomLogic.addLogicRoom2(tempRoomData);
                                room2.refresh();
                            }
                            if (!(diff1.toMinutes() >= 15) || !(diff2.toMinutes() >= 15) || !(diff3.toMinutes() >= 15) || !(diff4.toMinutes() >= 15)){
                                lblErrorMessage.setText("Showing cannot overlap in room 2!");
                            }
                            count++;
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
        scene.getStylesheets().add("style.css"); // apply css styling
        window.setScene(scene);

        // Show window
        window.show();
    }

    // Will initialize the layout of the form
    public void initializeContent(){
        // Make wrappers to put inside the container
        topPane = new GridPane();
        centerPane = new GridPane();
        centerPane.setHgap(10);
        centerPane.setVgap(5);
        bottomPane = new GridPane();
        addingPane = new GridPane();
        addingPane.setVisible(false); // adding field is not visible at startup
        addingPane.setHgap(40);
        addingPane.setVgap(10);
        errorHBox = new HBox(10);
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
