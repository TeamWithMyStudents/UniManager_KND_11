<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 50px; }
        .error { color: red; margin-bottom: 15px; }
        .form-group { margin-bottom: 10px; }
    </style>
</head>
<body>
    <h2>Login</h2>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="error"><%= request.getAttribute("errorMessage") %></div>
    <% } %>

    <form action="login" method="POST">
        <div class="form-group">
            <label>Email: </label><br/>
            <input type="text" name="email" required />
        </div>
        <div class="form-group">
            <label>Password: </label><br/>
            <input type="password" name="password" required />
        </div>
        <button type="submit">sign in</button>
    </form>
</body>
</html>