<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler" var="i18n"/>
<fmt:setBundle basename="property.page" var="path"/>
<jsp:useBean id="user" scope="session" class="com.pronovich.hotelbooking.entity.User"/>
<html>
<head>
    <title>Title</title>
    <link href="../../css/bootstrap.css" rel="stylesheet">
    <link href="../../css/font-awesome.css" rel="stylesheet">
    <link href="../../css/style.css" rel="stylesheet">
    <script src="../../js/jquery-1.12.4.min.js"></script>
    <script src="../../js/bootstrap.js"></script>
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
            <a class="navbar-brand" href="<fmt:message key="path.page.home" bundle="${ path }"/>">Logo</a>
        </div>
        <div class="collapse navbar-collapse" id="responsive-menu">
            <ul class="nav navbar-nav navbar-left">
                <li>
                    <a href="<fmt:message key="path.page.home" bundle="${ path }"/>">
                        <fmt:message key="navbar.link.home" bundle="${ i18n }"/>
                    </a>
                </li>
                <c:choose>
                    <c:when test="${user.role == 'ADMIN'}">
                        <li><a href="/controller?command=find_info_for_admin_account">Admin account</a>
                    </c:when>
                    <c:when test="${user.role == 'CLIENT'}">
                        <li>
                            <a href="<fmt:message key="path.page.client.add-room-request" bundle="${ path }"/>">
                                <fmt:message key="add-room-request.label.search-room" bundle="${ i18n }"/>
                            </a>
                        </li>
                        <li>
                            <a href="<fmt:message key="path.page.client.personal-account" bundle="${ path }"/>">
                                Personal-account
                            </a>
                        </li>
                    </c:when>
                </c:choose>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${user.id == null}">
                    <li>
                        <div class="btn-group btn-group-md">
                            <button class="btn btn-success navbar-btn" data-toggle="modal" data-target="#modal-1">
                                <fmt:message key="login.label.signin" bundle="${ i18n }"/>
                            </button>
                            <button class="btn btn-success navbar-btn" data-toggle="modal" data-target="#modal-2">
                                <fmt:message key="login.label.signup" bundle="${ i18n }"/>
                            </button>
                        </div>
                    </li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${user.id != null}">
                    <form method="post" action="/controller" class="navbar-form">
                        <input type="hidden" name="command" value="sign_out">
                        <fmt:message key="signout.button.submit" bundle="${ i18n }" var="buttonValue"/>
                        <input type="submit" name="submit" value="${buttonValue}" class="btn btn-primary">
                    </form>
                </c:if>
            </ul>
        </div>
    </div>
</div>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>

<a href="<fmt:message key="path.page.find-room" bundle="${ path }"/>">Find room</a>

<div class="modal fade" id="modal-1">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="login.label.signin" bundle="${ i18n }"/></h4>
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
                        <fmt:message key="login.button.submit" var="buttonValue" bundle="${ i18n }"/>
                        <input type="submit" name="submit" value="${buttonValue}" class="btn btn-sm btn-success">
                        <fmt:message key="common.button.cancel" var="buttonCancelValue" bundle="${ i18n }"/>
                        <input type="submit" name="submit" value="${buttonCancelValue}" class="btn btn-sm btn-danger"
                               data-dismiss="modal">
                    </fieldset>
                </form>
                <%-- <form method="post" action="/controller">
                    <input type="hidden" name="command" value="sign_in">
                    <label for="email"><fmt:message key="login.label.email"/>:<span class="asterisk"> *</span></label>
                    <input type="text" id="email" name="email" value="${requestValues.email}">
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
                </form>--%>
            </div>
            <%--<div class="modal-footer">--%>
            <%--<button class="btn btn-danger" type="button" data-dismiss="modal">Отмена</button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-2">
    <!--        <div class="modal-dialog">-->
    <!--        <div class="modal-dialog modal-lg">-->
    <!--        <div class="modal-dialog modal-sm">-->
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="login.label.signup" var="i18n"/></h4>
            </div>
            <div class="modal-body">
                <p>Это модальное окно 2</p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-danger" type="button" data-dismiss="modal">Отмена</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
