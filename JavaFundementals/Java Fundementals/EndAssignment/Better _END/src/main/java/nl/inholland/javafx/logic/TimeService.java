package nl.inholland.javafx.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.inholland.javafx.model.Movie;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeService {

    public TimeService(ObservableList<Movie> movies){

        String openingHours = "10:00";
        String closingHours = "00:00";
        for (Movie movie : movies){
            int hours = movie.getDuration() / 60; // calculate total hours
            int minutes = movie.getDuration() % 60; // calculate total minutes
            if (LocalTime.now().getMinute() > 30){

            }
        }

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //LocalDateTime formatDateTime = LocalDateTime.parse(time, formatter);

        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = localDate.format(formatter);


    }
}
