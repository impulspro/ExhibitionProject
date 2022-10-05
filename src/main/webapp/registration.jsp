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
    <form action="${pageContext.request.contextPath}/index-servlet" method="post">
        <div class="registration_container">
            <input name="command" type="hidden" value="registration_command">
            <input type="text" name="login" placeholder="<fmt:message key='registration.form.enterLogin'/>">
            <input type="password" name="password" placeholder="<fmt:message key='registration.form.enterPassword'/>">
            <button type="submit"><fmt:message key='registration.form.register'/></button>
        </div>
    </form>
</main>
<%@include file="view/template/message.jsp" %>
</body>
</html>
