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
    <title>Sign in</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <jsp:include page="/jsp/section/header.jsp"/>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="sign_in">
        <label for="email"><fmt:message key="login.label.email"/>:<span class="asterisk"> *</span></label>
        <input type="text" id="email" name="email" value="${correctValues.email}">
        <label class="wrong-values">${wrongValues.email}</label>
        <br>
        <label for="password"><fmt:message key="login.label.password"/>:<span class="asterisk"> *</span></label>
        <input type="password" id="password" name="password">
        <label class="wrong-values">${wrongValues.password}</label>
        <br>
        <fmt:message key="login.button.submit" var="buttonValue"/>
        <input type="submit" name="submit" value="${buttonValue}">
        <br>
        <label class="wrong-values">${wrongValues.emailOrPassword}</label>
    </form>
</body>
</html>



