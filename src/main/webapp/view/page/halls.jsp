<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="text" />

<html lang="${param.lang}">
<head>
    <title>Exhibition</title>

    <%@include file="../template/styles.jsp" %>
</head>
<body>
        <!-- Single item -->
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">Next</a></li>
            </ul>
        </nav>


            <div class="container">
                <div class="row">
                    <div class="col-lg-4">
                        <div class="card">
                            <img
                                    src="../img/hall_red.jpg"
                                    class="card-img-top"
                                    alt="Red Hall"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <p class="card-text">

                                </p>
                                <a href="#!" class="btn btn-primary">Button</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4 d-none d-lg-block">
                        <div class="card">
                            <img
                                    src="https://mdbcdn.b-cdn.net/img/new/standard/nature/182.webp"
                                    class="card-img-top"
                                    alt="Sunset Over the Sea"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <p class="card-text">
                                    Some quick example text to build on the card title and make up the bulk
                                    of the card's content.
                                </p>
                                <a href="#!" class="btn btn-primary">Button</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4 d-none d-lg-block">
                        <div class="card">
                            <img
                                    src="https://mdbcdn.b-cdn.net/img/new/standard/nature/183.webp"
                                    class="card-img-top"
                                    alt="Sunset over the Sea"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <p class="card-text">
                                    Some quick example text to build on the card title and make up the bulk
                                    of the card's content.
                                </p>
                                <a href="#!" class="btn btn-primary">Button</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        <!-- Single item -->

            <div class="container">
                <div class="row">
                    <div class="col-lg-4 col-md-12">
                        <div class="card">
                            <img
                                    src="https://mdbcdn.b-cdn.net/img/new/standard/nature/184.webp"
                                    class="card-img-top"
                                    alt="Fissure in Sandstone"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <p class="card-text">
                                    Some quick example text to build on the card title and make up the bulk
                                    of the card's content.
                                </p>
                                <a href="#!" class="btn btn-primary">Button</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4 d-none d-lg-block">
                        <div class="card">
                            <img
                                    src="https://mdbcdn.b-cdn.net/img/new/standard/nature/185.webp"
                                    class="card-img-top"
                                    alt="Storm Clouds"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <p class="card-text">
                                    Some quick example text to build on the card title and make up the bulk
                                    of the card's content.
                                </p>
                                <a href="#!" class="btn btn-primary">Button</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4 d-none d-lg-block">
                        <div class="card">
                            <img
                                    src="https://mdbcdn.b-cdn.net/img/new/standard/nature/186.webp"
                                    class="card-img-top"
                                    alt="Hot Air Balloons"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <p class="card-text">
                                    Some quick example text to build on the card title and make up the bulk
                                    of the card's content.
                                </p>
                                <a href="#!" class="btn btn-primary">Button</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        <!-- Single item -->

            <div class="container">
                <div class="row">
                    <div class="col-lg-4 col-md-12 mb-4 mb-lg-0">
                        <div class="card">
                            <img
                                    src="https://mdbcdn.b-cdn.net/img/new/standard/nature/187.webp"
                                    class="card-img-top"
                                    alt="Peaks Against the Starry Sky"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <p class="card-text">
                                    Some quick example text to build on the card title and make up the bulk
                                    of the card's content.
                                </p>
                                <a href="#!" class="btn btn-primary">Button</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4 mb-4 mb-lg-0 d-none d-lg-block">
                        <div class="card">
                            <img
                                    src="https://mdbcdn.b-cdn.net/img/new/standard/nature/188.webp"
                                    class="card-img-top"
                                    alt="Bridge Over Water"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <p class="card-text">
                                    Some quick example text to build on the card title and make up the bulk
                                    of the card's content.
                                </p>
                                <a href="#!" class="btn btn-primary">Button</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4 mb-4 mb-lg-0 d-none d-lg-block">
                        <div class="card">
                            <img
                                    src="https://mdbcdn.b-cdn.net/img/new/standard/nature/189.webp"
                                    class="card-img-top"
                                    alt="Purbeck Heritage Coast"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <p class="card-text">
                                    Some quick example text to build on the card title and make up the bulk
                                    of the card's content.
                                </p>
                                <a href="#!" class="btn btn-primary">Button</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

<main>
  <%--  <table align="center" class="exhibitions" cellspacing="9">
        <c:forEach var="hall" items="${hallList}">

            <tr>
                <td><b>${hall.name}</b><br>
                        ${hall.details} <br>
                </td>
            </tr>

            <c:set var="i" value="${i+1}"/>

        </c:forEach>

    </table>
    --%>
    <!-- Carousel wrapper -->

</main>

<%@include file="../template/message.jsp" %>

</body>
</html>
