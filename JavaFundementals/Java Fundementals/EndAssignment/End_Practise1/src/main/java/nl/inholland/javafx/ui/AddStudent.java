package nl.inholland.javafx.ui;

import javafx.collections.FXCollections;
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
import nl.inholland.javafx.model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddStudent {
    private final Stage window;

    public AddStudent(Person user) {
        window = new Stage();
        PersonService personService = new PersonService();

        // Set Window properties
        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Add Student");

        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create components
        NavigationMenu menu = new NavigationMenu();
        Label title = new Label("Add Student");
        Button addButton = new Button("Add Student");
        Button cancelButton = new Button("Cancel");

        TextField userInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        TextField firstNameInput = new TextField();
        TextField lastNameInput = new TextField();
        ComboBox<String> groupInput = new ComboBox<>(FXCollections.observableList(personService.getGroups()));
        DatePicker dateInput = new DatePicker();

        // Add attributes
        content.setPadding(new Insets(10));
        title.setFont(new Font(30));
        groupInput.setPrefWidth(200);
        dateInput.setPrefWidth(210);

        userInput.setPromptText("Username");
        passwordInput.setPromptText("Password");
        firstNameInput.setPromptText("First name");
        lastNameInput.setPromptText("Last name");
        groupInput.setPromptText("Group");
        dateInput.setPromptText("Birth date (yyyy-mm-dd)");

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
        addButton.setOnAction(actionEvent -> {
            String username = userInput.getText();
            String password = passwordInput.getText();
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            String group = groupInput.getValue();
            LocalDate birthDate = dateInput.getValue();

            if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || group.isEmpty() || birthDate == null) {
                new Alert(Alert.AlertType.INFORMATION, "Please fill in the whole form.").show();
            } else {
                List<Student> students = personService.getStudents();
                students.add(new Student(firstName, lastName, birthDate, group, username, password));
                if (personService.saveStudents(students)) {
                    new Alert(Alert.AlertType.INFORMATION, firstName + " was successfully added.").showAndWait();
                    new Students(user);
                    window.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went wrong while trying to add " + firstName + ".");
                }
            }
        });

        cancelButton.setOnAction(actionEvent -> {
            new Students(user);
            window.close();
        });

        // Add components to their container
        buttons.getChildren().addAll(addButton, cancelButton);

        grid.add(userInput, 0, 0);
        grid.add(dateInput, 1, 0);
        grid.add(passwordInput, 0, 1);
        grid.add(firstNameInput, 0, 2);
        grid.add(lastNameInput, 0, 3);
        grid.add(groupInput, 0, 4);
        grid.add(buttons, 0, 5);

        content.getChildren().addAll(title, grid);
        container.setTop(menu.getMenu(window, user));
        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        scene.getStylesheets().add("src/main/java/nl/inholland/javafx/files/css/style.css");
        window.setScene(scene);

        // Show window
        window.show();
    }
}
