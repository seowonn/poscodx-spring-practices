<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>${'Hello World: JSP(JSTL/EL) View' }</h1>
    
    <div style="padding-top: 20px">
        <a href="${pageContext.request.contextPath }/ex01">return to index</a>
    </div>
</body>
</html>
