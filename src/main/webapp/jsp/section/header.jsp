<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler" var="i18n"/>
<fmt:setBundle basename="property.page" var="path"/>
<jsp:useBean id="user" scope="session" class="com.pronovich.hotelbooking.entity.User"/>
<html lang="${language}">
<head>
    <title>Title</title>
    <link href="../../css/bootstrap.css" rel="stylesheet">
    <link href="../../css/font-awesome.css" rel="stylesheet">
    <link href="../../css/style.css" rel="stylesheet">
    <script src="../../js/jquery-1.12.4.min.js"></script>
    <script src="../../js/bootstrap.js"></script>
    <script src="../../js/main.js"></script>
    <%--<script src="../../js/jquery.popconfirm.js" type="text/javascript"></script>--%>
</head>
<body>
<div class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<fmt:message key="path.page.home" bundle="${ path }"/>">
                <img alt="Brand"  src="../../img/hotel-logo.gif" height="35px">
            </a>
        </div>
        <div class="collapse navbar-collapse" id="responsive-menu">
            <ul class="nav navbar-nav navbar-left">
                <li>
                    <a href="<fmt:message key="path.page.home" bundle="${ path }"/>">
                        <fmt:message key="navbar.link.home" bundle="${ i18n }"/>
                    </a>
                </li>
                <li>
                    <a href="/controller?command=find_rooms_description">
                        <fmt:message key="our-rooms.label" bundle="${ i18n }"/>
                    </a>
                </li>
                <c:choose>
                    <c:when test="${user.role == 'ADMIN'}">
                        <li>
                            <a href="/controller?command=find_info_for_admin_account">
                                <fmt:message key="admin.account.label" bundle="${ i18n }"/>
                            </a>
                        </li>
                    </c:when>
                    <c:when test="${user.role == 'CLIENT'}">
                        <li>
                            <a href="<fmt:message key="path.page.client.add-room-request" bundle="${ path }"/>">
                                <fmt:message key="add-room-request.label.search-room" bundle="${ i18n }"/>
                            </a>
                        </li>
                        <li>
                            <a href="/controller?command=find_info_for_client_account"/>
                                <fmt:message key="client.account.label" bundle="${ i18n }"/>
                            </a>
                        </li>
                    </c:when>
                </c:choose>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${user.id == null}">
                    <li>
                        <div class="btn-group btn-group-sm">
                            <button class="btn btn-success navbar-btn" data-toggle="modal" data-target="#modal-1">
                                <fmt:message key="signin.label" bundle="${ i18n }"/>
                            </button>
                            <button class="btn btn-success navbar-btn" data-toggle="modal" data-target="#modal-2">
                                <fmt:message key="login.label.signup" bundle="${ i18n }"/>
                            </button>
                        </div>
                    </li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                <c:if test="${user.id != null}">
                    <form method="post" action="/controller" class="navbar-form">
                        <input type="hidden" name="command" value="sign_out">
                        <fmt:message key="signout.button.submit" bundle="${ i18n }" var="buttonValue"/>
                        <input type="submit" name="submit" value="${buttonValue}" class="btn btn-primary">
                    </form>
                </c:if>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="navbar-form" action="/controller" method="get" id="language" name="language" onchange="submit()">
                        <input type="hidden" name="command" value="change_locale">
                        <div>
                            <select class="form-control" name="language">
                                <option value="ru_RU" ${language == 'ru' ? 'selected' : ''}>RU</option>
                                <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
                            </select>
                        </div>
                    </form>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                <c:choose>
                    <c:when test="${user.role == 'ADMIN'}">
                        <a href="/controller?command=find_info_for_admin_account">
                            <ctg:user-name user="${ user }"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="/controller?command=find_info_for_client_account">
                            <ctg:user-name user="${ user }"/>
                        </a>
                    </c:otherwise>
                </c:choose>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="text-right date-tag">
    <ctg:current-date/>
