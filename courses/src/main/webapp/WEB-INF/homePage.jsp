<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Welcome</title>
</head>
<body>
	<h1>Welcome, <c:out value="${user.email}" /></h1>
	
	<h3>Courses</h3>
	<table border="1">
		<tr>
			<th>Course</th>
			<th>Instructor</th>
			<th>Capacity</th>
			<th>Action</th>
			<th>Students</th>
		</tr>
		<c:forEach items="${allCourses}" var="course">
			<tr>
				<td><c:out value="${course.name}"/></td>
				<td><c:out value="${course.instructor}"/></td>
				<td><c:out value="${course.capacity}"/></td>
				<td><a href="/add/${course.id}">add</a> <a href="/edit/${course.id}">edit</a></td>
				<td>
					<c:forEach items="${course.students}" var="student">
						<c:out value="${student.email}"/>
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</table>
	<a href="/logout">Logout</a>
</body>
</html>