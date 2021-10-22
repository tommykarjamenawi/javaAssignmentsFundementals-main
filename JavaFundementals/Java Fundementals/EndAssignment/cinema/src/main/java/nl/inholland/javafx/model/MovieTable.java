package nl.inholland.javafx.model;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.logic.MovieLogic;

public class MovieTable {
    private TableView tableview;
    private MovieLogic movieLogic;
    Database database;

    public MovieTable(Database db){
        this.database = db;
        tableview = new TableView();
        movieLogic = new MovieLogic(database);
    }

    public TableView getTableViewRoom(){
        TableColumn<Room, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Room, Integer> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        durationColumn.setMinWidth(100);
        TableColumn<Room, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableview.getColumns().addAll(titleColumn, durationColumn, priceColumn);
        tableview.setItems(movieLogic.getMovies());

        return tableview;
    }
}
