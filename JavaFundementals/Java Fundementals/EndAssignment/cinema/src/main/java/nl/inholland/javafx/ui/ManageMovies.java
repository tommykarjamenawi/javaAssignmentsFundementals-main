package nl.inholland.javafx.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.logic.MovieLogic;
import nl.inholland.javafx.model.*;

public class ManageMovies {
    private Database dataBase;
    private MovieLogic movieLogic;
    // Layout of the form
    private GridPane topPane;
    private GridPane centerPane;
    private GridPane bottomPane;
    private GridPane addingPane;
    private HBox errorHBox;
    // BottomPane content
    private Label lblErrorMessage;
    private Label lblMovieHeader;
    private TextField txtMovieTitle;
    private Label lblDurationHeader;
    private TextField txtMovieDuration;
    private Button btnAddMovie;
    private Label lblPriceHeader;
    private TextField txtMoviePrice;
    private Button btnClearField;


    public ManageMovies(Person user, Database db){
        dataBase = db;
        movieLogic = new MovieLogic(dataBase);
        Stage window = new Stage();
        window.setTitle("Fantastic Cinema -- -- manage movies -- username: " + user.getUserName());
        lblErrorMessage = new Label("");

        // Make container
        BorderPane container = new BorderPane();
        initializeContent(); // initialize the Layout

        // call class NavigationBar top get the correct menuBar
        NavigationBar navigationBar = new NavigationBar();
        MenuBar menuBar = navigationBar.getMenuBar(user, window, dataBase);
        Label lblManageShowings = new Label("Manage movies");

        topPane.add(menuBar, 1, 0);
        topPane.add(lblManageShowings, 1, 1);

        // text above the tableview
        Label lblRoom1 = new Label("Movies");

        // Make tableview
        MovieTable movieTable = new MovieTable(dataBase);
        TableView movieTableView = movieTable.getTableViewRoom();
        movieTableView.setMinWidth(400);

        // add content to GridPanes
        centerPane.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID, null , null)));
        centerPane.add(lblRoom1, 1, 0); centerPane.add(movieTableView, 1, 1);

        setDefaultAddingInfo();
        addComponentsAddingField();
        bottomPane.add(addingPane, 1, 0);
        bottomPane.add(errorHBox, 1, 1);

        // Tableview click event
        movieTableView.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                addingPane.setVisible(true);
                addComponentsAddingField();
            }
        });

        // Add movie in the tableview/database
        btnAddMovie.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if (!txtMovieTitle.getText().isEmpty() && !txtMovieDuration.getText().isEmpty() && !txtMoviePrice.getText().isEmpty()){
                    int duration = Integer.parseInt(txtMovieDuration.getText());
                    double price = Double.parseDouble(txtMoviePrice.getText());
                    movieLogic.addMovie(new Movie(txtMovieTitle.getText(), duration, price));
                    lblErrorMessage.setText("");
                    movieTableView.refresh();
                }
                else{
                    lblErrorMessage.setText("fill all fields!");
                }
            }
        });

        //Clear the adding field upon click of the button
        btnClearField.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                removeComponentsAddingPane();
                lblErrorMessage.setText("");
                addingPane.setVisible(false);
            }
        });

        // add content to container
        container.setTop(topPane);
        container.setCenter(centerPane);
        container.setBottom(bottomPane);

        Scene scene = new Scene(container);
        // Jmetro for styling
        //scene.getStylesheets().add("style.css"); // apply css styling
        JMetro jMetro = new JMetro(Style.DARK);
        container.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        jMetro.setScene(scene);
        window.setScene(scene);

        // Show window
        window.show();
    }

    // Will initialize the layout of the form
    public void initializeContent(){
        // Make wrappers to put inside the container
        topPane = new GridPane();
        centerPane = new GridPane();
        centerPane.setHgap(10);
        centerPane.setVgap(5);
        bottomPane = new GridPane();
        addingPane = new GridPane();;
        addingPane.setHgap(40);
        addingPane.setVgap(10);
        errorHBox = new HBox();
        addingPane.setVisible(false); // Disable add Movie field on startup
        errorHBox.getChildren().add(lblErrorMessage);
    }

    // Default Add Showings field values
    public void setDefaultAddingInfo(){
        lblErrorMessage.setText("");
        lblMovieHeader = new Label("Movie title:");
        txtMovieTitle = new TextField();
        txtMovieTitle.setPromptText("Movie Title");

        lblDurationHeader = new Label("Duration:");
        txtMovieDuration = new TextField();
        txtMovieDuration.setPromptText("Movie Duration");
        btnAddMovie = new Button("Add movie");

        lblPriceHeader = new Label("Price:");
        txtMoviePrice = new TextField();
        txtMoviePrice.setPromptText("Movie Price");
        btnClearField = new Button("Clear");
    }

    // add components to the GridPane
    public void addComponentsAddingField(){
        addingPane.add(lblMovieHeader, 1,0); addingPane.add(txtMovieTitle, 2,0);
        addingPane.add(lblDurationHeader, 1,1); addingPane.add(txtMovieDuration, 2,1); addingPane.add(btnAddMovie, 3,1);
        addingPane.add(lblPriceHeader, 1,2); addingPane.add(txtMoviePrice, 2,2); addingPane.add(btnClearField, 3,2);
    }

    // remove components from the GridPane
    public void removeComponentsAddingPane(){
        addingPane.getChildren().removeAll(lblMovieHeader, txtMovieTitle,
                lblDurationHeader, txtMovieDuration, btnAddMovie,
                lblPriceHeader, txtMoviePrice, btnClearField);
    }
}
