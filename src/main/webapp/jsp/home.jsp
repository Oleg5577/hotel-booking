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
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%--<link href="../css/bootstrap.css" rel="stylesheet">--%>
    <%--<link href="../css/font-awesome.css" rel="stylesheet">--%>
    <%--<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->--%>
    <%--<script src="../js/jquery-1.12.4.min.js"></script>--%>
    <%--<!-- Include all compiled plugins (below), or include individual files as needed -->--%>
    <%--<script src="../js/bootstrap.js"></script>--%>
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
HOME PAGE
<%--<div class="modal fade" id="modal-1">
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
            <ul class="nav navbar-nav">
                <li><a href="#">Пункт 1</a></li>
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
                <li><a href="#">Пункт 4</a></li>
            </ul>
            <form action="" class="navbar-form navbar-right" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Email" value="">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="Пароль">
                </div>
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-sign-in"></i>Войти
                </button>
            </form>
        </div>
    </div>
</div>--%>
</body>
</html>



