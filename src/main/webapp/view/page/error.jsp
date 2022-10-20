<%
    String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().toString();
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="text"/>

<html lang="${param.lang}">
<head>
    <title>Exhibition</title>

    <%@include file="/view/template/styles.jsp" %>
</head>
<body>
<%@include file="/view/template/header.jsp" %>
<h2>Exception occurred while processing the request</h2>
<p>Type: <%= exception%></p>
<p>Message: <%= message %></p>
</body>
</html>

