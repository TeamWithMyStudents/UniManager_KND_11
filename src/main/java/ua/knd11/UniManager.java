package ua.knd11;

import ua.knd11.viewer.ConsoleMenu;

/**
 * Core application class
 * Handles the main menu and application flow
 */
public class UniManager {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        menu.startMenu();
    }
}