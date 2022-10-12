<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-info">
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <form class="nav-link" action="${pageContext.request.contextPath}/index-servlet" method="get">
                        <input name="command" type="hidden" value="home_command">
                        <button class="btn btn-info">Home</button>
                    </form>
                </li>
                <li class="nav-item">
                    <form class="nav-link" action="${pageContext.request.contextPath}/index-servlet" method="get">
                        <input name="command" type="hidden" value="getHalls_command">
                        <button type="submit" class="btn btn-info"><fmt:message key='index.hall.get'/></button>
                    </form>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Pricing</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="header.language" />
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item"  href="?lang=en">EN</a>
                        <a class="dropdown-item"  href="?lang=ru">RU</a>
                    </div>
                </li>
            </ul>
        </div>










        <form action="${pageContext.request.contextPath}/index-servlet" method="get">
            <input name="command" type="hidden" value="home_command">
            <button class="btn btn-info">Home</button>
        </form>

        <form action="${pageContext.request.contextPath}/index-servlet" method="get">
            <input name="command" type="hidden" value="getHalls_command">
            <button type="submit" class="btn btn-info"><fmt:message key='index.hall.get'/></button>
        </form>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="header.language" />
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item"  href="?lang=en">EN</a>
                        <a class="dropdown-item"  href="?lang=ru">RU</a>
                    </div>
                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/view/page/registration.jsp">Registration<span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <div class="login-container">
                    <form action="${pageContext.request.contextPath}/index-servlet" method="post">
                        <input name="command" type="hidden" value="login_command">
                        <input type="text" placeholder="<fmt:message key='topnav.input.login'/>" name="login">
                        <input type="password" placeholder="<fmt:message key='topnav.input.password'/>" name="password">
                        <button type="submit"><fmt:message key='topnav.button.login'/></button>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div class="login-container">
                    <form action="${pageContext.request.contextPath}/index-servlet" method="get">
                        <input name="command" type="hidden" value="logout_command">
                        <button type="submit"><fmt:message key='topnav.button.logOut'/></button>
                    </form>
                    <form action="${pageContext.request.contextPath}/index-servlet" method="get">
                        <input name="command" type="hidden" value="account_command">
                        <button type="submit"> Account Command </button>
                    </form>
                    <p>Balance = ${sessionScope.user.money}</p>
                    <div class="logged_user"><fmt:message key='topnav.info.loggedAs'/> ${sessionScope.user.login}</div>
                </div>
            </c:otherwise>
        </c:choose>
    </nav>

</header>
