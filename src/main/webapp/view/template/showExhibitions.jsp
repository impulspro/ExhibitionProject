<div class="container mt-3">
    <c:if test="${sessionScope.exhList != null}">
        <c:set var="i" value="1"/>
        <c:forEach var="exh" items="${sessionScope.exhList}">
            <div class="d-flex justify-content-md-center">

                <div class="col-6 card align-content-center text-center bg-info">
                    <div class="card-header bg-light">
                        <fmt:message key='exhibition.from'/>: <strong> ${exh.startDate} </strong>
                        <fmt:message key='exhibition.to'/>: <strong> ${exh.endDate} </strong>
                        <fmt:message key='exhibition.price'/> <strong> = ${exh.price}$</strong>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">${exh.theme}</h5>
                        <p class="card-text">${exh.details}</p>
                        <p> <strong> ${exh.startTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS)} </strong>
                            <fmt:message key='exhibition.to'/>
                            <strong> ${exh.endTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS)} </strong>
                        </p>
                        <strong><fmt:message key='exhibition.halls'/>:</strong>
                        <c:forEach var="hall" items="${exh.getHalls()}">
                            ${hall.name}
                        </c:forEach>
                    </div>
                    <div class="card-footer align-items-center">
                        <div class="d-flex justify-content-md-center">
                            <div class="row">

                                <c:if test="${sessionScope.user.role == null}">
                                    <a class="btn btn-primary"
                                       href="${pageContext.request.contextPath}/view/page/registration.jsp">
                                        <fmt:message key='exhibition.registryForBuying'/> <span class="sr-only">(current)</span></a>
                                </c:if>

                                <c:if test="${sessionScope.user.role == 'user'}">
                                    <form action="${pageContext.request.contextPath}/index-servlet"
                                          method="post">
                                        <input name="command" type="hidden" value="buyTicketCommand">
                                        <input name="exhibitionId" type="hidden" value="${exh.id}">

                                        <c:if test="${sessionScope.user.isTicketPresent(exh.id)}">
                                            <button class="btn btn-success" type="button" disabled >
                                                <fmt:message key='exhibition.alreadyBought'/>
                                            </button>
                                        </c:if>

                                        <c:if test="${exh.price != '-1'}">
                                            <c:if test="${!sessionScope.user.isTicketPresent(exh.id)}">
                                                <button class="btn btn-primary" type="submit">
                                                    <fmt:message key='exhibition.buyTickets'/></button>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${exh.price == '-1'}">
                                            <button class="btn btn-dark" disabled type="button">
                                                <fmt:message key='exhibition.alreadyCanceled'/></button>
                                        </c:if>
                                    </form>
                                </c:if>

                                <c:if test="${sessionScope.user.role == 'admin'}">
                                    <form action="${pageContext.request.contextPath}/index-servlet"
                                          method="post">
                                        <input name="command" type="hidden" value="cancelExhibitionCommand">
                                        <input name="exhibitionId" type="hidden" value="${exh.id}">

                                        <button class="btn-success" type="button">
                                                ${exh.amountOfTickets()} <fmt:message
                                                key='exhibition.amountOfTickets'/>
                                        </button>

                                        <c:if test="${exh.price != '-1'}">
                                            <button class="btn btn-warning" type="submit">
                                                <fmt:message key='exhibition.cancel'/>
                                            </button>
                                        </c:if>
                                        <c:if test="${exh.price == '-1'}">
                                            <button class="btn btn-dark disabled" disabled type="button">
                                                <fmt:message key='exhibition.alreadyCanceled'/></button>
                                        </c:if>

                                    </form>
                                    <form action="${pageContext.request.contextPath}/index-servlet"
                                          method="post">
                                        <input name="command" type="hidden" value="deleteExhibitionCommand">
                                        <input name="exhibitionId" type="hidden" value="${exh.id}">
                                        <button class="btn btn-danger" type="submit">
                                            <fmt:message key='exhibition.delete'/>
                                        </button>
                                    </form>
                                </c:if>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <c:set var="i" value="${i+1}"/>
        </c:forEach>
    </c:if>

    <div class="d-flex justify-content-md-center">
        <nav class="align-content-center align-items-center">
            <ul class="pagination">
                <c:forEach var="i" begin="1" end="${sessionScope.noOfPages}">
                    <li class="page-item"><a class="page-link"
                                             href="${pageContext.request.contextPath}/index-servlet?command=getExhibitionsCommand&currentPage=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>