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
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="sign_up">
        <label for="email"><fmt:message key="signup.label.email"/>:<span class="asterisk"> *</span></label>
        <input type="text" id="email" name="email" value="${requestValues.email}">
        <label class="wrong-values">${wrongValues.email}</label>
        <br>
        <label for="password"><fmt:message key="signup.label.password"/>:<span class="asterisk"> *</span></label>
        <input type="password" id="password" name="password">
        <label class="wrong-values">${wrongValues.password}</label>
        <br>
        <label for="repeat-password"><fmt:message key="signup.label.repeat-password"/>:<span class="asterisk"> *</span></label>
        <input type="password" id="repeat-password" name="repeatPassword">
        <label class="wrong-values">${wrongValues.repeatPassword}</label>
        <br>
        <label for="name"><fmt:message key="signup.label.name"/>:<span class="asterisk"> *</span></label>
        <input type="text" id="name" name="name" value="${requestValues.name}">
        <label class="wrong-values">${wrongValues.name}</label>
        <br>
        <label for="surname"><fmt:message key="signup.label.surname"/>:<span class="asterisk"> *</span></label>
        <input type="text" id="surname" name="surname" value="${requestValues.surname}">
        <label class="wrong-values">${wrongValues.surname}</label>
        <br>
        <label for="phoneNumber"><fmt:message key="signup.label.phone-number"/>:<span class="asterisk"> *</span></label>
        <input type="text" id="phoneNumber" name="phoneNumber" value="${requestValues.phoneNumber}">
        <label class="wrong-values">${wrongValues.phoneNumber}</label>
        <br>
        <fmt:message key="signup.button.submit" var="buttonValue"/>
        <input type="submit" name="submit" value="${buttonValue}">
    </form>
</body>
</html>