<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%pageContext.setAttribute("newLine", "\n");%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/add" method="post">
		<table border="1">
			<tr>
				<td>이름</td>
				<td><input type="text" name="name"></td>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td colspan=4>
					<textarea name="contents" cols=60 rows=5></textarea>
				</td>
			</tr>
			<tr>
				<td colspan=4 align=right>
					<input type="submit" value="등록">
				</td>
			</tr>
		</table>
	</form>
	
		<br>
		<c:forEach items="${list}" var="vo" varStatus="status">			
			<table width=510 border=1>
				<tr>
					<td>[${status.index + 1}]</td>
					<td>${vo.name}></td>
					<td><${vo.regDate}</td>
					<td><a href="${pageContext.request.contextPath}/delete/${vo.id}">삭제</a></td>
				</tr>
				<tr>
					<td colspan=4>${fn:replace(vo.contents, newLine, "<br>")}</td>
				</tr>
			</table>
		</c:forEach>
</body>
</html>