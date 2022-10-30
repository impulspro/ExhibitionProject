<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="uk">
<head>
    <title>Exhibition</title>
    <%@include file="/view/template/styles.jsp" %>
</head>
<body>
<header>
    <%@include file="/view/template/header.jsp" %>
</header>

<main>
    <div class="container-fluid d-flex justify-content-center align-items-center p-0">
        <div class="row bg-white shadow-sm">
            <div class="col-3 border rounded bg-info">
                <%@ include file="../template/admin/hallsOccupation.jspf" %>
            </div>
            <div class="col-3 border rounded bg-success">
                <%@ include file="../template/admin/addExhibition.jspf" %>
            </div>
            <div class="col-3 border rounded bg-light">
                <%@ include file="../template/admin/listOfAllUsers.jspf" %>
            </div>
            <div class="col-3 border rounded bg-light">
                <%@ include file="../template/admin/searchUserForm.jspf" %>
            </div>
        </div>
    </div>
</main>
<%@include file="/view/template/message.jsp" %>
</body>
</html>