package nl.inholland.javafx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

public class Room2TableView {
    private final TableView<Room> room2View;

    public TableView<Room> getRoom2View() {
        return room2View;
    }

    public Room2TableView(ObservableList<Movie> movies2) {
        room2View = new TableView<>();

        ObservableList<Room> room2 = FXCollections.observableArrayList();
        for (Movie movie : movies2){
            room2.add(new Room(LocalDateTime.parse("2021-05-21T12:30:00"), LocalDateTime.parse("2021-05-21T14:30:00"), movie, 100));
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

        room2View.getColumns().addAll(startColumn, endColumn, titleColumn, seatsColumn, priceColumn);
        room2View.setItems(room2);
    }
}
