<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.locale" var="i18n"/>
<fmt:setBundle basename="property.page" var="path"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="home.label" bundle="${ i18n }"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="shortcut icon" href="../img/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div id="carousel" class="carousel slide center-block" data-ride="carousel">
    <ol class="carousel-indicators">
        <li class="active" data-target="#carousel" data-slide-to="0"></li>
        <li data-target="#carousel" data-slide-to="1"></li>
        <li data-target="#carousel" data-slide-to="2"></li>
        <li data-target="#carousel" data-slide-to="3"></li>
    </ol>
    <div class="carousel-inner">
        <div class="item active">
            <img src="../img/room/1.1%20standard.jpg" alt="">
            <div class="carousel-caption">
                <h3><fmt:message key="room-type.label.standard" bundle="${ i18n }"/></h3>
                <a class="btn btn-info" href="/controller?command=find_rooms_description">
                    <fmt:message key="our-rooms.label" bundle="${ i18n }"/>
                </a>
            </div>
        </div>
        <div class="item">
            <img src="../img/room/2.1%20semilux.jpg" alt="">
            <div class="carousel-caption">
                <h3><fmt:message key="room-type.label.semilux" bundle="${ i18n }"/></h3>
                <a class="btn btn-info" href="/controller?command=find_rooms_description">
                    <fmt:message key="our-rooms.label" bundle="${ i18n }"/>
                </a>
            </div>
        </div>
        <div class="item">
            <img src="../img/room/3.1%20lux.jpg" alt="">
            <div class="carousel-caption">
                <h3><fmt:message key="room-type.label.lux" bundle="${ i18n }"/></h3>
                <a class="btn btn-info" href="/controller?command=find_rooms_description">
                    <fmt:message key="our-rooms.label" bundle="${ i18n }"/>
                </a>
            </div>
        </div>
        <div class="item">
            <img src="../img/room/4.2%20president.jpg" alt="">
            <div class="carousel-caption">
                <h3><fmt:message key="room-type.label.president" bundle="${ i18n }"/></h3>
                <a class="btn btn-info" href="/controller?command=find_rooms_description">
                    <fmt:message key="our-rooms.label" bundle="${ i18n }"/>
                </a>
            </div>
        </div>
    </div>


    <a href="#carousel" class="left carousel-control" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left"></span>
    </a>
    <a href="#carousel" class="right carousel-control" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right"></span>
    </a>
</div>
<jsp:include page="/jsp/section/footer.jsp"/>
</body>
</html>



