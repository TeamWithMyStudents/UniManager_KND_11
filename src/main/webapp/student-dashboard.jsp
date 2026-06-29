<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.knd11.model.Lesson" %>
<html>
<head>
    <title>Student Management Menu </title>
    <style>
        .menu-list { list-style-type: none; padding: 0; }
        .menu-list li { margin: 10px 0; }
        .content-box { margin-top: 25px; padding: 15px; border: 1px solid #ccc; background-color: #f9f9f9; }
        .error { color: red; }
    </style>
</head>
<body>

    <h2> Student Management Menu </h2>

    <ul class="menu-list">
        <li><a href="${pageContext.request.contextPath}/student?action=grades">1. View My Grades (Record Book)</a></li>
        <li><a href="${pageContext.request.contextPath}/student?action=schedule">2. View Schedule for a Specific Day</a></li>
        <li><a href="${pageContext.request.contextPath}/student?action=all-students">3. View All Registered Students</a></li>
        <li><a href="${pageContext.request.contextPath}/menu.jsp">4. Back to Main Menu</a></li>
    </ul>

    <hr>

    <div class="content-box">


        <% if ("grades".equals(request.getAttribute("currentAction"))) { %>
            <h3> My Record Book </h3>
            <p>${gradesData}</p>
        <% } %>


        <% if ("schedule".equals(request.getAttribute("currentAction"))) { %>
            <h3> View Schedule </h3>
            <form action="${pageContext.request.contextPath}/student" method="GET">
                <input type="hidden" name="action" value="schedule">
                <label for="day">Enter Day of Week (e.g., MONDAY): </label>
                <input type="text" id="day" name="day" placeholder="MONDAY" required>
                <button type="submit">Show Schedule</button>
            </form>


            <% if (request.getAttribute("scheduleError") != null) { %>
                <p class="error">${scheduleError}</p>
            <% } %>

            <% if (request.getAttribute("selectedDay") != null) { %>
                <h4>Schedule for ${selectedDay}:</h4>
                <%
                    List<Lesson> lessons = (List<Lesson>) request.getAttribute("lessonsList");
                    if (lessons == null || lessons.isEmpty()) {
                %>
                    <p>No lessons scheduled for this day.</p>
                <% } else { %>
                    <ul>
                        <% for (Lesson lesson : lessons) { %>
                            <li><%= lesson.toString() %></li>
                        <% } %>
                    </ul>
                <% } %>
            <% } %>
        <% } %>
<% if ("all-students".equals(request.getAttribute("currentAction"))) { %>
    <h3> Registered Students </h3>
    <ul>
        <%
            java.util.List<?> students = (java.util.List<?>) request.getAttribute("studentsList");
            if (students != null && !students.isEmpty()) {
                for (Object student : students) {
        %>
            <li><%= student.toString() %></li>
        <%
                }
            } else {
        %>
            <p>No registered students found.</p>
        <% } %>
    </ul>
<% } %>

    </div>

</body>
</html>