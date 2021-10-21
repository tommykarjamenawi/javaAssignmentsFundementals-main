package nl.inholland.javafx.model;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.inholland.javafx.dal.Database;

public class MovieTable {
    private TableView tableview;
    private Database dataBase;

    public MovieTable(Database db){
        dataBase = db;
        tableview = new TableView();
    }

    public TableView getTableViewRoom(){
        TableColumn<Room, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Room, Integer> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        TableColumn<Room, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableview.getColumns().addAll(titleColumn, durationColumn, priceColumn);
        tableview.setItems(dataBase.getMovies());

        return tableview;
    }
}
