<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler"/>
<fmt:setBundle basename="property.page" var="path"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header>
    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
            <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
        </select>
    </form>
    <form method="post" action="controller">
        <input type="hidden" name="command" value="sign_out">
        <fmt:message key="signout.button.submit" var="buttonValue"/>
        <input type="submit" name="submit" value="${buttonValue}">
    </form>
    <br>
    <a href="<fmt:message key="path.page.add-room-request" bundle="${ path }"/>">Add room request</a>
    <br>
    <a href="<fmt:message key="path.page.signup" bundle="${ path }"/>">Go to sign up</a>
    <br>
    <a href="<fmt:message key="path.page.signin" bundle="${ path }"/>">Go to sign in</a>
    <br>
    <a href="<fmt:message key="path.page.personal-account" bundle="${ path }"/>">Personal-account</a>
    <br>
</header>
</body>
</html>
