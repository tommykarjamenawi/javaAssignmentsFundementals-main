package nl.inholland.javafx.model;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.logic.RoomLogic;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RoomTable {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");
    private TableView tableview;
    private RoomLogic roomLogic;
    private Database dataBase;

    public RoomTable(ObservableList<Movie> movies, int seats, Database db){
        this.dataBase = db;
        roomLogic = new RoomLogic(dataBase);
        tableview = new TableView();
        int increaseTime = 15;
        if(roomLogic.getLogicRoom1().size() <= 0 || roomLogic.getLogicRoom2().size() <= 0) {
            for (Movie movie : movies) { // loop through list of movies
                LocalDateTime startTime = LocalDateTime.now().plusMinutes(increaseTime);
                LocalDateTime endTime = startTime.plusMinutes(movie.getDuration());
                if (seats == 200) {
                    roomLogic.addLogicRoom1(new RoomData(startTime, endTime, movie, seats)); // add movie to a room
                }
                else{
                    roomLogic.addLogicRoom2(new RoomData(startTime, endTime, movie, seats)); // add movie to a room
                }
                increaseTime += (movie.getDuration() + 15); // increase time to make the times not overlap each other
            }

        }
    }

    public TableView getTableViewRoom(int seats){
        TableColumn<RoomData, String> startColumn = new TableColumn<RoomData, String>("Start");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<RoomData, String> endColumn = new TableColumn<>("End");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn<RoomData, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<RoomData, Integer> seatsColumn = new TableColumn<>("Seats");
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        seatsColumn.setMinWidth(75);
        TableColumn<RoomData, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableview.getColumns().addAll(startColumn, endColumn, titleColumn, seatsColumn, priceColumn);
        if (seats == 200){
            tableview.setItems(roomLogic.getLogicRoom1());
        }
        else{
            tableview.setItems(roomLogic.getLogicRoom2());
        }
        return tableview;
    }
}
