package nl.inholland.javafx.model;

import java.time.LocalDateTime;

public class TicketOrder {
    private RoomData roomData;
    private int nrOfSeats;
    private String name;
    private LocalDateTime purchaseDate;

    public TicketOrder(RoomData roomData, int nrOfSeats, String name, LocalDateTime purchaseDate) {
        this.roomData = roomData;
        this.nrOfSeats = nrOfSeats;
        this.name = name;
        this.purchaseDate = purchaseDate;
    }

    public RoomData getRoom() {
        return roomData;
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
