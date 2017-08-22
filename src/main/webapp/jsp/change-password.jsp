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
    <title><fmt:message key="change-password.label" bundle="${ i18n }"/></title>
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
                    <h4 class="panel-title"><fmt:message key="change-password.label" bundle="${ i18n }"/></h4>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="/controller">
                        <input type="hidden" name="command" value="change_password">
                        <fieldset>
                            <div class="control-group">
                                <label for="password">
                                    <fmt:message key="signup.label.password" bundle="${ i18n }"/>:<span class="asterisk"> *</span>
                                </label>
                                <input class="form-control"
                                       placeholder="<fmt:message key="signup.label.password" bundle="${ i18n }"/>"
                                       id="password" name="password" type="password" required>
                                <label class="wrong-values">${wrongValues.password}</label>
                            </div>
                            <div class="control-group">
                                <label for="newPassword">
                                    <fmt:message key="change-password.label.new-password" bundle="${ i18n }"/>:<span class="asterisk"> *</span>
                                </label>
                                <input class="form-control" id="newPassword" name="newPassword" value="${requestValues.email}"
                                       placeholder="<fmt:message key="change-password.label.new-password" bundle="${ i18n }"/>"
                                       type="password" required>
                                <label class="wrong-values">${wrongValues.newPassword}</label>
                            </div>
                            <div class="control-group">
                                <label for="repeat-new-password">
                                    <fmt:message key="change-password.label.repeat-new-password" bundle="${ i18n }"/>:<span class="asterisk"> *</span></label>
                                <input class="form-control"
                                       placeholder="<fmt:message key="change-password.label.repeat-new-password" bundle="${ i18n }"/>"
                                       type="password" id="repeat-new-password" name="repeatNewPassword" required>
                                <label class="wrong-values">${wrongValues.repeatNewPassword}</label>
                            </div>
                            <div class="control-group">
                                <fmt:message key="change-password.label" bundle="${ i18n }" var="buttonValue"/>
                                <input type="submit" name="submit" value="${buttonValue}" class="btn btn-md btn-success">
                            </div>
                            <label class="wrong-values">${wrongValues.user}</label>
                            <c:if test="${passwordChangedSuccessfully}">
                                <label class="green">
                                    <fmt:message key="change-password.label.changed-success" bundle="${ i18n }"/>
                                </label>
                            </c:if>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/jsp/section/footer.jsp"/>
</body>
</html>
