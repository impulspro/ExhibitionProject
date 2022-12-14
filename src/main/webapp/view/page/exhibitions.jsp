<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="customTag" uri="/WEB-INF/custom.tld" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.temporal.ChronoUnit" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html lang="uk">
<head>
    <title>Exhibitions jsp</title>
</head>
<div class="container">
    <div class="row">
        <c:set var="i" value="1"/>
        <c:forEach var="exh" items="${sessionScope.exhibitionsList}">

            <div class="col-lg-4">
                <div class="card bg-light">
                    <div class="card-body">

                        <h5 class="card-title">${exh.theme}
                            <c:if test="${exh.price != '-1'}">
                                <strong class="float-right">${exh.price}$</strong>
                            </c:if>
                        </h5>

                        <strong>
                            <p class="text-center">
                                    ${exh.startDate}
                                (${exh.startTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS)}
                                -
                                    ${exh.endTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS)})
                                    ${exh.endDate}
                            </p>
                        </strong>
                        <p class="text-center">
                            <fmt:message key='exhibition.halls'/>:
                            <c:forEach var="hall" items="${sessionScope.hallService.getHallsByExhibitionId(exh.id)}">
                                <strong> ${hall.name}</strong>
                            </c:forEach>
                        </p>
                        <p class="text-center">
                                ${exh.details}
                        </p>
                    </div>
                    <div class="card-footer align-items-center">
                        <div class="d-flex justify-content-md-center">
                            <div class="row">
                                <c:if test="${sessionScope.user.role == null}">
                                    <a class="btn btn-primary"
                                       href="${pageContext.request.contextPath}/view/page/registration.jsp">
                                        <fmt:message key='exhibition.registryForBuying'/> <span class="sr-only">(current)</span></a>
                                </c:if>

                                <c:if test="${sessionScope.user.role == 'user'}">
                                    <form action="${pageContext.request.contextPath}/index-servlet"
                                          method="post">
                                        <input name="command" type="hidden" value="buyTicketCommand">
                                        <input name="exhibitionId" type="hidden" value="${exh.id}">

                                        <c:if test="${sessionScope.userService.isTicketPresent(sessionScope.user.login, exh.id)}">
                                            <button class="btn btn-success" type="button" disabled>
                                                <fmt:message key='exhibition.alreadyBought'/>
                                            </button>
                                        </c:if>

                                        <c:if test="${!sessionScope.exhibitionService.inPast(exh.id)}">
                                            <c:if test="${exh.price != '-1'}">
                                                <c:if test="${!sessionScope.userService.isTicketPresent(sessionScope.user.login, exh.id)}">
                                                    <button class="btn btn-primary" type="submit">
                                                        <fmt:message key='exhibition.buyTickets'/></button>
                                                </c:if>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${sessionScope.exhibitionService.inPast(exh.id)}">
                                            <c:if test="${exh.price != '-1'}">
                                                <c:if test="${!sessionScope.userService.isTicketPresent(sessionScope.user.login, exh.id)}">
                                                    <button class="btn btn-dark" disabled type="button">
                                                        <fmt:message key='exhibition.alreadyEnded'/></button>
                                                </c:if>
                                            </c:if>
                                        </c:if>

                                        <c:if test="${exh.price == '-1'}">
                                            <button class="btn btn-dark" disabled type="button">
                                                <fmt:message key='exhibition.alreadyCanceled'/></button>
                                        </c:if>
                                    </form>
                                </c:if>


                                <c:if test="${sessionScope.user.role == 'admin'}">

                                    <button class="btn-success" type="button" disabled>
                                            ${sessionScope.exhibitionService.amountOfTicketsByExhibition(exh.id)}
                                        <fmt:message key='exhibition.amountOfTickets'/>
                                    </button>

                                    <c:if test="${sessionScope.exhibitionService.inPast(exh.id)}">
                                        <c:if test="${!sessionScope.userService.isTicketPresent(sessionScope.user.login, exh.id)}">
                                            <button class="btn btn-dark" disabled type="button">
                                                <fmt:message key='exhibition.alreadyEnded'/></button>
                                        </c:if>
                                    </c:if>

                                    <c:if test="${!sessionScope.exhibitionService.inPast(exh.id)}">
                                        <form action="${pageContext.request.contextPath}/index-servlet"
                                              method="post">
                                            <input name="command" type="hidden" value="cancelExhibitionCommand">
                                            <input name="exhibitionId" type="hidden" value="${exh.id}">

                                            <c:if test="${exh.price != '-1'}">
                                                <button class="btn-warning" type="submit">
                                                    <fmt:message key='exhibition.cancel'/>
                                                </button>
                                            </c:if>
                                            <c:if test="${exh.price == '-1'}">
                                                <button class="btn-dark disabled" disabled type="button">
                                                    <fmt:message key='exhibition.alreadyCanceled'/></button>
                                            </c:if>

                                        </form>
                                        <form action="${pageContext.request.contextPath}/index-servlet"
                                              method="post">
                                            <input name="command" type="hidden" value="deleteExhibitionCommand">
                                            <input name="exhibitionId" type="hidden" value="${exh.id}">
                                            <button class="btn-danger" type="submit">
                                                <fmt:message key='exhibition.delete'/>
                                            </button>
                                        </form>
                                    </c:if>

                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <c:set var="i" value="${i+1}"/>
        </c:forEach>
    </div>

    <div class="d-flex justify-content-md-center">
        <nav class="align-content-center align-items-center">
            <ul class="pagination">
                <c:forEach var="i" begin="1" end="${sessionScope.amountOfPages}">
                    <li class="page-item"><a class="page-link"
                                             href="${pageContext.request.contextPath}/index-servlet?command=getExhibitionsCommand&currentPage=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>

</div>
</html>
