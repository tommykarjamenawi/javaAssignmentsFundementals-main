package nl.inholland.javafx.model;

import java.time.LocalDateTime;

public class Room {

    public Room(LocalDateTime start, LocalDateTime end, Movie movie, int seats) {
        this.start = start;
        this.end = end;
        this.title = movie.getTitle();
        this.seats = seats;
        this.price = movie.getPrice();
    }
    // Room properties
    private LocalDateTime start;
    private LocalDateTime end;
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
}
