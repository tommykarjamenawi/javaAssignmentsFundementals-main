package nl.inholland.javafx.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExitConfirmation {
    public void closePopup(Stage window){

        Stage popupwindow = new Stage();
        HBox hbox = new HBox(5);
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(5);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("INFO");

        Label lblText= new Label("Close the window?");

        Button btnOk= new Button("OK");
        Button btnCancel =  new Button("Cancel");

        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                popupwindow.close();
                window.close();
            }});

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                window.show();
                popupwindow.close();
            }});

        pane.add(lblText, 1, 0);
        hbox.getChildren().addAll(btnOk, btnCancel);
        pane.add(hbox, 1, 1);//; pane.add(btnCancel, 2, 1);

        Scene scene= new Scene(pane, 300, 250);

        popupwindow.setScene(scene);

        popupwindow.showAndWait();
    }
}

