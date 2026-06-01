package ua.knd11.controller;

import ua.knd11.model.Lesson;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;
import ua.knd11.util.SQLActions;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Scanner;

/**
 * Controller class responsible for handling student-specific operations.
 * It provides an interface for students to view their academic performance,
 * check lesson schedules, and manage student profile data.
 */
public class StudentController {

    private final StudentService studentService = new StudentServiceImpl();
    private final JournalService journalService;
    private final ScheduleService scheduleService;

    /**
     * Constructs a StudentController with the required academic services.
     *
     * @param journalService  the service handling student grades
     * @param scheduleService the service handling lesson schedules
     */
    public StudentController(JournalService journalService, ScheduleService scheduleService) {
        this.journalService = journalService;
        this.scheduleService = scheduleService;
    }

    /**
     * Displays the student dashboard menu and handles user interactions.
     *
     * @param scanner   the scanner used for console input
     * @param studentId the ID of the currently logged-in student
     */
    public void displayMenu(Scanner scanner, int studentId) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Student Dashboard ---");
            System.out.println("1. View My Grades (Record Book)");
            System.out.println("2. View Schedule for a Specific Day");
            System.out.println("3. View All Registered Students");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> System.out.println(journalService.generateRecordBook(studentId));
                case "2" -> ViewScheduleOnWeekDay(scanner);
                case "3" -> System.out.println(studentService.getAllStudents());
                case "4" -> running = false;
                default -> System.out.println("[WARNING] Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Internal helper to prompt for a day and display the corresponding schedule.
     */
    private void ViewScheduleOnWeekDay(Scanner scanner) { // todo: format output
        System.out.print("Enter Day of Week (e.g., MONDAY): ");
        String input = scanner.nextLine().trim().toUpperCase();

        try {
            // We use a helper similar to the one in ScheduleService to ensure consistent parsing
            DayOfWeek day = scheduleService.parseDayOfWeek(input);
            List<Lesson> lessons = scheduleService.getLessonsByDay(day);

            if (lessons.isEmpty()) {
                System.out.println("No lessons scheduled for " + day);
            } else {
                System.out.println("\n--- Schedule for " + day + " ---");
                lessons.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid day entered. Please check your spelling.");
        }
    }

    /**
     * Parses a raw string input from the terminal to add a new student.
     * The input is expected to contain exactly 5 space-separated fields:
     * Name, Surname, Group, Email, and Password.
     *
     * @param value the raw string containing student data from the terminal
     */
    public void addStudentFromTerminal(String value) {
        String[] parts = value.trim().split("\\s+");
        if (parts.length != 5) {
            System.out.println("Error: Expected 5 fields (Name Surname Group Email@example.com Password).");
            return;
        }
        try {
            SQLActions.addStudentToDB(studentService.createStudentWithParts(parts));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}