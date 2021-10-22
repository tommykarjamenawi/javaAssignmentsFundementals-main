package nl.inholland.javafx.logic;

import javafx.collections.ObservableList;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.Room;

public class RoomLogic{
    Database database;

    public RoomLogic(Database database) {
        this.database = database;
    }

    // Get room
    public ObservableList<Room> getLogicRoom1(){ return database.getRoom1(); }
    public ObservableList<Room> getLogicRoom2(){ return database.getRoom2(); }

    // Add a room
    public void addLogicRoom1(Room room){ database.addRoom1(room); }
    public void addLogicRoom2(Room room){ database.addRoom2(room); }
}
