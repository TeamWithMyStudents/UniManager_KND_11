package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.model.enums.StudentRole;
import ua.knd11.service.StudentService;
import ua.knd11.util.FieldValidator;

/**
 * The type Student service.
 */
public class StudentServiceImpl extends UserServiceImpl implements StudentService {
    /**
     * Prints students whose group contains the given group name (case-insensitive, partial match).
     * <p>
     * Validates `groupName` before searching; each matching `Student` is printed to standard output.
     *
     * @param groupName the group substring to match (e.g., "KND-11")
     * @throws IllegalArgumentException if `groupName` is invalid
     */
    @Override
    public void findByGroup(String groupName) throws IllegalArgumentException {
        FieldValidator.validateGroup(groupName);
        repository.stream().filter(user -> user instanceof Student st &&
                st.getGroup().contains(groupName.toUpperCase())).forEach(System.out::println);
    }

    /**
     * Assigns the specified student as the head student for their group.
     * <p>
     * If another student in the same group currently holds the head role, that student is demoted to `StudentRole.REGULAR`.
     * If the student with the given ID is not found or is not a `Student`, the method prints a message and returns without changes.
     * If the student is already the head of their group, the method prints a message and returns without changes.
     * On successful assignment, the student's role is set to `StudentRole.HEAD_STUDENT` and a confirmation is printed.
     *
     * @param studentId the unique ID of the student to assign as head student
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
     * Adds the given user to the underlying repository as a Student.
     *
     * @param user the user to add; must be an instance of {@code Student}
     * @return {@code true} if the student was added, {@code false} otherwise
     * @throws IllegalArgumentException if {@code user} is not an instance of {@code Student}
     */
    @Override
    public boolean add(User user) {
        if (!(user instanceof Student student)) {
            throw new IllegalArgumentException("User must be an instance of Student");
        }
        return super.add(student);
    }
}


