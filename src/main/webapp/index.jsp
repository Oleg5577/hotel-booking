<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<body>
    <jsp:forward page="WEB-INF/pages/signin.jsp"/>
</body>
</html>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.bundler"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Sign in</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>
<form method="post" action="controller">
    <input type="hidden" name="command" value="sign_in">
    <label for="username"><fmt:message key="login.label.username" />:</label>
    <input type="text" id="username" name="username">
    <br>
    <label for="password"><fmt:message key="login.label.password" />:</label>
    <input type="password" id="password" name="password">
    <br>
    <fmt:message key="login.button.submit" var="buttonValue" />
    <input type="submit" name="submit" value="${buttonValue}">
</form>
</body>
</html>
</html>--%>



