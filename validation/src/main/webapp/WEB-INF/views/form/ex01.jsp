<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bean Validation</title>
</head>
<body>
<h1>Bean Validation</h1>
<form action="${pageContext.request.contextPath }/ex01" method="post">
	<label for="name">name:</label>
	<input type="text" id="name" name="name" value="${user.name }">
	<spring:hasBindErrors name="user">
		<!-- hasFieldErrors를 통해 특정 필드에 에러가 있는지 확인한다. -->
		<c:if test='${errors.hasFieldErrors("name") }'>
			<!-- getFieldError: 특정 필드의 에러 정보를 가져온다. -->
			${errors.getFieldError("name").defaultMessage }
		</c:if>
	</spring:hasBindErrors>
	<br><br>

	<label for="email">email:</label>
	<input type="text" id="email" name="email" value="${user.email }">
	<spring:hasBindErrors name="user">
		<c:if test='${errors.hasFieldErrors("email") }'>
			${errors.getFieldError("email").defaultMessage }
		</c:if>
	</spring:hasBindErrors>
	<br><br>

	<label for="password">password:</label>
	<input type="password" id="password" name="password" value="">
	<spring:hasBindErrors name="user">
		<c:if test='${errors.hasFieldErrors("password") }'>
			${errors.getFieldError("password").defaultMessage }
		</c:if>
	</spring:hasBindErrors>
	<br><br>

	<input type="submit" value="sign up">
</form>
</body>
</html>