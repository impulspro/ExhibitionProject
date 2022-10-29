<c:if test="${not empty sessionScope.errorMessage}">
    <script>
        const errorMessage = "${sessionScope.errorMessage}";
        alert(errorMessage);
    </script>
    ${sessionScope.errorMessage = null}
</c:if>
<c:if test="${not empty sessionScope.userMessage}">
    <script>
        const userMessage = "${sessionScope.userMessage}";
        alert(userMessage);
    </script>
    ${sessionScope.userMessage = null}
</c:if>