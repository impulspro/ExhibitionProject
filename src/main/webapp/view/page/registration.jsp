<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html lang="uk">
<head>
    <title>Exhibition</title>
    <%@include file="../template/styles.jsp" %>

</head>
<body>

<%@include file="../template/header.jsp" %>

<main>

    <section class="vh-100" style="background-color: #eee;">

        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-lg-12 col-xl-11">
                    <div class="card text-black" style="border-radius: 25px;">
                        <div class="card-body p-md-5">
                            <div class="row justify-content-center">
                                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                                    <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4"><fmt:message
                                            key='registration.form.header'/></p>

                                    <form name = "regForm" action="${pageContext.request.contextPath}/index-servlet" method="post"  onsubmit="return validator()"  >
                                        <div class="d-flex flex-row align-items-center mb-4">

                                            <div class="form-outline flex-fill mb-0">
                                                <input type="text" name="login" required id="form3Example1c"
                                                       class="form-control"/>
                                                <label class="form-label" for="form3Example1c"><fmt:message
                                                        key='registration.form.enterLogin'/></label>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center mb-4">

                                            <div class="form-outline flex-fill mb-0">
                                                <input type="password" name="password"  required id="form3Example1cc"
                                                       class="form-control"/>
                                                <label class="form-label" for="form3Example1cc"><fmt:message
                                                        key='registration.form.enterPassword'/></label>
                                            </div>
                                        </div>
                                        <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                            <input name="command" type="hidden" value="registrationCommand">
                                            <button type="submit" class="btn btn-primary btn-lg"><fmt:message
                                                    key='registration.form.register'/></button>
                                        </div>

                                    </form>
                                </div>
                                <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                                         class="img-fluid" alt="Sample image">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<%@include file="../template/message.jsp" %>
</body>
<script>
    function validator(){
        const login = document.regForm.login.value;
        const password = document.regForm.password.value;
        const erPass = "<fmt:message key='registration.form.errorPassword'/>";
        const erLog = "<fmt:message key='registration.form.errorLogin'/>";
        if (login==null || login==="" || login.length < 3 || login.length > 32){
            alert(erLog);
            return false;
        }else if(password.length<6){
            alert(erPass);
            return false;
        }
    }
</script>
</html>
