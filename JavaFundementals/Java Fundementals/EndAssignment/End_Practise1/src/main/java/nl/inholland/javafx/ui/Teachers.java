package nl.inholland.javafx.ui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.inholland.javafx.logic.PersonService;
import nl.inholland.javafx.model.*;

import java.time.LocalDate;

public class Teachers {
    private final Stage window;

    public Teachers(Person user) {
        window = new Stage();
        PersonService personService = new PersonService();
        ObservableList<Teacher> teachers = FXCollections.observableList(personService.getTeachers());

        // Set Window properties
        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Teachers");

        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);

        // Create components
        NavigationMenu menu = new NavigationMenu();

        Label title = new Label("Teachers");
        TableView<Teacher> tableView = new TableView<>();

        TableColumn<Teacher, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(features -> new SimpleIntegerProperty(features.getValue().id).asObject());

        TableColumn<Teacher, String> firstNameColumn = new TableColumn<>("First name");
        firstNameColumn.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().firstName));

        TableColumn<Teacher, String> lastNameColumn = new TableColumn<>("Last name");
        lastNameColumn.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().lastName));

        TableColumn<Teacher, LocalDate> birthdateColumn = new TableColumn<>("Birth date");
        birthdateColumn.setCellValueFactory(features -> new SimpleObjectProperty<>(features.getValue().birthdate));

        TableColumn<Teacher, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(features -> new SimpleIntegerProperty(features.getValue().getAge()).asObject());

        TableColumn<Teacher, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(features -> new SimpleObjectProperty<>(features.getValue().salary));

        Button addButton = new Button("Add Teacher");
        Button editButton = new Button("Edit Teacher");
        Button deleteButton = new Button("Delete Teacher");

        // Add attributes
        tableView.setPlaceholder(new Label("No rows to display"));
        content.setPadding(new Insets(10));
        title.setFont(new Font(30));

        // When button is clicked
        addButton.setOnAction(actionEvent -> {
            new AddTeacher(user);
            window.close();
        });

        editButton.setOnAction(actionEvent -> {
            if (!tableView.getSelectionModel().isEmpty()) {
                new EditTeacher(user, teachers.get(teachers.indexOf(tableView.getSelectionModel().getSelectedItem())));
                window.close();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Please select a teacher to edit.").show();
            }
        });

        deleteButton.setOnAction(actionEvent -> {
            if (!tableView.getSelectionModel().isEmpty()) {
                Teacher teacher = tableView.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + teacher.firstName + " " + teacher.lastName + "?", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    teachers.remove(teacher);
                    personService.saveTeachers(teachers);
                } else {
                    tableView.getSelectionModel().clearSelection();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Please select a teacher to delete.").show();
            }
        });

        // Add components to its container
        if (user.levelOfAccess == LevelOfAccess.Admin) {
            buttons.getChildren().addAll(addButton, editButton, deleteButton);
        }

        //noinspection unchecked
        tableView.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, birthdateColumn, ageColumn);

        if (user.levelOfAccess == LevelOfAccess.Admin) {
            tableView.getColumns().add(salaryColumn);
        }

        tableView.setItems(teachers);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        content.getChildren().addAll(title, tableView, buttons);
        container.setTop(menu.getMenu(window, user));
        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        scene.getStylesheets().add("resources/css/style.css");
        window.setScene(scene);

        // Show window
        window.show();
    }
}
