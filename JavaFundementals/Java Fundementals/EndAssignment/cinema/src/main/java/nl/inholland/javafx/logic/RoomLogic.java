package nl.inholland.javafx.logic;

import javafx.collections.ObservableList;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.RoomData;

public class RoomLogic{
    Database database;

    public RoomLogic(Database database) {
        this.database = database;
    }

    // Get room
    public ObservableList<RoomData> getLogicRoom1(){ return database.getRoom1(); }
    public ObservableList<RoomData> getLogicRoom2(){ return database.getRoom2(); }

    // Add a room
    public void addLogicRoom1(RoomData roomData){ database.addRoom1(roomData); }
    public void addLogicRoom2(RoomData roomData){ database.addRoom2(roomData); }
}
