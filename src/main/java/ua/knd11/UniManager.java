package ua.knd11;

import ua.knd11.controller.StudentController;
import ua.knd11.controller.TeacherController;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.impl.JournalServiceImpl;
import ua.knd11.service.impl.ScheduleServiceImpl;
import ua.knd11.viewer.ConsoleMenu;

/**
 * Main entry point for the UniManager application.
 * This class is responsible for initializing the application context,
 * instantiating core services, injecting dependencies into controllers,
 * and starting the user interface.
 */
public class UniManager {

    /**
     * The main method that bootstraps the application.
     *
     * @param args command-line arguments (not utilized in this application)
     */
    public static void main(String[] args) {

        // 1. Initialize core business logic services
        JournalService journalService = new JournalServiceImpl();
        ScheduleService scheduleService = new ScheduleServiceImpl();

        // 2. Initialize controllers and inject service dependencies
        StudentController studentController = new StudentController(journalService, scheduleService);
        TeacherController teacherController = new TeacherController(journalService, scheduleService);

        // 3. Initialize the visual console menu and inject controller dependencies
        ConsoleMenu menu = new ConsoleMenu(studentController, teacherController);

        // 4. Start the interactive menu loop
        menu.startMenu();
    }
}