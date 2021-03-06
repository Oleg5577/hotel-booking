<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.locale" var="i18n"/>
<fmt:setBundle basename="property.page" var="path"/>
<jsp:useBean id="user" scope="session" class="com.pronovich.hotelbooking.entity.User"/>
<html lang="${language}">
<head>
    <link href="../../css/bootstrap.css" rel="stylesheet">
    <link href="../../css/font-awesome.css" rel="stylesheet">
    <link href="../../css/style.css" rel="stylesheet">
    <script src="../../js/jquery-3.2.1.js"></script>
    <script src="../../js/bootstrap.js"></script>
    <script src="../../js/main.js"></script>
</head>
<body>
<div id="footer" class="container">
    <div class="row">
        <div class="col-xs-offset-4 col-xs-4">
            <ul class="list-unstyled">
                <li><fmt:message key="home.label" bundle="${ i18n }"/><li>
                <li><fmt:message key="home.label.address" bundle="${ i18n }"/></li>
            </ul>
        </div>
        <div class="col-xs-4">
            <ul class="list-unstyled text-right">
                <li><li>
                <li><ctg:current-date/><li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-offset-8 col-xs-4">
            <p class="text-muted pull-right">
                <fmt:message key="home.label.rights" bundle="${ i18n }"/>
            </p>
        </div>
    </div>
</div>
</body>
</html>
