<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ru">
<head>
    <title>Add new user</title>
    <link rel="stylesheet" href="css/meals-styles">
    <link href="<c:url value="/css/update-meal.css" />" rel="stylesheet">
</head>
<body>
<form method="POST" action="meals" style="">
    <p><input type="hidden" name="id" value="${meal.id}"/></p>
    <p>Date & Time :</p>
    <input id="datetime" type="datetime-local" name="dateTime"
           value="${meal.dateTime}"/>"required/> <br/>
    <p>Description :</p>
    <input type="text" name="description"
           value="${meal.description}"/>" required/><br/>
    <p>Calories :</p>
    <input  type="text" name="calories" required pattern="^[0-9]+$"
           value="${meal.calories}" />" required/><br/>
    <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
