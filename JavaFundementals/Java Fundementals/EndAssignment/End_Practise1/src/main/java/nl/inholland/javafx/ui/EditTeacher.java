package nl.inholland.javafx.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.inholland.javafx.logic.PersonService;
import nl.inholland.javafx.model.Person;
import nl.inholland.javafx.model.Teacher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EditTeacher {
    private final Stage window;

    public EditTeacher(Person user, Teacher teacher) {
        window = new Stage();
        PersonService personService = new PersonService();

        // Set Window properties
        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Edit teacher");

        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create components
        NavigationMenu menu = new NavigationMenu();

        Label title = new Label("Edit Teacher");
        Button editButton = new Button("Edit Teacher");
        Button cancelButton = new Button("Cancel");

        TextField idInput = new TextField();
        TextField userInput = new TextField();
        TextField firstNameInput = new TextField();
        TextField lastNameInput = new TextField();
        TextField salaryInput = new TextField();
        DatePicker dateInput = new DatePicker();

        // Add attributes
        content.setPadding(new Insets(10));
        title.setFont(new Font(30));
        dateInput.setPrefWidth(210);

        firstNameInput.setPromptText("First name");
        lastNameInput.setPromptText("Last name");
        salaryInput.setPromptText("Salary");
        dateInput.setPromptText("Birth date (yyyy-mm-dd)");

        idInput.setDisable(true);
        userInput.setDisable(true);

        idInput.setText("Student id: " + teacher.id);
        userInput.setText("Username: " + teacher.username);
        firstNameInput.setText(teacher.firstName);
        lastNameInput.setText(teacher.lastName);
        salaryInput.setText(String.valueOf(teacher.salary));
        dateInput.setValue(teacher.birthdate);

        LocalDate minDate = LocalDate.now().minusYears(100);
        LocalDate maxDate = LocalDate.now().minusYears(10);
        dateInput.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    LocalDate date = LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    if (date.isAfter(minDate) && date.isBefore(maxDate)) {
                        return date;
                    }
                }
                return null;
            }
        });
        dateInput.setDayCellFactory(d ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }
                });

        // When button is clicked
        editButton.setOnAction(actionEvent -> {
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            double salary = Double.parseDouble(salaryInput.getText());
            LocalDate birthDate = dateInput.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || salaryInput.getText().isEmpty() || birthDate == null) {
                new Alert(Alert.AlertType.INFORMATION, "Please fill in the whole form.").show();
            } else {
                List<Teacher> teachers = personService.getTeachers();
                Teacher newTeacher = teacher;
                for (Teacher t : teachers) {
                    if (t.id == teacher.id) {
                        newTeacher = t;
                    }
                }
                newTeacher.firstName = firstName;
                newTeacher.lastName = lastName;
                newTeacher.salary = salary;
                newTeacher.birthdate = birthDate;

                if (personService.saveTeachers(teachers)) {
                    new Alert(Alert.AlertType.INFORMATION, firstName + " was successfully edited.").showAndWait();
                    new Students(user);
                    window.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went wrong while trying to edit " + firstName + ".");
                }
            }
        });

        cancelButton.setOnAction(actionEvent -> {
            new Students(user);
            window.close();
        });

        // Add components to their container
        buttons.getChildren().addAll(editButton, cancelButton);

        grid.add(idInput, 0, 0);
        grid.add(dateInput, 1, 0);
        grid.add(userInput, 0, 1);
        grid.add(firstNameInput, 0, 2);
        grid.add(lastNameInput, 0, 3);
        grid.add(salaryInput, 0, 4);
        grid.add(buttons, 0, 5);

        content.getChildren().addAll(title, grid);
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

