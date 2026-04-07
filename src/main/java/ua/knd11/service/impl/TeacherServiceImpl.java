package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;
import ua.knd11.util.FieldValidator;

/**
 * The type Teacher service.
 */
public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {

    /**
     * Calculates and prints the sum of salaries for all Teacher entries in the repository.
     * <p>
     * Iterates the repository, adds each Teacher's salary to a running total, and prints
     * the result to standard output as "Total University Budget": followed by the sum.
     */
    public void calculateTotalSalary() {
        double result = repository.stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    /**
     * Prints all teachers whose degree contains the given degree string (case-insensitive).
     *
     * @param degree substring to match against teacher degrees (case-insensitive)
     * @throws IllegalArgumentException if `degree` is null, empty, or contains non-alphabetic characters
     */
    public void filterByDegree(String degree) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Degree", degree);

        boolean found = false;
        for (User user : repository) {
            if (user instanceof Teacher t) {
                if (t.getDegree().toLowerCase().contains(degree.toLowerCase())) {
                    System.out.println(t);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No teachers found with the specified degree.");
        }
    }

    /**
     * Adds a teacher to the underlying repository.
     *
     * @param user the user to add; must be a {@code Teacher} instance
     * @return true if the user was added successfully, false otherwise
     * @throws IllegalArgumentException if {@code user} is not an instance of {@code Teacher}
     */
    @Override
    public boolean add(User user) throws IllegalArgumentException {
        if (!(user instanceof Teacher teacher)) {
            throw new IllegalArgumentException("User must be an instance of Teacher");
        }
        return super.add(teacher);
    }
}