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
     * A method that calculates teachers' salaries using the Stream API
     * It determines the number of teachers from a list and returns their salaries, which are then summed up.
     *
     * @throws IllegalArgumentException if the user is not an instance of Teacher
     * @throws IllegalStateException    if the repository is empty
     *
     */
    public void calculateTotalSalary() {
        double result = repository.stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    /**
     * A method that filters by teacher degree.
     * If the desired degree is not found, the method terminates.
     * Otherwise, a variable is created that stores a boolean value.
     * All users are then iterated through, with only teachers selected.
     * These users are compared with the desired degree, displayed, and true is set to the variable.
     * If not found, a message is displayed stating, "No teachers with this degree were found."
     *
     * @param degree the degree to search for.
     * @throws IllegalArgumentException if the degree is null or empty.
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
     * The method that adds a teacher.
     * Add method from the user class is called.
     *
     * @return true if the user was added successfully, false otherwise
     * @throws IllegalArgumentException if the user is not an instance of Teacher
     */
    @Override
    public boolean add(User user) throws IllegalArgumentException {
        if (!(user instanceof Teacher teacher)) {
            throw new IllegalArgumentException("User must be an instance of Teacher");
        }
        return super.add(teacher);
    }
}