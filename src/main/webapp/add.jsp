<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" />
<link href="/css/bootstrap.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>Add student</title>
</head>
<body>
<form method="POST" action="/student/add" name="frmAddStudent">
    <div>
        <label>Last Name</label>
        <input type="text" name="lastName" value="<c:out value="${user.lastName}"/>"/>
    </div>
    <div>
        <label>First Name</label>
        <input type="text" name="firstName" value="<c:out value="${user.firstName}"/>"/>
    </div>
    <div>
        <label>Middle Name</label>
        <input type="text" name="middleName" value="<c:out value="${user.middleName}"/>"/>
    </div>
    <div>
        <label>Home address</label>
        <input type="text" name="homeAddress" value="<c:out value="${user.homeAddress}"/>"/>
    </div>
    <div>
        <label>Date Of Birth</label>
        <input type="text" name="dateOfBirth" value="<c:out value="${fmtDate}"/>"/>
    </div>
    <div>
        <label>Student Group</label>
        <select name="group">
            <c:forEach var="group" items="${groups}">
                <option value="${group.name}">
                    <c:out value="${group.name}"/>
                </option>
            </c:forEach>
        </select>
    </div>
    <div>
        <input type="submit" value="Submit"/>
    </div>

</form>
</body>
</html>
