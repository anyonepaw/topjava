<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<style type="text/css">
    a {
        font-size: 120%;
        font-family: "Courier New", Courier, "Lucida Sans Typewriter", "Lucida Typewriter", monospace;
        color: #ffffff;
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
        color: #ff46df;
    }

    h2 {
        text-align: center;
        font-size: 120%;
        font-family: "Courier New", Courier, "Lucida Sans Typewriter", "Lucida Typewriter", monospace;
        color: #ffffff;
    }

    table, th, td {
        padding: 10px;
        border: 2px solid white;
        border-collapse: collapse;
    }

    th {
        align-items: center;
        font-family: "Courier New", Courier, "Lucida Sans Typewriter", "Lucida Typewriter", monospace;
        color: #ffffff;
    }

    td {
        align-items: center;
        font-family: "Courier New", Courier, "Lucida Sans Typewriter", "Lucida Typewriter", monospace;
    }

    p[id="calories"] {
        font-size: 250%;
        font-family: "Courier New", Courier, "Lucida Sans Typewriter", "Lucida Typewriter", monospace;
        color: #ffffff;
    }

    input {
        font-size: 150%;
        font-family: "Courier New", Courier, "Lucida Sans Typewriter", "Lucida Typewriter", monospace;
        color: #000000;
    }

</style>
<body style="background-color:#000000;">
<h3><a href="index.html">Home</a></h3>
<hr>
<p align="right" style="font-size: 120%"><a href="meals?action=create">-> Add meal</a></p>
<h2>Meal List</h2>
<br/>
<br/>
<table align="center">
    <thead>
    <tr>
        <th>ID</th>
        <th>Date & Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach items="${meals}" var="meal">
        <tr style="color:${meal.isExcess() ? 'red' : 'green'}">
            <td><c:out value='${meal.getId()}'/></td>
            <td><fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${parsedDateTime}"/></td>
            <td><c:out value="${meal.getDescription()}"/></td>
            <td><c:out value="${meal.getCalories()}"/></td>
            <td><a href="meals?action=update&id=<c:out value="${meal.getId()}"/>">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.getId()}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br/>
<br/>
<br/>
<p id="calories">Calories per day:</p>
<input type="text" name="caloriesPerDay" style="text-align: center"
       value="<c:out value="${caloriesPerDay}"/>"/>
</body>
</html>
