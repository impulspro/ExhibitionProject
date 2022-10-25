<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html lang="ua">
<head>

    <title>Exhibition</title>

    <%@include file="../template/styles.jsp" %>
    <%@include file="../template/header.jsp" %>

</head>
<body>
<main>

    <!-- Single item -->
    <div class="container">
        <div class="row">
            <c:set var="i" value="1"/>
            <c:forEach var="hall" items="${sessionScope.hallList}">

                <div class="col-lg-4">
                    <div class="card">
                        <img
                                src="../img/Halls/${hall.id}_hall.jpg"
                                class="card-img-top"
                         alt="${hall.id}_hall"/>
                        <div class="card-body">
                            <h5 class="card-title">${hall.name}</h5>
                            <p class="card-text">
                                    ${hall.details}
                            </p>
                            <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet"
                                  method="get">
                                <input name="command" type="hidden" value="getExhibitionsCommand">
                                <input name="sortType" type="hidden" value="sortByHall">
                                <input name="hallId" type="hidden" value=${hall.id}>
                                <button class="btn-success" type="submit"><fmt:message
                                        key='index.exhibition.byHall'/></button>
                            </form>
                        </div>
                    </div>
                </div>
                <c:set var="i" value="${i+1}"/>
            </c:forEach>
        </div>
    </div>

</main>

<%@include file="../template/message.jsp" %>
</body>
</html>
