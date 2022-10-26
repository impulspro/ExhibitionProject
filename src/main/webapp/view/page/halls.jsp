<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html lang="uk">
<head>

    <title>Exhibition</title>

    <%@include file="../template/styles.jsp" %>
    <%@include file="../template/header.jsp" %>
    <%@include file="../template/showHalls.jsp" %>

</head>
<body>


<%@include file="../template/message.jsp" %>
</body>
</html>
