package nl.inholland.javafx.model;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import nl.inholland.javafx.model.Person;
import nl.inholland.javafx.ui.App;
import nl.inholland.javafx.ui.MainWindow;

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

    public MenuBar getMenuBar(Person user){
        MenuItem showings = new MenuItem("Manage showings");
        MenuItem movies = new MenuItem("Manage movies");
        MenuItem about = new MenuItem("About");
        MenuItem logout1 = new MenuItem("Logout...");

        //here in Mainwindow database is passing to login page (App) database
        logout1.setOnAction(a-> App.db = MainWindow.db);

        help.getItems().addAll(about);
        admin.getItems().addAll(showings, movies);
        logout.getItems().addAll(logout1);
        menuBar.getMenus().addAll(admin,help,logout);
        if (user.getRole().equals("user")){ // remove menuitem form the menuBar on the user role
            admin.setVisible(false);
        }
        else{
            help.setVisible(false);
        }
        return menuBar;
    }
}
