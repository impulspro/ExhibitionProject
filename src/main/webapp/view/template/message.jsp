<c:if test="${not empty sessionScope.error_message}">
    <script>
        const error_message = "${sessionScope.error_message}";
        alert(error_message);
    </script>
    ${sessionScope.error_message = null}
</c:if>
<c:if test="${not empty sessionScope.user_message}">
    <script>
        const user_message = "${sessionScope.user_message}";
        alert(user_message);
    </script>
    ${sessionScope.user_message = null}
</c:if>