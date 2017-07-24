<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler"/>
<fmt:setBundle basename="property.page" var="path"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Sign up</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <jsp:include page="/jsp/section/header.jsp"/>
    <form action="<fmt:message key="path.page.signin" bundle="${ path }"/>">
        <input type="submit" value="Go to sign in">
    </form>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="sign_up">
        <label for="email"><fmt:message key="signup.label.email"/>:</label>
        <input type="text" id="email" name="email">
        <br>
        <label for="password"><fmt:message key="signup.label.password"/>:</label>
        <input type="password" id="password" name="password">
        <br>
        <label for="repeat-password"><fmt:message key="signup.label.repeat-password"/>:</label>
        <input type="password" id="repeat-password" name="repeat-password">
        <br>
        <label for="name"><fmt:message key="signup.label.name"/>:</label>
        <input type="text" id="name" name="name">
        <br>
        <label for="surname"><fmt:message key="signup.label.surname"/>:</label>
        <input type="text" id="surname" name="surname">
        <br>
        <label for="phone-number"><fmt:message key="signup.label.phone-number"/>:</label>
        <input type="text" id="phone-number" name="phone-number">
        <br>
        <fmt:message key="signup.button.submit" var="buttonValue"/>
        <input type="submit" name="submit" value="${buttonValue}">
    </form>
</body>
</html>