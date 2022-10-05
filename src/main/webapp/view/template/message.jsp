<c:if test="${sessionScope.error_message != null}">
    <script>
        alert(${sessionScope.error_message})
    </script>
    ${sessionScope.error_message = null}
</c:if>
<c:if test="${sessionScope.user_message != null}">
    <script>
        alert(${sessionScope.user_message})
    </script>
    ${sessionScope.user_message = null}
</c:if>