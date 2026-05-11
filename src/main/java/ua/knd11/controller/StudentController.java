package ua.knd11.controller;

import ua.knd11.model.Lesson;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Scanner;

/**
 * Controller class responsible for handling student-specific interactions.
 * Processes user input for viewing grades and schedules (Read-only access).
 */
public class StudentController {

    /** Service for managing academic records. */
    private final JournalService journalService;

    /** Service for managing the lesson timetable. */
    private final ScheduleService scheduleService;

    /**
     * Constructs a StudentController with required service dependencies.
     *
     * @param journalService  the service handling grades
     * @param scheduleService the service handling schedules
     */
    public StudentController(JournalService journalService, ScheduleService scheduleService) {
        this.journalService = journalService;
        this.scheduleService = scheduleService;
    }

    /**
     * Displays the student dashboard and processes menu selections.
     *
     * @param scanner   the {@link Scanner} object for reading user input
     * @param studentId the ID of the currently authenticated student
     */
    public void displayMenu(Scanner scanner, int studentId) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Student Dashboard ===");
            System.out.println("1. View My Grades (Record Book)");
            System.out.println("2. View Schedule for a Day");
            System.out.println("3. Return to Main Menu");
            System.out.print("Select action: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(journalService.generateRecordBook(studentId));
                    break;
                case "2":
                    handleViewSchedule(scanner);
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
     * Prompts the student for a day of the week and displays the corresponding schedule.
     *
     * @param scanner the {@link Scanner} object for reading user input
     */
    private void handleViewSchedule(Scanner scanner) {
        System.out.print("Enter Day of Week (e.g., MONDAY): ");
        try {
            DayOfWeek day = DayOfWeek.valueOf(scanner.nextLine().trim().toUpperCase());
            List<Lesson> lessons = scheduleService.getLessonsByDay(day);

            if (lessons.isEmpty()) {
                System.out.println("No lessons scheduled for " + day + ".");
            } else {
                System.out.println("\n--- Schedule for " + day + " ---");
                for (Lesson lesson : lessons) {
                    System.out.println(lesson.toString());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Invalid day entered.");
        }
    }
}