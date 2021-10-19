package nl.inholland.javafx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.ui.MainWindow;

import java.time.LocalDateTime;

public class RoomScreens {
    //tables view, movieModels, and data of 2 modes for 2 different views
    static TableView<MovieModel> movieTableView1 = new TableView<>();
    static TableView<MovieModel> movieTableView2 = new TableView<>();
    static ObservableList<Movie> movies = new Database().getMovies();
    static ObservableList<MovieModel> movieModels1 = FXCollections.observableArrayList();
    static ObservableList<MovieModel> movieModels2 = FXCollections.observableArrayList();

    public static void screen() {
        //initlizing data for both tables
        TableColumn<MovieModel, String> start1 = new TableColumn<>("Start");
        TableColumn<MovieModel, String> end1 = new TableColumn<>("End");
        TableColumn<MovieModel, String> title1 = new TableColumn<>("Title");
        TableColumn<MovieModel, String> seats1 = new TableColumn<>("Seats");
        TableColumn<MovieModel, String> price1 = new TableColumn<>("Price");
        TableColumn<MovieModel, String> start2 = new TableColumn<>("Start");
        TableColumn<MovieModel, String> end2 = new TableColumn<>("End");
        TableColumn<MovieModel, String> title2 = new TableColumn<>("Title");
        TableColumn<MovieModel, String> seats2 = new TableColumn<>("Seats");
        TableColumn<MovieModel, String> price2 = new TableColumn<>("Price");
        initializeCol(start1, end1, title1, seats1, price1);
        initializeCol(start2, end2, title2, seats2, price2);

        //fill data add data in tables
        fillData();
        //if row select go to room details and get all information
        movieTableView1.setOnMouseClicked((MouseEvent event) -> {
            if (movieTableView1.getSelectionModel().getSelectedItem() != null) {
                MovieModel model = movieTableView1.getSelectionModel().getSelectedItem();
                RoomDetails.vbox("Room 1", model.getTitle(), model.getStart(), model.getEnd(), Integer.parseInt(String.valueOf(model.getSeats())));
            }
        });

        movieTableView2.setOnMouseClicked((MouseEvent event) -> {
            if (movieTableView2.getSelectionModel().getSelectedItem() != null) {
                MovieModel model = movieTableView2.getSelectionModel().getSelectedItem();
                RoomDetails.vbox("Room 2", model.getTitle(), model.getStart(), model.getEnd(), Integer.parseInt(String.valueOf(model.getSeats())));
            }
        });

        //set all data to tables
        movieTableView1.getColumns().addAll(start1, end1, title1, seats1, price1);
        movieTableView1.setItems(movieModels1);
        movieTableView2.getColumns().addAll(start2, end2, title2, seats2, price2);
        movieTableView2.setItems(movieModels2);
        start1.setMinWidth(20);
        start2.setMinWidth(20);
        end1.setMinWidth(20);
        end2.setMinWidth(20);
        title1.setMinWidth(20);
        title2.setMinWidth(20);
        seats1.setMinWidth(80);
        seats2.setMinWidth(80);
        price1.setMinWidth(20);
        price2.setMinWidth(20);
        movieTableView1.setPrefWidth(580);
        movieTableView2.setPrefWidth(580);

        //table 1 add in left and table 2 in right
        MainWindow.container.setLeft(movieTableView1);
        MainWindow.container.setRight(movieTableView2);
    }

    private static void initializeCol(TableColumn<MovieModel, String> start2, TableColumn<MovieModel, String> end2, TableColumn<MovieModel, String> title2, TableColumn<MovieModel, String> seats2, TableColumn<MovieModel, String> price2) {
        start2.setCellValueFactory(new PropertyValueFactory<>("start"));
        end2.setCellValueFactory(new PropertyValueFactory<>("end"));
        title2.setCellValueFactory(new PropertyValueFactory<>("title"));
        seats2.setCellValueFactory(new PropertyValueFactory<>("seats"));
        price2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public static void fillData() {
        // get 2 local time 1 for table 1 and other for table 2
        LocalDateTime start1 = LocalDateTime.now();
        LocalDateTime end1;
        LocalDateTime start2 = LocalDateTime.now();
        LocalDateTime end2;
        //run loop on observable list and add in table
        for (int i = 0; i < movies.size(); i++) {
            //duration make for table1
            int duration1 = movies.get(i).getDuration();
            int hours1 = duration1 / 60;
            int min1 = duration1 % 60;

            //duration for table2
            int duration2 = movies.get(movies.size() - i - 1).getDuration();
            int hours2 = duration2 / 60;
            int min2 = duration2 % 60;

            //end time for both table movies (of index i) we get end time by duration of movie and add start time + duration time
            end1 = start1.plusHours(hours1).plusMinutes(min1);
            end2 = start2.plusHours(hours2).plusMinutes(min2);
            //if start end time is more than 10 then add else it means time is 0 to 9:59
            //we will not add it
            if (start1.getHour() >= 10 && end1.getHour() >= 10) {
                //syntax to show time
                String s1 = start1.getDayOfMonth() + "-" + start1.getMonthValue() + "-" + start1.getYear() + " " + start1.getHour() + ":" + start1.getMinute();
                String e1 = end1.getDayOfMonth() + "-" + end1.getMonthValue() + "-" + end1.getYear() + " " + end1.getHour() + ":" + end1.getMinute();

                String s2 = start2.getDayOfMonth() + "-" + start2.getMonthValue() + "-" + start2.getYear() + " " + start2.getHour() + ":" + start2.getMinute();
                String e2 = end2.getDayOfMonth() + "-" + end2.getMonthValue() + "-" + end2.getYear() + " " + end2.getHour() + ":" + end2.getMinute();

                //add in tables models
                movieModels1.add(new MovieModel(s1, e1,
                        movies.get(i).getTitle(), "200", String.valueOf(movies.get(i).getPrice())));
                movieModels2.add(new MovieModel(s2, e2,
                        movies.get(movies.size() - i - 1).getTitle(), "200", String.valueOf(movies.get(movies.size() - i - 1).getPrice())));
            }
            //add start time 15 minutes because there should be duration of 15 min between 2 movues
            start1 = end1.plusMinutes(15);
            start2 = end2.plusMinutes(15);
        }
    }
}
