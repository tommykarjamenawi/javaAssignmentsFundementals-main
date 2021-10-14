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

public class Room1TableView {
    private final TableView<Room> room1View;
    //private final TableView<Room> room2View;

    public TableView<Room> getRoom1View() {
        return room1View;
    }
    //public TableView<Room> getRoom2View() { return room2View; }

    public Room1TableView(ObservableList<Movie> movies1) {
        room1View = new TableView<>();

        ObservableList<Room> room1 = FXCollections.observableArrayList();
        for (Movie movie : movies1){
            room1.add(new Room(LocalDateTime.parse("2021-05-21T10:30:00"), LocalDateTime.parse("2021-05-21T11:30:00"), movie, 200));
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

        room1View.getColumns().addAll(startColumn, endColumn, titleColumn, seatsColumn, priceColumn);
        room1View.setItems(room1);
    }
}
