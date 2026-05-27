package ua.knd11;

import ua.knd11.controller.StudentController;
import ua.knd11.controller.TeacherController;
import ua.knd11.security.AuthService;
import ua.knd11.security.impl.AuthServiceImpl;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.impl.JournalServiceImpl;
import ua.knd11.service.impl.ScheduleServiceImpl;
import ua.knd11.util.SQLActions;
import ua.knd11.viewer.ConsoleMenu;

/**
 * Main entry point for the University Management System.
 * This class is responsible for initializing core services and controllers,
 * performing dependency injection, and launching the application menu.
 */
public class UniManager {

    /**
     * Starts the application by setting up the environment and displaying the main menu.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        // 1. Initialize core business logic services
        JournalService journalService = new JournalServiceImpl();
        ScheduleService scheduleService = new ScheduleServiceImpl();
        SQLActions.initDatabase();

        // 2. Initialize controllers and inject the required services
        StudentController studentController = new StudentController(journalService, scheduleService);
        TeacherController teacherController = new TeacherController(journalService, scheduleService);
        AuthService authService = new AuthServiceImpl();

        // 3. Initialize the main console viewer and inject the controllers
        ConsoleMenu menu = new ConsoleMenu(studentController, teacherController, authService);

        // 4. Launch the application
        System.out.println("Application initialized. Welcome!");
        menu.startMenu();
    }
}