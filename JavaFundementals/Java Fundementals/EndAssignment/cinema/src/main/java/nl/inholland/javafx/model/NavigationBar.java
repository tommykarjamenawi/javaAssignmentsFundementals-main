package nl.inholland.javafx.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.ui.App;
import nl.inholland.javafx.ui.ManageMovies;
import nl.inholland.javafx.ui.ManageShowings;

public class NavigationBar {
    private final MenuBar menuBar;
    private final Menu admin;
    private final Menu help;
    private final Menu logout;

    public NavigationBar() {
        menuBar = new MenuBar();
        admin = new Menu("Admin");
        help = new Menu("Help");
        logout = new Menu("Logout");
    }

    public MenuBar getMenuBar(Person user, Stage window, Database db){
        MenuItem showings = new MenuItem("Manage showings");
        MenuItem movies = new MenuItem("Manage movies");
        MenuItem about = new MenuItem("About");
        MenuItem logout1 = new MenuItem("Logout...");

        help.getItems().addAll(about);
        admin.getItems().addAll(showings, movies);
        logout.getItems().addAll(logout1);
        menuBar.getMenus().addAll(admin,help,logout);
        if (user.getRole().equals("user")){ // remove menuitem form the menuBar on the user role
            admin.setVisible(false);
        }
        else{
            //help.setVisible(false);
        }

        // Logout functionality while maintaining the same databse
        logout1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    new App().start(new Stage(), db);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                window.close();
            }});

        // open manage showings form
        showings.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    new ManageShowings(user, db);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                window.close();
            }});

        // open manage movies form
        movies.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    new ManageMovies(user, db);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                window.close();
            }});

        return menuBar;
    }
}
