<div class="container mt-3">
    <c:if test="${sessionScope.exhList != null}">
        <c:set var="i" value="1"/>
        <c:forEach var="exh" items="${sessionScope.exhList}">

            <c:set var="i" value="${i+1}"/>
        </c:forEach>
    </c:if>

</div>
