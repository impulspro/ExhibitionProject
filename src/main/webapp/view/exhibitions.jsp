<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="text" />

<html lang="${param.lang}">
<head>
    <title>Exhibition</title>

    <%@include file="template/styles.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}static/css/index.css">
</head>
<body>

<%@include file="template/header.jsp" %>

<main>
    <div class="exhibitions_container">
        <c:if test="${sessionScope.exhList != null}">

            <c:set var="i" value="1"/>

            <c:forEach var="exh" items="${exhList}">
                <tr>
                    <td><b>${exh.theme}</b><br>
                            ${exh.details} <br>

                        <br><b><fmt:message key='index.exhibition.time'/>: <fmt:message
                                key='index.exhibition.from'/></b> ${exh.startDate}
                        <b><fmt:message
                                key='index.exhibition.to'/></b> ${exh.endDate} ${exh.startTime}-${exh.endTime} <br>
                        <b><fmt:message key='index.exhibition.place'/>:</b>
                        <c:forEach var="hall" items="${exh.getHalls()}">
                            ${hall.name} <br>
                        </c:forEach>

                        <b><fmt:message key='index.exhibition.price'/>:</b> ${exh.price} <fmt:message
                                key='index.exhibition.uah'/>
                        <br><br>
                        <c:if test="${sessionScope.user.role == 'admin' || sessionScope.user.role == 'user'}">
                            <form action="${pageContext.request.contextPath}/index-servlet" method="post">
                                <input name="command" type="hidden" value="buyTicket">
                                <input name="exhibitionId" type="hidden" value="${exhibition.id}">
                                <button type="submit"><fmt:message key='index.exhibition.buyTickets'/></button>
                            </form>
                        </c:if>

                        <c:if test="${sessionScope.user.role == 'admin'}">
                            <form action="home" method="get">
                                <input name="command" type="hidden" value="cancelExhibition">
                                <input name="canceledExhibitionId" type="hidden" value="${exhibition.id}">
                                <button type="submit"><fmt:message
                                        key='index.exhibition.cancelExhibition'/></button>
                            </form>
                        </c:if>
                    </td>
                </tr>

                <c:set var="i" value="${i+1}"/>

            </c:forEach>
        </c:if>
    </div>

</main>

<%@include file="template/message.jsp" %>

</body>
</html>


<%--
        <div class="exhibitions_container">
            <c:if test="${exhibitions != null}">

                <c:set var="i" value="1"/>

                    <c:forEach var="exh" items="${exhList}">

                        <tr>
                            <td><b>${exh.theme}</b><br>
                                    ${exh.details} <br>

                                <br><b><fmt:message key='index.exhibition.time'/>: <fmt:message
                                        key='index.exhibition.from'/></b> ${exh.startDate}
                                <b><fmt:message
                                        key='index.exhibition.to'/></b> ${exh.endDate} ${exh.startTime}-${exh.endTime} <br>
                                <b><fmt:message key='index.exhibition.place'/>:</b>
                                <c:forEach var="hall" items="${exh.getHalls()}">
                                    ${hall.name} <br>
                                </c:forEach>

                                <b><fmt:message key='index.exhibition.price'/>:</b> ${exh.price} <fmt:message
                                        key='index.exhibition.uah'/>
                                <br><br>
                                <c:if test="${user.role == 'admin' || user.role == 'user'}">
                                    <form action="${pageContext.request.contextPath}/index-servlet" method="post">
                                        <input name="command" type="hidden" value="buyTicket">
                                        <input name="exhibitionId" type="hidden" value="${exhibition.id}">
                                        <button type="submit"><fmt:message key='index.exhibition.buyTickets'/></button>
                                    </form>
                                </c:if>

                                <c:if test="${user.role == 'admin'}">
                                    <form action="home" method="get">
                                        <input name="command" type="hidden" value="cancelExhibition">
                                        <input name="canceledExhibitionId" type="hidden" value="${exhibition.id}">
                                        <button type="submit"><fmt:message
                                                key='index.exhibition.cancelExhibition'/></button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>

                    <c:set var="i" value="${i+1}"/>

                </c:forEach>
        </c:if>
    </div>
--%>