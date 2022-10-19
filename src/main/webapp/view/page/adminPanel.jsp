<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="text"/>

<html lang="${param.lang}">
<head>
  <title>Exhibition</title>

  <%@include file="../template/styles.jsp" %>

</head>
<body>

<%@include file="../template/header.jsp" %>

<main>

</main>
<%@include file="../template/message.jsp" %>
</body>
</html>
