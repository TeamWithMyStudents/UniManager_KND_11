package ua.knd11.security.impl;

import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.SQLActions;

import java.util.List;

import static ua.knd11.util.FieldValidator.makeProtectedPassword;

public class AuthServiceImpl implements AuthService {
    // TODO: Нужно привязать к дб +
    //  делать ретурн к меню если дубликат имейла происходит что бы из-за дб код не дропался

    public void registerUser(User user) {
        if (isEmailTaken(user.getEmail())) {
            System.out.println("Error: User with email " + user.getEmail() + " already exists.");
            return;
        }
        if (user instanceof Student s) {
            SQLActions.addStudentToDB(s);
        } else if (user instanceof Teacher t) {
            SQLActions.addTeacherToDB(t);
        }
    }

    public User login(String email, String value) throws IllegalArgumentException {
        FieldValidator.validateEmail(email);
        FieldValidator.validatePassword(value);

        if (UserSession.isAuthenticated()) {
            throw new IllegalArgumentException("User is already logged in");
        }
        User foundUser = findUserByEmail(email);
        String userPasswordGuess = makeProtectedPassword(value);
        if (foundUser == null || !foundUser.getPassword().equals(userPasswordGuess)) {
            throw new IllegalArgumentException("User with this email not found or incorrect password");
        }
        UserSession.login(foundUser);
        System.out.println("Login successful! \n Welcome," + foundUser.getName());
        return foundUser;
    }

    /**Method help find user in db if is already registered, at first from teachers, then students**/
    private User findUserByEmail(String email) {
//        List<Teacher> teachers = SQLActions.retrieveTeachersFromDB();
//        for (Teacher t : teachers) {
//            if (t.getEmail().equalsIgnoreCase(email)) return t;
//        }
//        List<Student> students = SQLActions.retrieveStudentsFromDB();
//        for (Student s : students) {
//            if (s.getEmail().equalsIgnoreCase(email)) return s;
//        }
//        return null;
            Teacher teacher = SQLActions.getTeacherByEmail(email);
            if (teacher != null) return teacher;
            return SQLActions.getStudentByEmail(email);
        }


    private boolean isEmailTaken(String email) {
        return findUserByEmail(email) != null;
    }

    //    public void removeUser(int id) throws IllegalArgumentException {
//        if (registeredUsers.removeIf(user -> user.getId() == id))
//            throw new IllegalArgumentException("User with this id not found");
//    }
    @Override
    public void removeUser(int id) {
        SQLActions.deleteStudentFromDBWithID(id);
        SQLActions.deleteTeacherFromDBWithID(id);
    }
}
