<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" />
<link href="/css/bootstrap.css" rel="stylesheet" type="text/css">
<html>
<head><title>Students</title></head>
<body>
<div class="container">
    <div class="page-header">
        <h1>Students</h1>
    </div>
</div>
<div class="container">
    <h3 class="pull-right"><a href="/student/add">Add new student</a></h3>
</div>
<div class="container table-responsive">
    <table class="table">
        <tr>
            <td>Last Name</td>
            <td>First Name</td>
            <td>Middle Name</td>
            <td>Home Address</td>
            <td>Date Of Birth</td>
            <td>Group</td>
            <td>Edit</td>
            <td>Delete</td>
        </tr>
        <c:forEach var="student" items="${students}" varStatus="status">
    	<tr>
            <td><c:out value="${student.lastName}" /></td>
            <td><c:out value="${student.firstName}" /></td>
            <td><c:out value="${student.middleName}" /></td>
    		<td><c:out value="${student.homeAddress }" /></td>
            <td><p><fmt:formatDate pattern="yyyy-MM-dd"
                                   value="${student.dateOfBirth}"/></p></td>
            <td><c:out value="${student.studentGroup.name}" /></td>
            <td><a href="/student/edit?userId=<c:out value="${student.id}"/>">Edit</a></td>
            <td>
                <form method="POST"  action="/student/delete?userId=<c:out value="${student.id}"/>">
                    <input type="submit" value="Delete"/>
                </form>
            </tr>
        </c:forEach>
    </table>
</div>
</body></html>