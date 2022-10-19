<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="text"/>

<html lang="${param.lang}">
<head>
    <title>Exhibition</title>

    <%@include file="../template/styles.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}static/css/index.css">
</head>
<body style="background-image: url('../img/back2.jpg');">

<%@include file="../template/header.jsp" %>

<main>

    <c:if test="${sessionScope.exhList != null}">

        <c:set var="i" value="1"/>

        <c:forEach var="exh" items="${exhList}">

            <div class="col-3 bg-info">
                <div class="card-header bg-light">
                    <fmt:message key='exhibition.from'/>: <b> ${exh.startDate} </b>
                    <fmt:message key='exhibition.to'/>: <b> ${exh.endDate} </b>
                    <fmt:message key='exhibition.price'/> <b> = ${exh.price}</b>
                </div>
                <div class="card-body">
                    <h5 class="card-title">${exh.theme}</h5>
                    <p class="card-text">${exh.details}</p>
                    <p> ${exh.startTime}-${exh.endTime}
                        <b><fmt:message key='exhibition.halls'/>:</b>
                        <c:forEach var="hall" items="${exh.getHalls()}">
                            ${hall.name}
                        </c:forEach>
                    </p>

                </div>
                <div class="row">
                    <c:if test="${sessionScope.user.role == null}">
                        <button class="btn btn-primary" disabled>
                            <fmt:message key='exhibition.registryForBuying'/>:
                        </button>
                    </c:if>

                    <c:if test="${sessionScope.user.role == 'user'}">
                        <form action="${pageContext.request.contextPath}/index-servlet" method="post">
                            <input name="command" type="hidden" value="buyTicket_command">
                            <input name="exhibition_id" type="hidden" value="${exh.id}">

                            <c:if test="${sessionScope.user.isTicketPresent(exh.id)}">
                                <button class="btn btn-success" disabled>
                                    <fmt:message key='exhibition.alreadyBought'/>:
                                </button>
                            </c:if>

                            <c:if test="${!sessionScope.user.isTicketPresent(exh.id)}">
                                <button class="btn btn-primary" type="submit">
                                    <fmt:message key='exhibition.buyTickets'/></button>
                            </c:if>

                        </form>
                    </c:if>

                    <c:if test="${sessionScope.user.role == 'admin'}">


                        <form action="${pageContext.request.contextPath}/index-servlet" method="put">
                            <input name="command" type="hidden" value="cancelExhibition_command">
                            <input name="exhibition_id" type="hidden" value="${exh.id}">
                            <button class="btn-success" type="button">
                                    ${exh.amountOfTickets()} <fmt:message key='exhibition.amountOfTickets'/>:
                            </button>
                            <button class="btn btn-danger" type="submit" >
                                <fmt:message key='exhibition.cancel'/>
                            </button>
                        </form>
                        <form action="${pageContext.request.contextPath}/index-servlet" method="put">
                            <input name="command" type="hidden" value="deleteExhibition_command">
                            <input name="exhibition_id" type="hidden" value="${exh.id}">
                            <button class="btn btn-danger" type="submit" >
                                <fmt:message key='exhibition.delete'/>
                            </button>
                        </form>
                    </c:if>

                </div>
            </div>

            <c:set var="i" value="${i+1}"/>
            <br>

        </c:forEach>
    </c:if>
</main>

<%@include file="../template/message.jsp" %>

</body>
</html>



