<%@ page import="com.exhibit.model.Hall" %>
<%@ page import="com.exhibit.services.HallService" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="customTag" uri="/WEB-INF/custom.tld" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="text"/>

<html lang="${param.lang}">
<head>
    <title>Exhibition</title>

    <%@include file="view/template/styles.jsp" %>
    <%@include file="view/template/header.jsp" %>
    <%
        List<Hall> hallList = new HallService().findAll();
        session.setAttribute("hallList", hallList);
    %>

</head>
<body style="background-image: url('view/img/back2.jpg');">


<main>
    <div class="container mt-3">
        <div class="d-flex justify-content-md-center bg-warning mb-3">
            <h2><fmt:message key="index.welcome"/><customTag:Hello message="!"/></h2>
        </div>
        <div class="d-flex justify-content-md-center">
            <div class="col">
                <div class="d-flex justify-content-md-center">
                    <div class="row">
                        <form action="${pageContext.request.contextPath}/index-servlet" method="get">
                            <input name="command" type="hidden" value="getExhibitions_command">
                            <input name="sortType" type="hidden" value="sortByPrice">
                            <button class="btn btn-success" type="submit"><fmt:message
                                    key='index.exhibition.byPrice'/></button>
                        </form>
                        &nbsp; &nbsp; &nbsp;
                        <form action="${pageContext.request.contextPath}/index-servlet" method="get">
                            <input name="command" type="hidden" value="getExhibitions_command">
                            <input name="sortType" type="hidden" value="sortByTheme">
                            <button class="btn btn-info" type="submit"><fmt:message
                                    key='index.exhibition.byTheme'/></button>
                        </form>
                        &nbsp; &nbsp; &nbsp;
                        <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet" method="get">
                            <input name="command" type="hidden" value="getExhibitions_command">
                            <input name="sortType" type="hidden" value="sortByDate">
                            <label for="exhDate"></label><input type="date" name="exhDate" id="exhDate">
                            <button class="btn btn-dark" type="submit"><fmt:message
                                    key='index.exhibition.byDate'/></button>
                        </form>
                        &nbsp; &nbsp; &nbsp;
                        <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet"
                              method="get">
                            <input name="sortType" type="hidden" value="sortByHall">
                            <input name="command" type="hidden" value="getExhibitions_command">
                            <div class="form-group">
                                <label for="hallId"></label><select name="hall_id" class="form-control" id="hallId" required>
                                    <c:forEach items="${hallList}" var="hall">
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

</main>

<%@include file="view/template/message.jsp" %>

</body>
</html>



