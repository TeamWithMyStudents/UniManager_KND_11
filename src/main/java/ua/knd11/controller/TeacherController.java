package ua.knd11.controller;

import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;

import java.util.Scanner;

/**
 * Controller class responsible for handling teacher-specific interactions.
 * Processes user input for assigning grades and managing the schedule.
 */
public class TeacherController {

    /** Service for managing academic records. */
    private final JournalService journalService;

    /** Service for managing the lesson timetable. */
    private final ScheduleService scheduleService;

    /**
     * Constructs a TeacherController with required service dependencies.
     *
     * @param journalService  the service handling grades
     * @param scheduleService the service handling schedules
     */
    public TeacherController(JournalService journalService, ScheduleService scheduleService) {
        this.journalService = journalService;
        this.scheduleService = scheduleService;
    }

    /**
     * Displays the teacher control panel and processes menu selections.
     *
     * @param scanner the {@link Scanner} object for reading user input
     */
    public void displayMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Teacher Control Panel ===");
            System.out.println("1. Assign Grade to Student");
            System.out.println("2. Add Lesson to Schedule");
            System.out.println("3. Return to Main Menu");
            System.out.print("Select action: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    handleAssignGrade(scanner);
                    break;
                case "2":
                    handleAddLesson(scanner);
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("[WARNING] Invalid option. Try again.");
            }
        }
    }

    /**
     * Prompts the teacher for grade details and passes them to the JournalService.
     *
     * @param scanner the {@link Scanner} object for reading user input
     */
    private void handleAssignGrade(Scanner scanner) {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Subject: ");
            String subject = scanner.nextLine();
            System.out.print("Enter Score (0-100): ");
            int score = Integer.parseInt(scanner.nextLine());

            journalService.assignGrade(studentId, subject, score);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Input must be a valid number.");
        }
    }

    /**
     * Prompts the teacher for lesson details and passes them to the ScheduleService.
     *
     * @param scanner the {@link Scanner} object for reading user input
     */
    private void handleAddLesson(Scanner scanner) {
        System.out.print("Enter Day of Week (e.g., MONDAY): ");
        String day = scanner.nextLine();
        System.out.print("Enter Time (e.g., 08:30): ");
        String time = scanner.nextLine();
        System.out.print("Enter Subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter Teacher Surname: ");
        String surname = scanner.nextLine();

        scheduleService.addLesson(day, time, subject, surname);
    }
}