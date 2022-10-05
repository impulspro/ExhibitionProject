<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="index.jsp">Home</a>

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
            </ul>
        </div>

    </nav>
    <c:choose>
        <c:when test="${sessionScope.user == null}">
            <div class="login-container">
                <form action="${pageContext.request.contextPath}/index-servlet" method="get">
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
                <div class="logged_user"><fmt:message key='topnav.info.loggedAs'/> ${sessionScope.user.login}</div>
            </div>
        </c:otherwise>
    </c:choose>
    <a href="${pageContext.request.contextPath}/registration.jsp"><fmt:message key='topnav.menu.registration'/></a>

    <p>Login: ${user.login}</p>
    <p>Login: ${sessionScope.user.login}</p>
    <p>Tom: ${sessionScope.tom}</p>
    <p>UserLogin: ${sessionScope.user_login}</p>
    </div>
 </header>
