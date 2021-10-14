package nl.inholland.javafx.ui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import nl.inholland.javafx.model.*;

public class NavigationMenu {
    private final MenuBar menuBar;
    private final Menu dashboardMenu;
    private final Menu studentsMenu;
    private final Menu teachersMenu;
    private final Menu logoutMenu;

    public NavigationMenu() {
        menuBar = new MenuBar();
        dashboardMenu = new Menu("Dashboard");
        studentsMenu = new Menu("Students");
        teachersMenu = new Menu("Teachers");
        logoutMenu = new Menu("Log Out");
    }

    public MenuBar getMenu(Stage window, Person user) {
        onAction(dashboardMenu);
        onAction(studentsMenu);
        onAction(teachersMenu);
        onAction(logoutMenu);

        dashboardMenu.setOnAction(actionEvent -> {
            new Dashboard(user);
            window.close();
        });

        studentsMenu.setOnAction(actionEvent -> {
            new Students(user);
            window.close();
        });

        teachersMenu.setOnAction(actionEvent -> {
            new Teachers(user);
            window.close();
        });

        logoutMenu.setOnAction(actionEvent -> {
            new App().start(new Stage());
            window.close();
        });

        menuBar.getMenus().addAll(dashboardMenu, studentsMenu, teachersMenu, logoutMenu);

        return menuBar;
    }

    private void onAction(Menu menu) {
        final MenuItem menuItem = new MenuItem();

        menu.getItems().add(menuItem);
        menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
        menu.addEventHandler(Menu.ON_SHOWING, event -> menu.fire());
    }
}
