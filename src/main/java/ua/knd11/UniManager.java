package ua.knd11;

import ua.knd11.viewer.ConsoleMenu;

/**
 * Main entry point for the University Management System.
 */
public class UniManager {
    /**
     * Starts the application by initializing and displaying the console menu.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        menu.startMenu();
    }
}