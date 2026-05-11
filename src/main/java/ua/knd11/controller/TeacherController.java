package ua.knd11.controller;

import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.impl.TeacherServiceImpl;
import ua.knd11.model.Teacher;
import ua.knd11.model.User;

import java.util.List;
import java.util.Scanner;

/**
 * Controller class responsible for handling teacher-related operations.
 * It acts as an intermediary between the user interface and the business logic layers
 * for managing teacher profiles, academic schedules, and student grades.
 */
public class TeacherController {

    private final TeacherServiceImpl teacherProfileService = new TeacherServiceImpl();
    private final JournalService journalService;
    private final ScheduleService scheduleService;

    /**
     * Constructs a TeacherController with the required academic services.
     *
     * @param journalService  the service handling student grades
     * @param scheduleService the service handling lesson schedules
     */
    public TeacherController(JournalService journalService, ScheduleService scheduleService) {
        this.journalService = journalService;
        this.scheduleService = scheduleService;
    }

    /**
     * Displays the teacher-specific menu and handles user interactions.
     *
     * @param scanner the scanner used for console input
     */
    public void displayMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Teacher Management Menu ---");
            System.out.println("1. Assign Grade to Student");
            System.out.println("2. Add Lesson to Schedule");
            System.out.println("3. View All Teachers");
            System.out.println("4. Calculate Total Salaries");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> handleAssignGrade(scanner);
                case "2" -> handleAddLesson(scanner);
                case "3" -> getAll();
                case "4" -> calculateTotalSalary();
                case "5" -> running = false;
                default -> System.out.println("[WARNING] Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Internal helper to collect input and assign a grade via JournalService.
     */
    private void handleAssignGrade(Scanner scanner) {
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Subject: ");
            String subject = scanner.nextLine();
            System.out.print("Enter Score: ");
            int score = Integer.parseInt(scanner.nextLine());

            journalService.assignGrade(id, subject, score);
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Student ID and Score must be numeric.");
        }
    }

    /**
     * Internal helper to collect input and add a lesson via ScheduleService.
     */
    private void handleAddLesson(Scanner scanner) {
        System.out.print("Enter Day (e.g., MONDAY): ");
        String day = scanner.nextLine();
        System.out.print("Enter Time (e.g., 10:30): ");
        String time = scanner.nextLine();
        System.out.print("Enter Subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter Teacher Surname: ");
        String surname = scanner.nextLine();

        scheduleService.addLesson(day, time, subject, surname);
    }

    /**
     * Retrieves and prints a list of all teachers currently stored in the repository.
     */
    public void getAll() {
        List<User> teachers = teacherProfileService.getAll();
        boolean found = false;
        for (User user : teachers) {
            if (user instanceof Teacher teacher) {
                System.out.println(teacher);
                found = true;
            }
        }
        if (!found) {
            System.err.println("No teachers found in the repository.\n");
        }
    }

    /**
     * Calculates and displays the total salary of all teachers.
     */
    public void calculateTotalSalary() {
        // Просто викликаємо метод сервісу, бо він сам друкує результат у консоль
        teacherProfileService.calculateTotalSalary();
    }

    /**
     * Creates and adds a new teacher profile to the system.
     * * @param input raw string containing teacher details separated by spaces
     */
    public void create(String input) {
        String normalized = input.trim().replace(",", " ");
        String[] parts = normalized.split("\\s+");

        if (parts.length != 7) {
            System.out.println("Error: Expected 7 fields (Name Surname Dept Degree Salary Email Password).");
            return;
        }

        try {
            double salary = Double.parseDouble(parts[4]);
            Teacher teacher = new Teacher(parts[0], parts[1], parts[2], parts[3], salary, parts[5], parts[6]);
            if (teacherProfileService.add(teacher)) {
                System.out.println("Teacher added successfully!");
            } else {
                System.out.println("Teacher wasn't added.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}