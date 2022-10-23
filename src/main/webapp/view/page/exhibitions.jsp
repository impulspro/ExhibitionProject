<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="text"/>

<html lang="${param.lang}">
<head>
    <title>Exhibition</title>

    <%@include file="../template/styles.jsp" %>
</head>
<body style="background-image: url('../img/back2.jpg');">

<%@include file="../template/header.jsp" %>

<main>

    <div class="container mt-3">
        <c:if test="${sessionScope.exhList != null}">
            <c:set var="i" value="1"/>
            <c:forEach var="exh" items="${sessionScope.exhList}">
                <div class="d-flex justify-content-md-center">

                    <div class="col-6 card align-content-center text-center bg-info">
                        <div class="card-header bg-light">
                            <fmt:message key='exhibition.from'/>: <b> ${exh.startDate} </b>
                            <fmt:message key='exhibition.to'/>: <b> ${exh.endDate} </b>
                            <fmt:message key='exhibition.price'/> <b> = ${exh.price}$</b>
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
                        <div class="card-footer align-items-center">
                            <div class="d-flex justify-content-md-center">
                                <div class="row">

                                    <c:if test="${sessionScope.user.role == null}">
                                        <button class="btn btn-primary" disabled type="button">
                                            <fmt:message key='exhibition.registryForBuying'/>:
                                        </button>
                                    </c:if>

                                    <c:if test="${sessionScope.user.role == 'user'}">
                                        <form action="${pageContext.request.contextPath}/index-servlet"
                                              method="post">
                                            <input name="command" type="hidden" value="buyTicket_command">
                                            <input name="exhibition_id" type="hidden" value="${exh.id}">

                                            <c:if test="${sessionScope.user.isTicketPresent(exh.id)}">
                                                <button class="btn btn-success" type="button" disabled >
                                                    <fmt:message key='exhibition.alreadyBought'/>:
                                                </button>
                                            </c:if>

                                            <c:if test="${exh.price != '-1'}">
                                                <c:if test="${!sessionScope.user.isTicketPresent(exh.id)}">
                                                    <button class="btn btn-primary" type="submit">
                                                        <fmt:message key='exhibition.buyTickets'/></button>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${exh.price == '-1'}">
                                                <button class="btn btn-dark" disabled type="button">
                                                    <fmt:message key='exhibition.alreadyCanceled'/></button>
                                            </c:if>
                                        </form>
                                    </c:if>

                                    <c:if test="${sessionScope.user.role == 'admin'}">
                                        <form action="${pageContext.request.contextPath}/index-servlet"
                                              method="post">
                                            <input name="command" type="hidden" value="cancelExhibition_command">
                                            <input name="exhibition_id" type="hidden" value="${exh.id}">

                                            <button class="btn-success" type="button">
                                                    ${exh.amountOfTickets()} <fmt:message
                                                    key='exhibition.amountOfTickets'/>
                                            </button>

                                            <c:if test="${exh.price != '-1'}">
                                                <button class="btn btn-warning" type="submit">
                                                    <fmt:message key='exhibition.cancel'/>
                                                </button>
                                            </c:if>
                                            <c:if test="${exh.price == '-1'}">
                                                <button class="btn btn-dark disabled" disabled type="button">
                                                    <fmt:message key='exhibition.alreadyCanceled'/></button>
                                            </c:if>

                                        </form>
                                        <form action="${pageContext.request.contextPath}/index-servlet"
                                              method="post">
                                            <input name="command" type="hidden" value="deleteExhibition_command">
                                            <input name="exhibition_id" type="hidden" value="${exh.id}">
                                            <button class="btn btn-danger" type="submit">
                                                <fmt:message key='exhibition.delete'/>
                                            </button>
                                        </form>
                                    </c:if>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <c:set var="i" value="${i+1}"/>
            </c:forEach>
        </c:if>

        <div class="d-flex justify-content-md-center">
            <nav class="align-content-center align-items-center">
                <ul class="pagination">
                    <c:forEach var="i" begin="1" end="${sessionScope.noOfPages}">

                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/index-servlet?command=getExhibitions_command&currentPage=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </div>
</main>

<%@include file="../template/message.jsp" %>

</body>
</html>



