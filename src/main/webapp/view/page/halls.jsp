<%@ page import="com.exhibit.model.Hall" %>
<%@ page import="com.exhibit.services.HallService" %>
<%@ page import="java.util.List" %>
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
    <%@include file="../template/header.jsp" %>
    <%
        List<Hall> hallList = new HallService().findAll();
        session.setAttribute("hallList", hallList);
    %>
</head>
<body>
<main>

    <!-- Single item -->
    <div class="container">
        <div class="row">
            <c:set var="i" value="1"/>
            <c:forEach var="hall" items="${hallList}">

                <div class="col-lg-4">
                    <div class="card">
                        <img
                                src="../img/Halls/${hall.id}_hall.jpg"
                                class="card-img-top"
                         alt="${hall.id}_hall"/>
                        <div class="card-body">
                            <h5 class="card-title">${hall.name}</h5>
                            <p class="card-text">
                                    ${hall.details}
                            </p>
                            <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet"
                                  method="get">
                                <input name="command" type="hidden" value="getExhibitions_command">
                                <input name="sortType" type="hidden" value="sortByHall">
                                <input name="hall_id" type="hidden" value=${hall.id}>
                                <button class="btn-success" type="submit"><fmt:message
                                        key='index.exhibition.byHall'/></button>
                            </form>
                        </div>
                    </div>
                </div>
                <c:set var="i" value="${i+1}"/>
            </c:forEach>
        </div>
    </div>

</main>

<%@include file="../template/message.jsp" %>
</body>
</html>
