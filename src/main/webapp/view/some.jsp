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
    <div class="container">

        <h1><fmt:message key="index.welcome"/></h1>
        <h2><fmt:message key="index.showrooms"/></h2>

    </div>


</main>

<%@include file="template/message.jsp" %>

</body>
</html>
