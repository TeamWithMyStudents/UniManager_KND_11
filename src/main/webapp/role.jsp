<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style>
        body { font-family: Arial, sans-serif; margin: 50px; text-align: center; }
        .btn { padding: 10px 20px; margin: 10px; font-size: 16px; cursor: pointer; }
    </style>
</head>
<body>
    <h2>Provided email has two entries</h2>
    <p>Please select the role you wish to log in as:</p>

 <form action="${pageContext.request.contextPath}/role" method="post">
        <button type="submit" name="role" value="student" class="btn">Student</button>
        <button type="submit" name="role" value="teacher" class="btn">Teacher</button>
    </form>
</body>
</html>