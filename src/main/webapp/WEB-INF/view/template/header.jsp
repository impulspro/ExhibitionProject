<nav class="navbar sticky-top navbar-expand-sm navbar-light bg-light">
    <div class="mx-auto d-sm-flex d-block flex-sm-nowrap">
        <div class="collapse navbar-collapse text-center" id="navbarExample11">
            <ul class="navbar-nav">

                <li class="nav-item">
                    <button class="btn-success"
                            onclick="window.location.href='../../../view/index.jsp'">
                        <em class="fa fa-home"></em> <fmt:message key='header.home'/></button>
                </li>
                &nbsp; &nbsp; &nbsp;
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/index-servlet" method="get">
                        <input name="sortType" type="hidden" value="sortByDate">
                        <input name="command" type="hidden" value="getExhibitionsCommand">
                        <button class="btn-success" type="submit"><em class="fa fa-folder"></em>
                            <fmt:message key='header.showExhibitions'/></button>
                    </form>
                </li>
                &nbsp; &nbsp; &nbsp;
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/index-servlet" method="get">
                        <input name="command" type="hidden" value="getHallsCommand">
                        <button type="submit" class="btn-success"><em class="fa fa-folder"></em>
                            <fmt:message key='header.showHalls'/></button>
                    </form>
                </li>
                &nbsp; &nbsp; &nbsp;
                <li class="nav-item">
                    <div class="btn-group mr-2" role="group" aria-label="First group">
                        <c:choose>
                            <c:when test="${sessionScope.user == null}">
                                <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet"
                                      method="post">
                                    <input name="command" type="hidden" value="loginCommand">
                                    <label>
                                        <input type="text" placeholder="<fmt:message key='header.user.loginLabel'/>"
                                               name="login">
                                    </label>
                                    <label>
                                        <input type="password"
                                               placeholder="<fmt:message key='header.user.passwordLabel'/>"
                                               name="password">
                                    </label>
                                    <button class="btn-dark" type="submit"><fmt:message
                                            key='header.user.logIn'/></button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button class="btn-dark" disabled>${sessionScope.user.login}</button>
                                <c:if test="${sessionScope.user.role == 'user'}">
                                    <button class="btn-dark" disabled>${sessionScope.user.money}$</button>
                                    <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet"
                                          method="get">
                                        <input name="command" type="hidden" value="printPdfCommand">
                                        <button class="btn-dark" type="submit"><fmt:message
                                                key='header.printPdf'/></button>
                                    </form>
                                </c:if>

                                <form class="form-inline" action="${pageContext.request.contextPath}/index-servlet"
                                      method="get">
                                    <input name="command" type="hidden" value="logoutCommand">
                                    <button class="btn-dark" type="submit"><em class="fa fa-close"></em>
                                        <fmt:message key='header.user.logOut'/></button>
                                </form>

                            </c:otherwise>
                        </c:choose>
                    </div>
                </li>
                &nbsp; &nbsp; &nbsp;
                <li class="nav-item">
                    <div class="btn-group" role="group" aria-label="Third group">
                        <c:if test="${sessionScope.user.role == 'admin'}">
                            <button class="btn-danger"
                                    onclick="window.location.href='${pageContext.request.contextPath}/view/page/adminPanel.jsp'">
                                <fmt:message key='header.adminPanel'/><span class="sr-only">(current)</span>
                            </button>
                        </c:if>
                        <div>
                            <button class="btn-dark"
                                    onclick="window.location.href='${pageContext.request.contextPath}/view/page/registration.jsp'">
                                <fmt:message key='header.registration'/><span class="sr-only">(current)</span></button>
                        </div>
                    </div>
                </li>
                &nbsp; &nbsp; &nbsp;
                <li class="nav-item">
                    <div class="btn-group mr-2" role="group" aria-label="Second group">
                        <div>
                            <button class="btn-dark" onclick="window.location.href='?lang=uk'">UK</button>
                        </div>
                        <div>
                            <button class="btn-dark" onclick="window.location.href='?lang=en'">EN</button>
                        </div>
                    </div>
                </li>

            </ul>
        </div>
    </div>
</nav>