</div>
<div class="modal fade" id="modal-1">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="signin.label" bundle="${ i18n }"/></h4>
            </div>
            <div class="modal-body">
                <form role="form" method="post" action="/controller">
                    <input type="hidden" name="command" value="sign_in">
                    <fieldset>
                        <div class="form-group">
                            <input class="form-control" placeholder="E-mail" name="email" type="email" autofocus="">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Password" name="password" type="password" value="">
                        </div>
                        <fmt:message key="signin.button.submit" var="buttonValue" bundle="${ i18n }"/>
                        <input type="submit" name="submit" value="${buttonValue}" class="btn btn-sm btn-success">
                        <fmt:message key="common.button.cancel" var="buttonCancelValue" bundle="${ i18n }"/>
                        <input type="submit" name="submit" value="${buttonCancelValue}" class="btn btn-sm btn-danger"
                               data-dismiss="modal">
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-2">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="login.label.signup" bundle="${ i18n }"/></h4>
            </div>
            <div class="modal-body">
                <form role="form" method="post" action="/controller">
                    <input type="hidden" name="command" value="sign_up">
                    <fieldset>
                        <div class="form-group">
                            <label for="email">
                                <fmt:message key="signup.label.email" bundle="${ i18n }"/>:<span class="asterisk"> *</span>
                            </label>
                            <input class="form-control" id="email" name="email" value="${requestValues.email}"
                                   placeholder="<fmt:message key="signup.label.email" bundle="${ i18n }"/>"
                                   type="email">
                        </div>
                        <div class="form-group">
                            <label for="password">
                                <fmt:message key="signup.label.password" bundle="${ i18n }"/>:<span class="asterisk"> *</span>
                            </label>
                            <input class="form-control"
                                   placeholder="<fmt:message key="signup.label.password" bundle="${ i18n }"/>"
                                   id="password" name="password" type="password">
                        </div>
                        <div class="form-group">
                            <label for="repeat-password">
                                <fmt:message key="signup.label.repeat-password" bundle="${ i18n }"/>:<span class="asterisk"> *</span></label>
                            <input class="form-control"
                                   placeholder="<fmt:message key="signup.label.password" bundle="${ i18n }"/>"
                                   type="password" id="repeat-password" name="repeatPassword">
                        </div>
                        <div class="form-group">
                            <label for="name">
                                <fmt:message key="signup.label.name" bundle="${ i18n }"/>:<span class="asterisk"> *</span>
                            </label>
                            <input class="form-control"
                                   placeholder="<fmt:message key="signup.label.name" bundle="${ i18n }"/>"
                                   type="text"
                                   id="name" name="name" value="${requestValues.name}">
                        </div>
                        <div class="form-group">
                            <label for="surname">
                                <fmt:message key="signup.label.surname" bundle="${ i18n }"/>:<span class="asterisk"> *</span>
                            </label>
                            <input class="form-control" id="surname" name="surname" value="${requestValues.surname}"
                                   placeholder="<fmt:message key="signup.label.surname" bundle="${ i18n }"/>">
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">
                                <fmt:message key="signup.label.phone-number" bundle="${ i18n }"/>:<span
                                    class="asterisk"> *</span>
                            </label>
                            <input class="form-control" name="phoneNumber"
                                   placeholder="<fmt:message key="signup.label.phone-number" bundle="${ i18n }"/>"
                                   id="phoneNumber"
                                   value="${requestValues.phoneNumber}" type="text">
                        </div>
                        <div class="form-group">
                            <fmt:message key="signup.button.submit" bundle="${ i18n }" var="buttonValue"/>
                            <input type="submit" name="submit" value="${buttonValue}" class="btn btn-sm btn-success">
                            <fmt:message key="common.button.cancel" var="buttonCancelValue" bundle="${ i18n }"/>
                            <input type="submit" name="submit" value="${buttonCancelValue}" class="btn btn-sm btn-danger"
                                   data-dismiss="modal">
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
