<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="text"/>

<html lang="${param.lang}">
<head>
    <title>Exhibition</title>

    <%@include file="view/template/styles.jsp" %>


</head>
<body style="background-image: url('view/img/back2.jpg');">

<%@include file="view/template/header.jsp" %>

<main>

    <h1><fmt:message key="index.welcome"/></h1>

    <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet" method="get">
        <input name="command" type="hidden" value="getExhibitions_command">
        <input name="sortType" type="hidden" value="sortByDate">
        <input type="date" placeholder="Date" name="exhDate">
        <button class="btn-success" type="submit"><fmt:message key='index.exhibition.byDate'/></button>
    </form>

    <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet" method="get">
        <input name="command" type="hidden" value="getExhibitions_command">
        <input name="sortType" type="hidden" value="sortByPrice">
        <button class="btn-success" type="submit"><fmt:message key='index.exhibition.byPrice'/></button>
    </form>

    <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet" method="get">
        <input name="command" type="hidden" value="getExhibitions_command">
        <input name="sortType" type="hidden" value="sortByTheme">
        <button class="btn-success" type="submit"><fmt:message key='index.exhibition.byTheme'/></button>
    </form>

    <a href="view/template/addExhibition.jsp" > Add exhibition</a>
</main>

<%@include file="view/template/message.jsp" %>

</body>
</html>



