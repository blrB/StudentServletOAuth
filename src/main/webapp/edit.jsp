<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Editor</title>
    <!-- Materialize -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
    <!-- Materialize fonts -->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Main css -->
    <link rel="stylesheet" href="css/style.css">

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
        <div class="row">
            <div class="col s12 m6">
                <form method="POST" action="<c:out value="${action}"/>" name="frmAddStudent"
                      role="form" class="form-horizontal">
                    <input type="hidden" name="id" value="<c:out value="${student.id}"/>"/>
                    <div class="input-field">
                        <input placeholder="Enter last name..." type="text" id="lastName"
                               value="<c:out value="${student.lastName}"/>"/>
                        <label for="lastName">Last Name</label>
                    </div>
                    <div class="input-field">
                        <input placeholder="Enter first name..." type="text" id="firstName"
                               value="<c:out value="${student.firstName}"/>"/>
                        <label for="firstName">First Name</label>
                    </div>
                    <div class="input-field">
                        <input placeholder="Enter middle name..." type="text" id="middleName"
                               value="<c:out value="${student.middleName}"/>"/>
                        <label for="middleName">Middle Name</label>
                    </div>
                    <div class="input-field">
                        <input placeholder="Enter home address..." type="text" id="homeAddress"
                               value="<c:out value="${student.homeAddress}"/>"/>
                        <label for="homeAddress">Home address</label>
                    </div>
                    <div class="input-field">
                        <label for="dateOfBirth">Date Of Birth</label>
                        <%--<input placeholder="Enter birthday..." type="text" id="dateOfBirth" value="<fmt:formatDate pattern="yyyy-MM-dd"--%>
                                   <%--value="${student.dateOfBirth}"/>"/>--%>
                        <input type="date" class="datepicker"  id="dateOfBirth" value="<fmt:formatDate pattern="yyyy-MM-dd"
                                   value="${student.dateOfBirth}"/>">
                    </div>
                    <div class="input-field">
                        <select id="group">
                            <c:forEach var="group" items="${groups}">
                                <option value="${group.name}">
                                    <c:out value="${group.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                        <label for="group">Group</label>
                    </div>
                    <div>
                        <button class="waves-effect waves-light btn" type="submit">
                            Submit
                        </button>
                        <a href="/students" class="waves-effect waves-light btn" type="button">
                            Return to list of students
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- Materialize -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script>
<script>
    $(document).ready(function() {
        $('select').material_select();
    });
    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 30, // Creates a dropdown of 15 years to control year
        format: 'yyyy-mm-dd',
        formatSubmit: 'yyyy-mm-dd'
    });
</script>
</body>
</html>
