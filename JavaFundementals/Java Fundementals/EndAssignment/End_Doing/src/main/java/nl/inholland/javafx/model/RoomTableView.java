package nl.inholland.javafx.model;

import com.sun.jdi.DoubleValue;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDateTime;
import java.util.List;

public class RoomTableView {
    public TableView<Room> room1View;
    public TableView<Room> room2View;

    public TableView<Room> getRoom1View() {
        return room1View;
    }
    public TableView<Room> getRoom2View() {

        return room2View;
    }

    public RoomTableView(ObservableList<Movie> movies1, ObservableList<Movie> movies2) {
        room1View = new TableView<>();
        room2View = new TableView<>();

        ObservableList<Room> room1 = FXCollections.observableArrayList();
        ObservableList<Room> room2 = FXCollections.observableArrayList();
        for (Movie movie : movies1){
            room1.add(new Room(LocalDateTime.parse("2021-05-21T10:30:00"), LocalDateTime.parse("2021-05-21T11:30:00"), movie, 200));
        }
        for (Movie movie : movies2){
            room2.add(new Room(LocalDateTime.parse("2021-05-21T15:30:00"), LocalDateTime.parse("2021-05-21T16:30:00"), movie, 100));
        }

        TableColumn<Room, LocalDateTime> startColumn = new TableColumn<>("Start");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        TableColumn<Room, LocalDateTime> endColumn = new TableColumn<>("End");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        TableColumn<Room, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Room, Integer> seatsColumn = new TableColumn<>("Seats");
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        TableColumn<Room, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //tableView.getColumns().addAll(startColumn, endColumn, titleColumn, seatsColumn, priceColumn);
        room1View.getColumns().addAll(startColumn, endColumn, titleColumn, seatsColumn, priceColumn);
        room1View.setItems(room1);

        room2View.getColumns().addAll(startColumn, endColumn, titleColumn, seatsColumn, priceColumn);
        room2View.setItems(room2);
    }
}
