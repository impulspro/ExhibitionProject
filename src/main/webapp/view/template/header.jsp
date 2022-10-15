<nav class="navbar sticky-top navbar-light bg-light">

    <div class="container-fluid">
        <form action="${pageContext.request.contextPath}/index-servlet" method="get">
            <input name="command" type="hidden" value="home_command">
            <button class="btn btn-info">Home</button>
        </form>
        <form action="${pageContext.request.contextPath}/index-servlet" method="get">
            <input name="sortType" type="hidden" value="sort">
            <input name="command" type="hidden" value="getExhibitions_command">
            <button class="btn btn-success" type="submit"><fmt:message key='index.exhibition.byDefault'/></button>
        </form>
        <form class="nav-link" action="${pageContext.request.contextPath}/index-servlet" method="get">
            <input name="command" type="hidden" value="getHalls_command">
            <button type="submit" class="btn btn-warning"><fmt:message key='index.hall.get'/></button>
        </form>


        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group mr-2" role="group" aria-label="First group">
                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                        <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet" method="post">
                            <input name="command" type="hidden" value="login_command">
                            <input type="text" placeholder="<fmt:message key='topnav.input.login'/>" name="login">
                            <input type="password" placeholder="<fmt:message key='topnav.input.password'/>" name="password">
                            <button class="btn-success" type="submit"><fmt:message key='topnav.button.login'/></button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet" method="get">
                            <button class="btn-info" disabled>${sessionScope.user.login}</button>
                            <input name="command" type="hidden" value="logout_command">
                            <button class="btn-success" type="submit"><fmt:message key='topnav.button.logOut'/></button>
                            <button class="btn-info" disabled>${sessionScope.user.money}$</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="btn-group mr-2" role="group" aria-label="Second group">
                <a class="btn btn-light"  href="?lang=en">EN</a>
                <a class="btn btn-light"  href="?lang=ua">UA</a>
            </div>
            <div class="btn-group" role="group" aria-label="Third group">
                <a class="btn btn-dark" href="${pageContext.request.contextPath}/view/page/registration.jsp">Registration<span class="sr-only">(current)</span></a>
            </div>
        </div>
    </div>

</nav>