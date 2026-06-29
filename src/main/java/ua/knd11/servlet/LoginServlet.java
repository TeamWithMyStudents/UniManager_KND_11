package ua.knd11.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.knd11.model.User;
import ua.knd11.security.UserSession;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.SQLActions;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("currentUser") != null) {
            response.sendRedirect("menu.jsp");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            FieldValidator.validateEmail(email);
            FieldValidator.validatePassword(password);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Incorrect email or password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        User superUser = UserSession.getSuperUser();
        if (superUser.getEmail().equalsIgnoreCase(email)) {
            if (FieldValidator.verifyPassword(password, superUser.getPassword(), superUser.getSalt())) {
                request.getSession().setAttribute("currentUser", superUser);
                response.sendRedirect("menu.jsp");
                return;
            }
        }

        User student = SQLActions.getStudentByEmail(email);
        User teacher = SQLActions.getTeacherByEmail(email);
        User targetedUser = null;

        if (student != null && teacher != null) {
            HttpSession session = request.getSession();
            session.setAttribute("tempStudent", student);
            session.setAttribute("tempTeacher", teacher);
            session.setAttribute("tempPassword", password);
            response.sendRedirect("role.jsp");
            return;
        }

        if (student != null) {
            targetedUser = student;
        } else if (teacher != null) {
            targetedUser = teacher;
        }

        if (targetedUser != null && FieldValidator.verifyPassword(password, targetedUser.getPassword(), targetedUser.getSalt())) {
            request.getSession().setAttribute("currentUser", targetedUser);
            response.sendRedirect("menu.jsp");
            return;
        }

        request.setAttribute("errorMessage", "Incorrect email or password");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}