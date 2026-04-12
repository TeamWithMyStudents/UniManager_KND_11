package ua.knd11;

import ua.knd11.util.SQLActions;
import ua.knd11.viewer.ConsoleMenu;

public class UniManager {
    public static void main(String[] args) {
        SQLActions.initDatabase();
        ConsoleMenu menu = new ConsoleMenu();
        menu.startMenu();
    }
}