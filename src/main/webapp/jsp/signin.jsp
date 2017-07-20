<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Sign in</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <jsp:include page="section/header.jsp"/>
    <a href="signup.jsp">Go to sign up</a>
    <br>
    <a href="signin.jsp">Go to sign in</a>
    <br>
    <form method="post" action="controller">
        <input type="hidden" name="command" value="sign_in">
        <label for="email"><fmt:message key="login.label.email"/>:</label>
        <input type="text" id="email" name="email">
        <br>
        <label for="password"><fmt:message key="login.label.password"/>:</label>
        <input type="password" id="password" name="password">
        <br>
        <fmt:message key="login.button.submit" var="buttonValue"/>
        <input type="submit" name="submit" value="${buttonValue}">
    </form>
</body>
</html>



