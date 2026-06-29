package ua.knd11.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.knd11.model.User;
import ua.knd11.util.FieldValidator;

import java.io.IOException;

@WebServlet("/role")
public class RoleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User student = (User) session.getAttribute("tempStudent");
        User teacher = (User) session.getAttribute("tempTeacher");
        String password = (String) session.getAttribute("tempPassword");

        String chosenRole = request.getParameter("role");
        User selectedUser = null;

        if ("student".equals(chosenRole)) {
            selectedUser = student;
        } else if ("teacher".equals(chosenRole)) {
            selectedUser = teacher;
        }

        if (selectedUser != null && password != null &&
                FieldValidator.verifyPassword(password, selectedUser.getPassword(), selectedUser.getSalt())) {

            session.setAttribute("currentUser", selectedUser);

            session.removeAttribute("tempStudent");
            session.removeAttribute("tempTeacher");
            session.removeAttribute("tempPassword");

            response.sendRedirect("menu.jsp");
        } else {
            session.invalidate();
            request.setAttribute("errorMessage", "Incorrect email or password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}