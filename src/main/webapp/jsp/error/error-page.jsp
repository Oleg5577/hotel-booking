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
    <title>Server error</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="shortcut icon" href="../../img/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="container">
    <div class="jumbotron">
        <h1><span class="glyphicon glyphicon-fire red"></span>500 Internal Server Error</h1>
        <p class="lead">The web server is returning an internal error for <em><span id="display-domain"></span></em>.</p>
        <a href="javascript:document.location.reload(true);" class="btn btn-default btn-lg text-center">
            <span class="green">Try This Page Again</span>
        </a>
        <a href="<fmt:message key="path.page.home" bundle="${ path }"/>" class="btn btn-default btn-lg">
            <span class="green">To Home page</span>
        </a>
    </div>
</div>
<jsp:include page="/jsp/section/footer.jsp"/>
</body>
</html>
