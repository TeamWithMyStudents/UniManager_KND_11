package ua.knd11.servlet;

import ua.knd11.model.User;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.StudentService;
import ua.knd11.service.TeacherService;
import ua.knd11.service.impl.JournalServiceImpl;
import ua.knd11.service.impl.ScheduleServiceImpl;
import ua.knd11.service.impl.StudentServiceImpl;
import ua.knd11.service.impl.TeacherServiceImpl;
import ua.knd11.util.SQLActions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/teacher")
public class TeacherServlet extends HttpServlet {

    private final TeacherService teacherService = new TeacherServiceImpl();
    private final JournalService journalService = new JournalServiceImpl();
    private final ScheduleService scheduleService = new ScheduleServiceImpl();
    private final StudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (currentUser == null || currentUser.getAccessLevel() < 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You can't do that. Access denied.");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "menu";
        }

        switch (action) {
            case "view-teachers" -> {
                Object teachersData = SQLActions.retrieveTeachersFromDB();
                String formattedTeachers = String.valueOf(teachersData).replace("\n", "<br>");
                request.setAttribute("teachersList", formattedTeachers);
            }
            case "calculate-salaries" -> {
                String salaryResult = teacherService.calculateTotalSalary();
                request.setAttribute("salaryMessage", salaryResult);
            }
        }

        request.setAttribute("currentAction", action);
        request.getRequestDispatcher("/teacher-dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("assign-grade".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("studentId"));
                String subject = request.getParameter("subject");
                int score = Integer.parseInt(request.getParameter("score"));

                boolean studentExists = studentService.getAllStudents().stream()
                        .anyMatch((ua.knd11.model.Student student) -> student.getId() == id);

                if (!studentExists) {
                    request.getSession().setAttribute("errorMessage", "Student with ID " + id + " was not found");
                    response.sendRedirect(request.getContextPath() + "/teacher?action=grade-form");
                    return;
                }

                journalService.assignGrade(id, subject, score);
                request.getSession().setAttribute("successMessage", "Grade successfully assigned to Student ID: " + id);

            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMessage", "Student ID and Score must be numeric!");
            }

            response.sendRedirect(request.getContextPath() + "/teacher?action=grade-form");
            return;
        }

        if ("add-lesson".equals(action)) {
            String day = request.getParameter("day");
            String time = request.getParameter("time");
            String subject = request.getParameter("subject");
            String surname = request.getParameter("surname");

            boolean teacherExists = teacherService.getAllTeachers().stream()
                    .anyMatch((ua.knd11.model.Teacher teacher) -> teacher.getSurname().equals(surname));

            if (!teacherExists) {
                request.getSession().setAttribute("errorMessage", "Teacher with surname " + surname + " was not found");
                response.sendRedirect(request.getContextPath() + "/teacher?action=grade-form");
                return;
            }


            scheduleService.addLesson(day, time, subject, surname);
            request.getSession().setAttribute("successMessage", "Lesson successfully added to schedule!");

            response.sendRedirect(request.getContextPath() + "/teacher?action=lesson-form");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/teacher");
    }
}