<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<a href="Lindex.jsp?l=guj">Gujarati</a><br>
	<a href="Lindex.jsp?l=hi">Hindi</a><br>
	<a href="Lindex.jsp">English</a><br>
	
	<fm:setLocale value="${param.l}" />
	
	<fm:setBundle basename="m" var="i"/>
	
	<fm:message bundle="${i}" key="fn" /> <input type="text"><br><br>
	
	<fm:message bundle="${i}" key="ln" /> <input type="text"><br>
	
</body>
</html>