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
    <table align="center" class="exhibitions" cellspacing="9">
        <c:forEach var="hall" items="${hallList}">

            <tr>
                <td><b>${hall.name}</b><br>
                        ${hall.details} <br>
                </td>
            </tr>

            <c:set var="i" value="${i+1}"/>

        </c:forEach>

    </table>
</main>

<%@include file="template/message.jsp" %>

</body>
</html>
