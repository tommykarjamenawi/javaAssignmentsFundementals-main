package nl.inholland.javafx;

import javafx.application.Application;
import java.text.DecimalFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage window) throws Exception {
        window.setHeight(250);
        window.setWidth(500);
        window.setTitle("Week5 Assignment1 Currency converter");

        //Create a layout to be used, in this case a gridpane
        GridPane myGrid = new GridPane();
        myGrid.setPadding(new Insets(10,10,10,10));
        myGrid.setVgap(10);
        myGrid.setHgap(8);

        //the amount euro label and textfield
        Label amountLabel = new Label("Amount €:");
        myGrid.add(amountLabel,0,0);
        TextField amount = new TextField();
        amount.setPromptText("amount");
        myGrid.add(amount,1,0);

        //The Converted Label
        Label convertedAmount = new Label("Amount $:");
        myGrid.add(convertedAmount,0,3);

        // convert button
        Button convertButton = new Button("Convert Euro to Dollar");
        convertButton.getStyleClass().add("customButton");
        convertButton.setId("specialButton");

        // add button to grid with display options
        convertButton.setVisible(true);
        myGrid.add(convertButton,1,2);



        //let the button do something
        convertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int euro = Integer.parseInt(amount.getText());
                double dollar = euro * 1.18;
                // Format double with 2 numbers after comma
                DecimalFormat df = new DecimalFormat("0.00");
                // Make label to display converted money
                Label convertedAmountText = new Label();
                convertedAmountText.setText("");
                convertedAmountText.setText(df.format(dollar));
                myGrid.add(convertedAmountText,1,3);
            }
        });

        // Set grid background color
        myGrid.styleProperty().set("-fx-background-color: #0099FF");

        //add the grid to the scene, the scene to the stage, and show it
        Scene scene = new Scene(myGrid);

        // Add stylesheet
        scene.getStylesheets().add("style.css");

        window.setScene(scene);
        window.show();
    }
}
