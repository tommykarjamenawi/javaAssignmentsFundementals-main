package nl.inholland.javafx.model;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.inholland.javafx.dal.Database;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RoomTable {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");
    private TableView tableview;
    private Database dataBase;

    public RoomTable(ObservableList<Movie> movies, int seats, Database db){
        dataBase = db;
        tableview = new TableView();
        int increaseTime = 15;
        if(dataBase.room1.size() <= 0 || dataBase.room2.size() <= 0) {
            for (Movie movie : movies) { // loop through list of movies
                LocalDateTime startTime = LocalDateTime.now().plusMinutes(increaseTime);
                LocalDateTime endTime = startTime.plusMinutes(movie.getDuration());
                //room.add(new Room(startTime, endTime, movie,seats)); // add movie to a room
                if (seats == 200) {
                    dataBase.room1.add(new Room(startTime, endTime, movie, seats)); // add movie to a room
                }
                else{
                    dataBase.room2.add(new Room(startTime, endTime, movie, seats)); // add movie to a room
                }
                increaseTime += (movie.getDuration() + 15); // increase time to make the times not overlap each other
            }

        }
    }

    public TableView getTableViewRoom(int seats){
        TableColumn<Room, String> startColumn = new TableColumn<Room, String>("Start");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<Room, String> endColumn = new TableColumn<>("End");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn<Room, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Room, Integer> seatsColumn = new TableColumn<>("Seats");
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        seatsColumn.setMinWidth(75);
        TableColumn<Room, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableview.getColumns().addAll(startColumn, endColumn, titleColumn, seatsColumn, priceColumn);
        if (seats == 200){
            tableview.setItems(dataBase.room1);
        }
        else{
            tableview.setItems(dataBase.room2);
        }
        return tableview;
    }
}
