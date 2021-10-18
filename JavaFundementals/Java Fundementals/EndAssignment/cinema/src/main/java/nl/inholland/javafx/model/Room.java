package nl.inholland.javafx.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Room {

    public Room(LocalDateTime start, LocalDateTime end, Movie movie, int seats) {
        this.start = start;
        this.startTime = start.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.end = end;
        this.endTime = end.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.title = movie.getTitle();
        this.seats = seats;
        this.price = movie.getPrice();
    }
    // Room properties
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
