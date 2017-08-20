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
    <title><fmt:message key="error-page-404.label" bundle="${ i18n }"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="shortcut icon" href="../../img/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="container">
    <div class="jumbotron">
        <h1><i class="fa fa-frown-o red"></i><fmt:message key="error-page-404.label" bundle="${ i18n }"/></h1>
        <p class="lead"><fmt:message key="error-page-404.message" bundle="${ i18n }"/></p>
        <p>
            <a href="<fmt:message key="path.page.home" bundle="${ path }"/>" class="btn btn-default btn-lg">
                <span class="green"><fmt:message key="error-page.button.to-home" bundle="${ i18n }"/></span>
            </a>
        </p>
    </div>
</div>
<jsp:include page="/jsp/section/footer.jsp"/>
</body>
</html>



