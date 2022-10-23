<%@ page import="com.exhibit.model.Hall" %>
<%@ page import="java.util.List" %>
<%@ page import="com.exhibit.services.HallService" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="text"/>

<html lang="${param.lang}">
<head>
    <title>Exhibition</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%@include file="/view/template/styles.jsp" %>

</head>
<body>

<%@include file="/view/template/header.jsp" %>
<%
    List<Hall> hallList = new HallService().findAll();
    session.setAttribute("hallList", hallList);
%>
<main>

    <div class="container-fluid d-flex h-100 justify-content-center align-items-center p-0">

        <div class="row bg-white shadow-sm">

            <div class="col border rounded p-4 bg-info">

                <form name="addExhForm" action="${pageContext.request.contextPath}/index-servlet" method="post"
                      onsubmit="return validator()">

                    <legend><fmt:message key='addExhibition.topic'/> <tf:tagdate/></legend>


                    <input name="command" type="hidden" value="addExhibition_command">

                    <label for="themeId"><fmt:message key='addExhibition.form.theme'/></label>
                    <br>
                    <input type="text" name="theme" id="themeId" required
                           placeholder="<fmt:message key='addExhibition.form.enterTheme'/>">
                    <br>
                    <label for="detailsId"><fmt:message key='addExhibition.form.details'/></label>
                    <br>
                    <textarea name="details" id="detailsId" required> <fmt:message
                            key='addExhibition.form.enterDetails'/></textarea>
                    <br>

                    <input type="date" name="startDate" id="startDateId" required>
                    <label for="startDateId"><fmt:message key='addExhibition.form.startDate'/></label>
                    <br>
                    <input type="date" name="endDate" id="endDateId" required>
                    <label for="endDateId"><fmt:message key='addExhibition.form.endDate'/></label>

                    <br>
                    <input type="time" name="startTime" id="startTimeId" required value="09:00">
                    <label for="startTimeId"><fmt:message key='addExhibition.form.startTime'/></label>

                    <br>
                    <input type="time" name="endTime" id="endTimeId" required value="21:00">
                    <label for="endTimeId"><fmt:message key='addExhibition.form.endTime'/></label>

                    <div class="form-group">
                        <label for="hallsId"><fmt:message key='addExhibition.form.chooseHalls'/></label>
                        <select name="halls_id" multiple class="form-control" id="hallsId" required>
                            <c:forEach items="${hallList}" var="hall">
                                <option value="${hall.id}">${hall.name}</option>
                                <br>
                            </c:forEach>
                        </select>
                    </div>

                    <label for="priceId"><fmt:message key='addExhibition.form.price'/></label> <br>
                    <input type="number" name="price" id="priceId" required step="50" placeholder="0.00" min="0">


                    <button class="btn-success align-content-center" type="submit"><fmt:message
                            key='addExhibition.topic'/></button>

                </form>
            </div>
        </div>
    </div>
</main>
<%@include file="/view/template/message.jsp" %>

</body>
<script>
    const dateInput = document.getElementById('startDateId');
    const dateInput2 = document.getElementById('endDateId');

    // ✅ Using the visitor's timezone
    dateInput.value = formatDate();
    dateInput2.value = formatDate();

    console.log(formatDate());

    function padTo2Digits(num) {
        return num.toString().padStart(2, '0');
    }

    function formatDate(date = new Date()) {
        return [
            date.getFullYear(),
            padTo2Digits(date.getMonth() + 1),
            padTo2Digits(date.getDate()),
        ].join('-');
    }
</script>
<script>
    function validator() {
        const theme = document.getElementById("themeId").value;
        const details = document.getElementById("detailsId").value;
        const startDate = document.getElementById("startDateId").value;
        const endDate = document.getElementById("endDateId").value;
        const startTime = document.getElementById("startTimeId").value;
        const endTime = document.getElementById("endTimeId").value;
        const price = document.getElementById("priceId").value;
        const erTheme = "<fmt:message key='addExhibition.form.errorTheme'/>";
        const erDetails = "<fmt:message key='addExhibition.form.errorDetails'/>";
        const erDate = "<fmt:message key='addExhibition.form.errorDate'/>";
        const erTime = "<fmt:message key='addExhibition.form.errorTime'/>";
        const erDatePast = "<fmt:message key='addExhibition.form.errorDatePast'/>";
        const today = formatDate();
        const erPrice = "<fmt:message key='addExhibition.form.errorPrice'/>";

        if (theme.length < 5 || theme.length > 255) {
            alert(erTheme);
            return false;
        }
        if (details.length < 5 || details.length > 1024) {
            alert(erDetails);
            return false;
        }
        if (startDate > endDate) {
            alert(erDate);
            return false;
        }

        if (startTime > endTime) {
            alert(erTime);
            return false;
        }
        if (startDate < today || endDate < today ) {
            alert(erDatePast);
            return false;
        }
        if (price == null || price < 0 || price > 1000) {
            alert(erPrice);
            return false;
        }
    }
</script>
</html>