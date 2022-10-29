<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE><html lang="uk">
<head>

    <!-- Required meta tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css"/>"/>
    <!-- Icons -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">

    <%--    <script src="https://code.jquery.com/jquery-3.1.1.min.js"--%>
    <%--            integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=" crossorigin="anonymous"></script>--%>
    <script type="text/javascript" src="<c:url value="/js/jquery/jquery-3.1.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery/jquery-ui.min.js"/>"></script>
    <%--    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"--%>
    <%--            integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU=" crossorigin="anonymous"></script>--%>

    <script type="text/javascript" src="<c:url value="/js/jquery/popper.min.js"/>"></script>
    <%--    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"--%>
    <%--            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"--%>
    <%--            crossorigin="anonymous"></script>--%>

    <script type="text/javascript" src="<c:url value="/js/jquery/bootstrap.min.js"/>"></script>
    <%--    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"--%>
    <%--            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"--%>
    <%--            crossorigin="anonymous"></script>--%>

    <script type="text/javascript" src="<c:url value="/js/dist/pagination.js"/>"></script>

</head>

<body class="d-flex flex-column">
<div class="flex-grow-1 flex-shrink-0">

<header>
    <c:import url="/WEB-INF/view/template/header.jsp"/>
</header>
<main>
    <c:import url="${showPage}"/>
</main>
</div>
</body>

<footer id="sticky-footer" class="py-4 bg-dark text-white-50 flex-shrink-0">
    <c:import url="/WEB-INF/view/template/message.jsp"/>
</footer>

</html>
