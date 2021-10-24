package nl.inholland.javafx.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RoomData {

    public RoomData(LocalDateTime start, LocalDateTime end, Movie movie, int seats) {
        this.movie = movie;
        this.start = start;
        this.startTime = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.end = end;
        this.endTime = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.title = movie.getTitle();
        this.seats = seats;
        this.price = movie.getPrice();
    }

    public RoomData(String startTime, String endTime, Movie movie, int seats) {
        this.movie = movie;
        this.start = LocalDateTime.parse(startTime);
        this.startTime = startTime;
        this.end = LocalDateTime.parse(endTime);
        this.endTime = endTime;
        this.title = movie.getTitle();
        this.seats = seats;
        this.price = movie.getPrice();
    }

    public RoomData(){} // to make an temporary empty roomData

    // Room properties
    private Movie movie;
    private LocalDateTime start;
    private LocalDateTime end;
    private String startTime;
    private String endTime;
    private String title;
    private int seats;
    private double price;

    // Getters & Setters
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStartTime() {return startTime;}

    public void setStartTime(String startTime) {this.startTime = startTime;}

    public String getEndTime() {return endTime;}

    public void setEndTime(String endTime) {this.endTime = endTime;}
}
