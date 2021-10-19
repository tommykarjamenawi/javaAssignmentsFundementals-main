package nl.inholland.javafx.model;

import java.time.LocalDateTime;

public class TicketOrder {
    private Room room;
    private int nrOfSeats;
    private String name;
    private LocalDateTime purchaseDate;

    public TicketOrder(Room room, int nrOfSeats, String name, LocalDateTime purchaseDate) {
        this.room = room;
        this.nrOfSeats = nrOfSeats;
        this.name = name;
        this.purchaseDate = purchaseDate;
    }

    public Room getRoom() {
        return room;
    }

    public int getNrOfSeats() {
        return nrOfSeats;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }
}
