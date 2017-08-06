<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler" var="i18n"/>
<fmt:setBundle basename="property.page" var="path"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Sign in</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="container">
    <div class="jumbotron">
        <h1><i class="fa fa-frown-o red"></i>404 Page Not Found</h1>
        <p class="lead">We couldn't find what you're looking for on <em><span id="display-domain"></span></em>.</p>
        <p>
            <a href="<fmt:message key="path.page.home" bundle="${ path }"/>" class="btn btn-default btn-lg">
                <span class="green">To Home page</span>
            </a>
        </p>
    </div>
</div>
</body>
</html>



