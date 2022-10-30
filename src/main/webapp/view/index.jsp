<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="customTag" uri="/WEB-INF/custom.tld" %>
<%@ page isELIgnored="false"%>
<%@ page import="java.time.temporal.ChronoUnit" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html lang="uk">
<head>
    <title>Exhibition</title>
    <%@include file="template/styles.jsp" %>
</head>
<body>
<header>
    <%@include file="template/header.jsp" %>
</header>

<div class="container mt-3">
    <div class="d-flex justify-content-md-center mb-3 bg-light">
        <h3 class="font-italic"><fmt:message key="index.welcome"/><customTag:Hello message="!"/></h3>
    </div>
    <div class="d-flex justify-content-md-center">
        <div class="col">
            <div class="d-flex justify-content-md-center">
                <div class="row">
                    <form action="${pageContext.request.contextPath}/index-servlet" method="get">
                        <input name="command" type="hidden" value="getExhibitionsCommand">
                        <input name="sortType" type="hidden" value="sortByPrice">
                        <button class="btn btn-success" type="submit"><fmt:message
                                key='index.exhibition.byPrice'/></button>
                    </form>
                    &nbsp; &nbsp; &nbsp;
                    <form action="${pageContext.request.contextPath}/index-servlet" method="get">
                        <input name="command" type="hidden" value="getExhibitionsCommand">
                        <input name="sortType" type="hidden" value="sortByTheme">
                        <button class="btn btn-info" type="submit"><fmt:message
                                key='index.exhibition.byTheme'/></button>
                    </form>
                    &nbsp; &nbsp; &nbsp;
                    <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet"
                          method="get">
                        <input name="command" type="hidden" value="getExhibitionsCommand">
                        <input name="sortType" type="hidden" value="sortByDate">
                        <label for="exhDate"></label><input type="date" name="sortParam" id="exhDate">
                        <button class="btn btn-dark" type="submit"><fmt:message
                                key='index.exhibition.byDate'/></button>
                    </form>
                    &nbsp; &nbsp; &nbsp;
                    <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet"
                          method="get">
                        <input name="sortType" type="hidden" value="sortByHall">
                        <input name="command" type="hidden" value="getExhibitionsCommand">
                        <div class="form-group">
                            <label for="hallId"></label><select name="sortParam" class="form-control" id="hallId"
                                                                required>

                            <c:forEach items="${sessionScope.hallList}" var="hall">
                                <option value="${hall.id}">${hall.name}</option>
                                <br>
                            </c:forEach>
                        </select>
                        </div>
                        <button class="btn btn-primary" type="submit"><fmt:message
                                key='index.exhibition.byHall'/></button>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<main>
    <br> <br>
    <jsp:include page="${sessionScope.showPage}" flush="true" />
</main>

<%@ include file="template/message.jsp" %>
</body>
</html>



