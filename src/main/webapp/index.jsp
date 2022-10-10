<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="text" />

<html lang="${param.lang}">
<head>
    <title>Exhibition</title>

    <%@include file="view/template/styles.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}static/css/index.css">
</head>
<body>

<%@include file="view/template/header.jsp" %>

<main>
    <div class="container">

        <h1><fmt:message key="index.welcome"/></h1>
        <h2><fmt:message key="index.showrooms"/></h2>

    </div>

    <div class="exhibitions_container">

        <h1><fmt:message key='index.topic'/></h1>

        <form action="home" method="get">
            <input name="command" type="hidden" value="command_getExhibitions">
            <input name="pageNum" type="hidden" value="1">
            <input name="sortType" type="hidden" value="default">
            <button type="submit"><fmt:message key='index.exhibition.byDefault'/></button>
        </form>
    </div>



</main>

<%@include file="view/template/message.jsp" %>

</body>
</html>
