package nl.inholland.javafx.model;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.inholland.javafx.ui.MainWindow;

public class RoomDetails {

    //pass values that we have to show by selecting the row
    public static void vbox(String rm, String title, String st, String ed, int s) {
        //and make a vbox and add data
        VBox vBox = new VBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();

        Label room = new Label("Room");
        Label roomTxt = new Label(rm);
        Label movieTitle = new Label("Movie Title");
        Label movieTxt = new Label(title);
        Label start = new Label("Start");
        Label startTxt = new Label(st);
        Label end = new Label("End");
        Label endTxt = new Label(ed);
        Label seats = new Label("No. of seats");
        ChoiceBox<Integer> seatsBox = new ChoiceBox<>();
        Label name = new Label("Name");
        TextField nameTxt = new TextField();
        Button purchase = new Button("Purchase");
        Button clear = new Button("Clear");

        for (int i = 0; i < s; i++){
            seatsBox.getItems().add(i+1);
        }

        room.setPrefSize(70, 20);
        start.setPrefSize(70, 20);
        end.setPrefSize(70, 20);
        roomTxt.setPrefSize(150, 20);
        startTxt.setPrefSize(150, 20);
        endTxt.setPrefSize(150, 20);
        movieTitle.setPrefSize(150, 20);
        seats.setPrefSize(150, 20);
        name.setPrefSize(150, 20);
        movieTxt.setPrefSize(150, 20);
        seatsBox.setPrefSize(150, 20);
        nameTxt.setPrefSize(150, 20);


        hBox1.getChildren().addAll(room, roomTxt, movieTitle, movieTxt);
        hBox2.getChildren().addAll(start, startTxt, seats, seatsBox, purchase);
        hBox3.getChildren().addAll(end, endTxt, name, nameTxt, clear);
        hBox1.setSpacing(10);
        hBox2.setSpacing(10);
        hBox3.setSpacing(10);

        vBox.getChildren().addAll(hBox1, hBox2, hBox3);
        vBox.setSpacing(10);

        MainWindow.container.setBottom(vBox);

    }
}
