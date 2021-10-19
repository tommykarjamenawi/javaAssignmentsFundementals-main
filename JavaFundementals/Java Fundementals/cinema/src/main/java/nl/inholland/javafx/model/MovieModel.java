package nl.inholland.javafx.model;

import javafx.beans.property.SimpleStringProperty;

public class MovieModel {
    //movie model that contain table view data
    SimpleStringProperty start, end, title, seats, price;

    public MovieModel(String start, String end, String title, String  seats, String price) {
        this.start = new SimpleStringProperty(start);
        this.end = new SimpleStringProperty(end);
        this.title = new SimpleStringProperty(title);
        this.seats = new SimpleStringProperty(seats);
        this.price = new SimpleStringProperty(price);
    }

    public String getStart() {
        return start.get();
    }

    public SimpleStringProperty startProperty() {
        return start;
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public String getEnd() {
        return end.get();
    }

    public SimpleStringProperty endProperty() {
        return end;
    }

    public void setEnd(String end) {
        this.end.set(end);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getSeats() {
        return seats.get();
    }

    public SimpleStringProperty seatsProperty() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats.set(seats);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }
}
