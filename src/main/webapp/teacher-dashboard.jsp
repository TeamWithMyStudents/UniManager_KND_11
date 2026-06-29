<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Teacher Management Menu</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .menu-bar { background-color: #f4f4f4; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
        .menu-bar a { margin-right: 15px; text-decoration: none; color: #333; font-weight: bold; }
        .menu-bar a:hover { color: #007BFF; }
        .container { border: 1px solid #ccc; padding: 20px; border-radius: 5px; background-color: #fafafa; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input, .form-group select { padding: 8px; width: 300px; border: 1px solid #ccc; border-radius: 4px; }
        .btn { padding: 10px 15px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .btn:hover { background-color: #218838; }
        .alert-success { color: green; font-weight: bold; margin-bottom: 15px; }
        .alert-error { color: red; font-weight: bold; margin-bottom: 15px; }
    </style>
</head>
<body>

    <h2> Teacher Management Menu </h2>

    <div class="menu-bar">
        <a href="${pageContext.request.contextPath}/teacher?action=grade-form">1. Assign Grade</a>
        <a href="${pageContext.request.contextPath}/teacher?action=lesson-form">2. Add Lesson</a>
        <a href="${pageContext.request.contextPath}/teacher?action=view-teachers">3. View All Teachers</a>
        <a href="${pageContext.request.contextPath}/teacher?action=calculate-salaries">4. Calculate Salaries</a>
        <a href="${pageContext.request.contextPath}/menu.jsp" style="color: #dc3545;">5. Back to Main Menu</a>
    </div>

    <%
        String success = (String) session.getAttribute("successMessage");
        String error = (String) session.getAttribute("errorMessage");
        if (success != null) { %>
            <div class="alert-success"><%= success %></div>
        <% session.removeAttribute("successMessage"); }
        if (error != null) { %>
            <div class="alert-error"><%= error %></div>
        <% session.removeAttribute("errorMessage"); }
    %>

    <div class="container">
        <% if ("menu".equals(request.getAttribute("currentAction"))) { %>
        <% } %>

        <% if ("grade-form".equals(request.getAttribute("currentAction"))) { %>
            <h3>Assign Grade to Student</h3>
            <form action="${pageContext.request.contextPath}/teacher?action=assign-grade" method="POST">
                <div class="form-group">
                    <label>Enter Student ID:</label>
                    <input type="number" name="studentId" required placeholder="e.g. 12">
                </div>
                <div class="form-group">
                    <label>Enter Subject:</label>
                    <input type="text" name="subject" required placeholder="e.g. Java Advanced">
                </div>
                <div class="form-group">
                    <label>Enter Score:</label>
                    <input type="number" name="score" required min="0" max="100" placeholder="0-100">
                </div>
                <button type="submit" class="btn">Submit Grade</button>
            </form>
        <% } %>

        <% if ("lesson-form".equals(request.getAttribute("currentAction"))) { %>
            <h3>Add Lesson to Schedule</h3>
            <form action="${pageContext.request.contextPath}/teacher?action=add-lesson" method="POST">
                <div class="form-group">
                    <label>Select Day:</label>
                    <select name="day">
                        <option value="MONDAY">Monday</option>
                        <option value="TUESDAY">Tuesday</option>
                        <option value="WEDNESDAY">Wednesday</option>
                        <option value="THURSDAY">Thursday</option>
                        <option value="FRIDAY">Friday</option>
                        <option value="SATURDAY">Saturday</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Enter Time:</label>
                    <input type="text" name="time" required placeholder="e.g. 10:30">
                </div>
                <div class="form-group">
                    <label>Enter Subject:</label>
                    <input type="text" name="subject" required placeholder="e.g. Discrete Math">
                </div>
                <div class="form-group">
                    <label>Enter Teacher Surname:</label>
                    <input type="text" name="surname" required placeholder="e.g. Smith">
                </div>
                <button type="submit" class="btn" style="background-color: #007BFF;">Add Lesson</button>
            </form>
        <% } %>

        <% if ("view-teachers".equals(request.getAttribute("currentAction"))) { %>
            <h3>Registered Teachers List</h3>
            <div style="background-color: #fff; padding: 15px; border: 1px dashed #999; font-family: monospace;">
                <%= request.getAttribute("teachersList") %>
            </div>
        <% } %>

<% if ("calculate-salaries".equals(request.getAttribute("currentAction"))) { %>
<h3>Salary Calculation Status</h3>
    <pre style="font-family: monospace; font-size: 16px; margin: 10px 0;"><%= request.getAttribute("salaryMessage") %></pre>
<% } %>

    </div>

</body>
</html>