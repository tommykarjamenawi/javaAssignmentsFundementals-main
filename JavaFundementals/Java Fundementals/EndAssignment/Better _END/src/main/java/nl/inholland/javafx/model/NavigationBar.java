package nl.inholland.javafx.model;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import nl.inholland.javafx.model.Person;

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
        //MenuItem logout1 = new MenuItem("Logout...");
        /*if (user.getRole().equals("admin")){ // admin menubar
            MenuItem showings = new MenuItem("Manage showings");
            MenuItem movies = new MenuItem("Manage movies");

            admin.getItems().addAll(showings, movies);
            logout.getItems().addAll(logout1);
            menuBar.getMenus().addAll(admin,help,logout);
        }
        else{ // user menubar
            // Create menubar items when clicked
            MenuItem about = new MenuItem("About");
            //add items to the menubar
            help.getItems().addAll(about);
            logout.getItems().addAll(logout1);
            menuBar.getMenus().addAll(help,logout);
        }*/
        MenuItem showings = new MenuItem("Manage showings");
        MenuItem movies = new MenuItem("Manage movies");
        MenuItem about = new MenuItem("About");
        MenuItem logout1 = new MenuItem("Logout...");

        help.getItems().addAll(about);
        admin.getItems().addAll(showings, movies);
        logout.getItems().addAll(logout1);
        menuBar.getMenus().addAll(admin,help,logout);
        if (user.getRole().equals("user")){
            admin.setVisible(false);
        }
        else{
            help.setVisible(false);
        }

        return menuBar;
    }
}
