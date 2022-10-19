<%@ page import="com.exhibit.model.Hall" %>
<%@ page import="java.util.List" %>
<%@ page import="com.exhibit.services.HallService" %>
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
<%
    List<Hall> hallList = new HallService().findAll();
    session.setAttribute("hallList", hallList);
%>
<main>

        <div class="container col-2 d-flex justify-content-center btn-info">
            <form action="${pageContext.request.contextPath}/index-servlet" method="post">
                <fieldset>

                    <legend class="align-content-center"><fmt:message key='addExhibition.topic'/></legend>

                    <input name="command" type="hidden" value="addExhibition_command">

                    <label><fmt:message key='addExhibition.form.theme'/></label>
                    <br>
                    <input type="text" name="theme" placeholder="<fmt:message key='addExhibition.form.enterTheme'/>">
                    <br>
                    <label><fmt:message key='addExhibition.form.details'/></label>
                    <br>
                    <textarea name="details"> <fmt:message key='addExhibition.form.enterDetails'/>"> </textarea>
                    <br>

                    <input type="date" name="startDate">
                    <label><fmt:message key='addExhibition.form.startDate'/></label>
                    <br>
                    <input type="date" name="endDate" value="">
                    <label><fmt:message key='addExhibition.form.endDate'/></label>

                    <br>
                    <input type="time" name="startTime" value="09:00">
                    <label><fmt:message key='addExhibition.form.startTime'/></label>

                    <br>
                    <input type="time" name="endTime" value="21:00">
                    <label><fmt:message key='addExhibition.form.endTime'/></label>

                    <div class="form-group">
                        <label for="exampleFormControlSelect2"><fmt:message key='addExhibition.form.chooseHalls'/></label>
                        <select name="halls_id" multiple class="form-control" id="exampleFormControlSelect2">
                            <c:forEach items="${hallList}" var="hall">
                                <option value="${hall.id}">${hall.name}</option>
                                <br>
                            </c:forEach>
                        </select>
                    </div>

                    <label><fmt:message key='addExhibition.form.price'/></label> <br>
                    <input type="number" name="price" step="50" placeholder="0.00" min="0">

                </fieldset>

                <button class="btn-success align-content-center" type="submit"><fmt:message key='addExhibition.topic'/></button>

            </form>
    </div>

</main>
<%@include file="/view/template/message.jsp" %>

</body>
</html>