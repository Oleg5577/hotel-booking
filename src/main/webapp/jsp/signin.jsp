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
    <title>Sign in</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="shortcut icon" href="../img/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title"><fmt:message key="signin.label" bundle="${ i18n }"/></h4>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="/controller">
                        <input type="hidden" name="command" value="sign_in">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="E-mail" name="email" type="email"
                                       autofocus="" value="${requestValues.email}">
                                <label class="wrong-values">${wrongValues.email}</label>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" name="password" type="password">
                                <label class="wrong-values">${wrongValues.password}</label>
                                <label class="wrong-values">${wrongValues.emailOrPassword}</label>
                            </div>
                            <fmt:message key="signin.button.submit" bundle="${ i18n }" var="buttonValue"/>
                            <input type="submit" name="submit" value="${buttonValue}"
                                   class="btn btn-md btn-success">
                            <fmt:message key="common.button.cancel" bundle="${ i18n }" var="buttonCancelValue"/>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<jsp:include page="/jsp/section/footer.jsp"/>
</body>
</html>



