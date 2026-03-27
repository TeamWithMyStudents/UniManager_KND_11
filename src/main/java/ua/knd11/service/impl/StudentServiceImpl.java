package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.model.enums.StudentRole;
import ua.knd11.service.StudentService;

public class StudentServiceImpl extends UserServiceImpl implements StudentService {
    public StudentServiceImpl() {
        super();
    }

    @Override
    public void findByGroup(String groupQuery) {
        if (isNullOrBlank(groupQuery, "Group")) return;

        repository.stream().filter(user -> user instanceof Student st &&
                st.getGroup().contains(groupQuery.toUpperCase())).forEach(System.out::println);
    }

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
        if (isNullOrBlank(student.getName(), "Name") ||
                isNullOrBlank(student.getSurname(), "Surname") ||
                isNullOrBlank(student.getLastname(), "Lastname") ||
                isNullOrBlank(student.getGroup(), "Group")) {
            return false;
        } else return super.add(student);
    }
}


