<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
</head>
<body>

<form:form action="/update" method="post" modelAttribute="course">
	<form:input type="hidden" path="id"/>
	<label>Name:
		<form:input path="name"/>
		<form:errors path="name"/>
	</label><br>
	<label>Instructor:
		<form:input path="instructor"/>
		<form:errors path="instructor"/>
	</label><br>
	<label>Capacity:
		<form:input path="capacity"/>
		<form:errors path="capacity"/>
	</label><br>

	
	<input type="submit">
</form:form>

</body>
</html>