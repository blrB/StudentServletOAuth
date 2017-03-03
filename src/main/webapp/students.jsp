<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Students</title>
    <!-- Materialize -->
    <link rel="stylesheet" href="/css/materialize.min.css">
    <!-- Materialize fonts -->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Main css -->
    <link rel="stylesheet" href="/css/style.css">

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
<div class="navbar-fixed">
    <nav class="light-blue darken-4" role="navigation">
        <div class="nav-wrapper container">
            <a id="logo-container" class="brand-logo">Laboratory work №5-6</a>
        </div>
    </nav>
</div>
<div class="container">
    <div class="section">
        <div class="row" align="right">
            <a class="waves-effect waves-light btn" href="/student/add">Add new student</a>
        </div>
        <table class="row bordered striped responsive-table">
            <thead>
            <tr>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Middle Name</th>
                <th>Home Address</th>
                <th>Date Of Birth</th>
                <th>Group</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${students}" varStatus="status">
                <tr itemtype="http://schema.org/Person">
                    <td itemprop="familyName"><c:out value="${student.lastName}"/></td>
                    <td itemprop="givenName"><c:out value="${student.firstName}"/></td>
                    <td itemprop="alternateName"><c:out value="${student.middleName}"/></td>
                    <td itemprop="address"><c:out value="${student.homeAddress }"/></td>
                    <td itemprop="birthDate"><fmt:formatDate pattern="yyyy-MM-dd" value="${student.dateOfBirth}"/></td>
                    <td><c:out value="${student.studentGroup.name}"/></td>
                    <td>
                        <a href="/student/edit?userId=<c:out value="${student.id}"/>"
                           class="waves-effect waves-light btn">
                            Edit
                        </a>
                    </td>
                    <td>
                        <form class="student-table-input" method="POST" action="/student/delete?userId=<c:out value="${student.id}"/>">
                            <input type="submit" class="btn btn-default" value="Delete"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="/js/jquery.min.js"></script>
<!-- Materialize -->
<script src="/js/materialize.min.js"></script>
</body>
</html>