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
<form:form 
	modelAttribute="user" 
	action="${pageContext.request.contextPath }/ex03" 
	method="post">
	<label for="name">name:</label>
	<form:input path="name" />
	<p style="padding: 5px 0; margin:0; color:#f00">	
		<form:errors path="name" />
	</p>

	<label for="email">email:</label>
	<form:input path="email" />
	<p style="padding: 5px 0; margin:0; color:#f00">	
		<form:errors path="email" />
	</p>

	<label for="password">password:</label>
	<form:password path="password" />
	<p style="padding: 5px 0; margin:0; color:#f00">	
		<form:errors path="password" />
	</p>

	<input type="submit" value="sign up">
</form:form>
</body>
</html>