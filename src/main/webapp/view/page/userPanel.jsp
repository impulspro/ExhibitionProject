<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.temporal.ChronoUnit" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="uk">
<head>
    <title>Exhibition</title>
    <%@include file="/view/template/styles.jsp" %>
</head>
<body>
<header>
    <%@include file="/view/template/header.jsp" %>
</header>

<main>
    <div class="container bg-light">
        <br> <br>
        <div class="row justify-content-md-center">

            <button type="button" disabled class="btn-warning">
                ${sessionScope.user.money}$
            </button>


            <form name="addMoneyUser"
                  action="${pageContext.request.contextPath}/index-servlet"
                  method="post">
                <div class="input-group">
                    <div class="form-inline">
                        <input name="command" type="hidden" value="addUserFundsCommand">
                        <label for="moneyId"></label> <br>
                        <input type="number" name="money" id="moneyId" required step="50" placeholder="0.00" min="0">
                    </div>
                    <button type="submit" class="btn-warning">
                        <fmt:message key='userPanel.addMoney'/>
                    </button>
                </div>
            </form>


            <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet"
                  method="get">
                <input name="command" type="hidden" value="printPdfCommand">
                <button class="btn-dark" type="submit"><fmt:message
                        key='header.printPdf'/></button>
            </form>

        </div>
        <br>
        <div class="row justify-content-center">
            <strong><fmt:message key='adminPanel.userExhibitions'/>:</strong>

        </div>
        <br>
        <c:forEach var="ticket" items="${sessionScope.user.getUserTickets()}">
            <div class="row justify-content-md-center">
                <c:set var="exhibition" value="${ticket.getExhibition()}"/>
                <div class="col-md-auto">
                <strong>
                        ${exhibition.theme}
                        ${exhibition.startDate}
                    (${exhibition.startTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS)}
                    -
                        ${exhibition.endTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS)})
                        ${exhibition.endDate}
                        ${exhibition.price}
                </strong>
                </div>
                <div class="col-md-auto">
                <c:if test="${ticket.isCanBeReturn()}">
                    <form name="returnTicket"
                          action="${pageContext.request.contextPath}/index-servlet"
                          method="post">
                        <div class="input-group">
                            <div class="form-inline">
                                <input name="command" type="hidden" value="returnTicketCommand">
                                <input name="exhibitionId" type="hidden" value="${exhibition.id}">
                            </div>
                            <button type="submit" class="btn-danger">
                                <fmt:message key='userPanel.returnTicket'/>
                            </button>
                        </div>
                    </form>
                </c:if>
                </div>
            </div>
            <br>
        </c:forEach>
    </div>
</main>
<%@include file="/view/template/message.jsp" %>
</body>
</html>