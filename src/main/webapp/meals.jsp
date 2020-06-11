<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ru">
<head>
    <title>Meals</title>
    <link href="<c:url value="/css/meals.css" />" rel="stylesheet">
</head>
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
        <th>Date & Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br/>
<br/>
<br/>
</body>
</html>
