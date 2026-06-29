<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ua.knd11.model.User" %>
<%

    User currentUser = (User) session.getAttribute("currentUser");

    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<html>
<head>
    <title>University Management System</title>
</head>
<body>
    <h2> UNIVERSITY MANAGEMENT SYSTEM </h2>
    <p>You have logged in as a <%= currentUser.getEmail() %></p>

    <ul>
        <li><a href="${pageContext.request.contextPath}/student">1. STUDENT SECTION </a></li>

        <% if (currentUser.getAccessLevel() >= 1) { %>
            <li><a href="${pageContext.request.contextPath}/teacher">2. TEACHER SECTION </a></li>
        <% } %>

        <% if (currentUser.getAccessLevel() >= 2) { %>
            <li><a href="${pageContext.request.contextPath}/admin" style="color: red; font-weight: bold;">4. ADMIN SECTION (User Management) </a></li>
        <% } %>

        <li><a href="${pageContext.request.contextPath}/logout">3. EXIT (Log out)</a></li>
    </ul>
</body>
</html>