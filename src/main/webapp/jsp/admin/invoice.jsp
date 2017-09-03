<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.locale" var="i18n"/>
<fmt:setBundle basename="property.page" var="path"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="admin.account.label.invoice" bundle="${ i18n }"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="shortcut icon" href="../../img/favicon.ico" type="image/x-icon">
    <link href="../../css/bootstrap.css" rel="stylesheet">
    <link href="../../css/font-awesome.css" rel="stylesheet">
    <link href="../../css/style.css" rel="stylesheet">
    <script src="../../js/jquery-3.2.1.js"></script>
    <script src="../../js/bootstrap.js"></script>
    <script src="../../js/main.js"></script>
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="container">
    <div class="row">
    <div id="printableArea">
        <div class="row">
            <div class="col-xs-12">
                <div class="invoice-title">
                    <h2>Invoice / Счет</h2>
                    <h3 class="pull-right">Order / Заказ № ${roomOrder.id}</h3>
                </div>
                <hr>
                <div class="row">
                    <div class="col-xs-6">
                        <address>
                            <strong>Bill to / Плательщик: </strong><br>
                            ${roomOrder.user.name} ${roomOrder.user.surname}<br>
                            ${roomOrder.user.email}<br>
                            ${roomOrder.user.phoneNumber}<br>
                        </address>
                    </div>
                    <div class="col-xs-6 text-right">
                        <address>
                            <strong>Payment to / Получатель оплаты:</strong><br>
                            Booking hotel<br>
                            Nemiga str, 1<br>
                            Minsk, Belarus<br>
                        </address>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 col-xs-offset-6 text-right">
                        <address>
                            <strong>Invoice Date / Дата счета:</strong><br>
                            <ctg:current-date/><br><br>
                        </address>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><strong>Order summary / Итог заказа</strong></h3>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-condensed">
                                <thead>
                                <tr>
                                    <th><strong>Room / Номер</strong></th>
                                    <th class="text-center"><strong>Check-in / Заселение</strong></th>
                                    <th class="text-center"><strong>Check-out / Выезд</strong></th>
                                    <th class="text-center"><strong>Quantity / Количество дней</strong></th>
                                    <th class="text-center"><strong>Price / Цена</strong></th>
                                    <td class="text-right"><strong>Amount / Сумма</strong></td>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            №${roomOrder.room.roomNumber},
                                            <c:choose>
                                                <c:when test="${roomOrder.room.roomType == 'STANDARD'}">
                                                    standard / стандартный
                                                </c:when>
                                                <c:when test="${roomOrder.room.roomType == 'SEMILUX'}">
                                                    semilux / полулюкс
                                                </c:when>
                                                <c:when test="${roomOrder.room.roomType == 'LUX'}">
                                                    lux / люкс
                                                </c:when>
                                                <c:when test="${roomOrder.room.roomType == 'PRESIDENT'}">
                                                    president / президентский
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">${roomOrder.checkInDate}</td>
                                        <td class="text-center">${roomOrder.checkOutDate}</td>
                                        <td class="text-center">${daysNumber}</td>
                                        <td class="text-center">${roomOrder.room.price} EUR</td>
                                        <td class="text-right">${roomOrder.amount} EUR</td>
                                    </tr>
                                    <tr>
                                        <td class="thick-line"></td>
                                        <td class="thick-line"></td>
                                        <td class="thick-line"></td>
                                        <td class="thick-line"></td>
                                        <td class="thick-line text-center"><strong>Total / Итого</strong></td>
                                        <td class="thick-line text-right">${roomOrder.amount} EUR</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <button onclick="printDiv('printableArea')" class="btn btn-info">
        <fmt:message key="admin.account.label.invoice.print" bundle="${ i18n }"/>
    </button>
    <a href="/controller?command=find_info_for_admin_account" class="btn btn-default">
        <span class="green"><fmt:message key="admin.account.label" bundle="${ i18n }"/></span>
    </a>
</div>
</div>
<jsp:include page="/jsp/section/footer.jsp"/>
</body>
</html>
