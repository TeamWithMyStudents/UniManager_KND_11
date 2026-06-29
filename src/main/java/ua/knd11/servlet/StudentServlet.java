package ua.knd11.servlet;

import ua.knd11.model.Lesson;
import ua.knd11.model.User;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.JournalServiceImpl;
import ua.knd11.service.impl.ScheduleServiceImpl;
import ua.knd11.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private final JournalService journalService = new JournalServiceImpl();
    private final ScheduleService scheduleService = new ScheduleServiceImpl();
    private final StudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int studentId = currentUser.getId();

        String action = request.getParameter("action");
        if (action == null) {
            action = "menu";
        }

        switch (action) {
            case "grades" -> {
                String recordBook = journalService.generateRecordBook(studentId);
                request.setAttribute("gradesData", recordBook.replace("\n", "<br>"));
            }
            case "schedule" -> {
                String dayInput = request.getParameter("day");
                if (dayInput != null && !dayInput.trim().isEmpty()) {
                    try {
                        DayOfWeek day = scheduleService.parseDayOfWeek(dayInput.trim().toUpperCase());
                        List<Lesson> lessons = scheduleService.getLessonsByDay(day);
                        request.setAttribute("selectedDay", day.toString());
                        request.setAttribute("lessonsList", lessons);
                    } catch (IllegalArgumentException e) {
                        request.setAttribute("scheduleError", "Invalid day entered. Please check your spelling.");
                    }
                }
            }
            case "all-students" -> {
                java.util.List<ua.knd11.model.Student> allStudents = studentService.getAllStudents();
                request.setAttribute("studentsList", allStudents);
            }
        }

        request.setAttribute("currentAction", action);
        request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);
    }
}