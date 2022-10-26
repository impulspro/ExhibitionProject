<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.temporal.ChronoUnit" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html lang="uk">
<head>
    <title>Exhibition</title>

    <%@include file="../template/styles.jsp" %>
</head>
<body style="background-image: url('../img/back2.jpg');">

<main>
    <%@include file="../template/header.jsp" %>
    <%@include file="../template/showExhibition2.jsp" %>
    <%@include file="../template/message.jsp" %>
</main>


</body>
</html>



