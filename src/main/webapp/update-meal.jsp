<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <link type="text/css"
          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Add new user</title>
</head>

<style>
    body, form {
        background-color: #000000;
    }
    p {
        font-size: 120%;
        font-family: "Courier New", Courier, "Lucida Sans Typewriter", "Lucida Typewriter", monospace;
        color: #ffffff;
        text-decoration: none;
    }
    input {
        font-size: 120%;
        font-family: "Courier New", Courier, "Lucida Sans Typewriter", "Lucida Typewriter", monospace;
        color: #000000;
    }
    input[type=submit] {
        font-family: "Courier New", Courier, "Lucida Sans Typewriter", "Lucida Typewriter", monospace;
        background-color: #2c16af;
        border: none;
        color: white;
        padding: 16px 32px;
        text-decoration: none;
        margin: 4px 2px;
        cursor: pointer;
    }
</style>

<body>

<form method="POST" action="meals" style="">
    <p><input type="hidden" name="id" value="<c:out value="${id}" />"/></p>
    <p>Date & Time :</p>
    <input id="datetime" type="datetime-local" name="dateTime" required/> <br/>
    <p>Description :</p>
    <input type="text" name="description"
           value="<c:out value="${description}"/>" required/><br/>
    <p>Calories :</p>
    <input  type="text" name="calories" required pattern="^[0-9]+$"
           value="<c:out value="${calories}" />" required/><br/>
    
    <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
