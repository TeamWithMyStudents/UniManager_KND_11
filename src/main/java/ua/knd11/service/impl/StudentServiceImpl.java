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
     * The method searches for students by group name and outputs in the console.
     * The search is performed by partial matching and is case-insensitive.
     *
     * @param groupName is the string to search for the group (e.g., "KND-11").
     *                  If a string is empty or null, the method terminates.
     */
    @Override
    public void findByGroup(String groupName) {
        FieldValidator.validateGroup(groupName);

        repository.stream().filter(user -> user instanceof Student st &&
                st.getGroup().contains(groupName.toUpperCase())).forEach(System.out::println);
    }

    /**
     * The method assigns a student by ID as the Head Student of their group.
     * Logic:
     * <ul>
     * <li>Searches for a student by ID. If not found, returns an error.</li>
     * <li>Checks if the current Head Student is in the found student's group.</li>
     * <li>If another Head Student exists in the group, they are removed from their position (converted to REGULAR).</li>
     * <li>If the target student is already the Head Student, returns a message and terminates.</li>
     * <li>Assigns a new Head Student and outputs in the console.</li>
     * </ul>
     *
     * @param studentId is the unique ID of the student to be assigned as the Head Student.
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

    @Override
    public boolean add(User user) {
        if (!(user instanceof Student student)) {
            throw new IllegalArgumentException("User must be an instance of Student");
        }
        return super.add(student);
    }
}


