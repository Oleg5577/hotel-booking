<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler"/>
<fmt:setBundle basename="property.page" var="path"/>
<jsp:useBean id="user" scope="session" class="com.pronovich.hotelbooking.entity.User"/>
<html>
<head>
    <title>Title</title>
    <link href="../../css/bootstrap.css" rel="stylesheet">
    <link href="../../css/font-awesome.css" rel="stylesheet">
    <script src="../../js/jquery-1.12.4.min.js"></script>
    <script src="../../js/bootstrap.js"></script>
</head>
<body>
<div class="modal fade" id="modal-1">
    <!--        <div class="modal-dialog">-->
    <!--        <div class="modal-dialog modal-lg">-->
    <!--        <div class="modal-dialog modal-sm">-->
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Название окна (Sign in)</h4>
            </div>
            <div class="modal-body">
                <p>Это модальное окно</p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-danger" type="button" data-dismiss="modal">Отмена</button>
            </div>
        </div>
    </div>
</div>
<div class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Logo</a>
        </div>
        <div class="collapse navbar-collapse" id="responsive-menu">
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${user.id == null}">
                    <li class="nav navbar-right"><a href="<fmt:message key="path.page.signup" bundle="${ path }"/>">Go
                        to sign up</a></li>
                    <li class="nav navbar-right"><a href="<fmt:message key="path.page.signin" bundle="${ path }"/>">Go
                        to sign in</a></li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <c:choose>
                    <c:when test="${user.role == 'ADMIN'}">
                        <%--            <form action="/controller" method="get">
                                        <input type="hidden" name="command" value="get_info_for_admin_account">
                                        <fmt:message key="admin.account.button.submit" var="buttonValue"/>
                                        <input type="submit" name="submit" value="${buttonValue}">
                                    </form>--%>
                        <%--<a href="<fmt:message key="path.page.admin.admin-account" bundle="${ path }"/>">Admin account</a>--%>
                        <li><a href="/controller?command=find_info_for_admin_account">Admin account</a>
                    </c:when>
                    <c:when test="${user.role == 'CLIENT'}">
                        <li><a href="<fmt:message key="path.page.client.add-room-request" bundle="${ path }"/>">Add room
                            request</a></li>
                        <li><a href="<fmt:message key="path.page.client.personal-account" bundle="${ path }"/>">Personal-account</a>
                        </li>
                    </c:when>
                </c:choose>
                <%--<li><a href="#">Пункт 1</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Пункт 2<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Пункт 1</a></li>
                        <li><a href="#">Пункт 2</a></li>
                        <li><a href="#">Пункт 3</a></li>
                        <li class="divider"></li>
                        <li><a href="#">Пункт 4</a></li>
                    </ul>
                </li>
                <li><a href="#">Пункт 3</a></li>
                <li><a href="#">Пункт 4</a></li>--%>
            </ul>
            <%--            <form action="" class="navbar-form navbar-right" method="post">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Email" value="">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="Пароль">
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <i class="fa fa-sign-in"></i>Войти
                            </button>
                        </form>--%>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <button class="btn btn-success" data-toggle="modal" data-target="#modal-1">Открыть модальное окно(Sign in)
            </button>
        </div>
    </div>
</div>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>
<%--<jsp:useBean id="user" scope="session" class="com.pronovich.hotelbooking.entity.User"/>--%>
<c:if test="${user.id != null}">
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="sign_out">
        <fmt:message key="signout.button.submit" var="buttonValue"/>
        <input type="submit" name="submit" value="${buttonValue}">
    </form>
</c:if>
<%--<c:if test="${user.id == null}">
    <br>
    <a href="<fmt:message key="path.page.signup" bundle="${ path }"/>">Go to sign up</a>
    <br>
    <a href="<fmt:message key="path.page.signin" bundle="${ path }"/>">Go to sign in</a>
    <br>
</c:if>--%>
<%--<c:choose>
    <c:when test="${user.role == 'ADMIN'}">
        &lt;%&ndash;            <form action="/controller" method="get">
                        <input type="hidden" name="command" value="get_info_for_admin_account">
                        <fmt:message key="admin.account.button.submit" var="buttonValue"/>
                        <input type="submit" name="submit" value="${buttonValue}">
                    </form>&ndash;%&gt;
        &lt;%&ndash;<a href="<fmt:message key="path.page.admin.admin-account" bundle="${ path }"/>">Admin account</a>&ndash;%&gt;
        <a href="/controller?command=find_info_for_admin_account">Admin account</a>
        <br>
    </c:when>
    <c:when test="${user.role == 'CLIENT'}">
        <a href="<fmt:message key="path.page.client.add-room-request" bundle="${ path }"/>">Add room request</a>
        <br>
        <a href="<fmt:message key="path.page.client.personal-account" bundle="${ path }"/>">Personal-account</a>
        <br>
    </c:when>
</c:choose>--%>
<a href="<fmt:message key="path.page.find-room" bundle="${ path }"/>">Find room</a>
<br>
</body>
</html>
