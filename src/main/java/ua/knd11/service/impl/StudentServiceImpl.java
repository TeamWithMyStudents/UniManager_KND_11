package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.model.enums.StudentRole;
import ua.knd11.service.StudentService;

import static ua.knd11.util.FieldValidators.isNullOrBlank;

/**
 * Implementation of StudentService interface.
 * Extends UserServiceImpl with student-specific functionality.
 */
public class StudentServiceImpl extends UserServiceImpl implements StudentService {
    /**
     * Searches for students by group name and displays the results.
     * Uses partial matching and is case-insensitive.
     *
     * @param groupName the group name to search for (e.g., "KND-11")
     * @throws IllegalArgumentException if the group name is null or empty
     */
    @Override
    public void findByGroup(String groupName) {
        if (isNullOrBlank(groupName, "Group")) return;

        repository.stream().filter(user -> user instanceof Student st &&
                st.getGroup().contains(groupName.toUpperCase())).forEach(System.out::println);
    }

    /**
     * Assigns a student as the Head Student of their group.
     * <p>
     * The method follows this logic:
     * <ul>
     * <li>Searches for a student by ID. If not found, displays an error.</li>
     * <li>Checks if the current Head Student is in the same group.</li>
     * <li>If another Head Student exists in the group, they are demoted to REGULAR.</li>
     * <li>If the target student is already Head Student, displays a message and returns.</li>
     * <li>Assigns the new Head Student role and displays confirmation.</li>
     * </ul>
     *
     * @param studentId the unique ID of the student to be assigned as Head Student
     */
    @Override
    public void assignHeadStudent(int studentId) {
        Student newHeadStudent = null;
        for (User u : getAll()) {
            if (u.getId() == studentId && u instanceof Student) {
                newHeadStudent = (Student) u;
                break;
            }
        }
        if (newHeadStudent == null) {
            System.out.println("Student with ID " + studentId + " not found.");
            return;
        }
        String targetGroup = newHeadStudent.getGroup();

        for (User u : getAll()) {
            if (u instanceof Student st) {
                if (st.getGroup().equals(targetGroup) && st.getRole() == StudentRole.HEAD_STUDENT &&
                        st.getId() != studentId) {
                    st.setRole(StudentRole.REGULAR);
                    System.out.println("Previous Head Student " + st.getName() + " " + st.getSurname() + " " + st.getLastname() +
                            "\nin group " + st.getGroup() + " stepped down.");
                } else if (st.getGroup().equals(targetGroup) && st.getRole() == StudentRole.HEAD_STUDENT &&
                        st.getId() == studentId) {
                    System.out.println("Student " + st.getName() + " " + st.getSurname() + " " + st.getLastname() +
                            "\nis already the Head Student of group " + st.getGroup() + ".");
                    return;
                }
            }
        }
        newHeadStudent.setRole(StudentRole.HEAD_STUDENT);
        System.out.println("Student " + newHeadStudent.getName() + " " + newHeadStudent.getSurname() + " " + newHeadStudent.getLastname() +
                "\nis now the Head Student of group " + targetGroup + "!");
    }

    /**
     * Adds a student to the repository after type validation.
     * Ensures only Student instances can be added through this service.
     *
     * @param user the user to be added (must be a Student)
     * @return true if the student was added successfully, false otherwise
     * @throws IllegalArgumentException if the user is not an instance of Student
     */
    @Override
    public boolean add(User user) {
        if (!(user instanceof Student student)) {
            throw new IllegalArgumentException("User must be an instance of Student");
        }
        return super.add(student);
    }
}


