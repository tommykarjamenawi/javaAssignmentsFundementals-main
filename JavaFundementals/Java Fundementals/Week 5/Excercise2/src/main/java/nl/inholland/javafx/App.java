package nl.inholland.javafx;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class App extends Application {
    @Override
    public void start(Stage window) throws Exception {
        Database db = new Database();
        ObservableList<Person> people = FXCollections.observableArrayList(db.getPeople());

        Stage stage = new Stage();
        stage.setTitle("People Manager");
        stage.setMinWidth(250);
        VBox layout = new VBox();
        layout.setPadding(new Insets(10));

        TableView<Person> tableView = new TableView<>();
        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Person, String> birthDateColumn = new TableColumn<>("Birth date");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, birthDateColumn);

        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("First name");
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("Last name");
        DatePicker birthdateInput = new DatePicker();
        birthdateInput.setPromptText("Birth date");
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");

        addButton.getStyleClass().add("customButton");
        deleteButton.setId("specialButton");
        tableView.setItems(people);

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // create a new person with the data from the input fields
                Person newPerson = new Person(firstNameInput.getText(), lastNameInput.getText(), birthdateInput.getValue());

                // add the person to the list
                people.add(newPerson);
                //tableView.getItems().clear();
                tableView.setItems(people);
                // clear the input fields. When done, you might try the delete button?
                firstNameInput.clear();
                lastNameInput.clear();
                birthdateInput.setValue(null);
                tableView.setItems(people);
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<Person> selectedPeople =
                        tableView.getSelectionModel().getSelectedItems();
                people.removeAll(selectedPeople);
            }
        });



        HBox form = new HBox();
        String test = "";
        Label serialized = new Label(test);
        form.getChildren().add(serialized);
        for (Person p : db.getPeople()){
            FileOutputStream fileout = new FileOutputStream("PersonInfo.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(p);
            out.close();
        }
        form.setPadding(new Insets(10));
        form.setSpacing(10);
        form.getChildren().addAll(firstNameInput, lastNameInput, birthdateInput, addButton, deleteButton);

        layout.getChildren().add(tableView);
        layout.getChildren().add(form);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.show();
    }
}

