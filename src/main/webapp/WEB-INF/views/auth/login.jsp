<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login do Usuário</title>
</head>
<body>
	<jsp:include page="../includes/header.jsp" />
	<jsp:include page="../includes/nav-bar.jsp" />
	
	<h1>Fazer o Login</h1>
	
	<form method="post" action="${pageContext.request.contextPath}/user/login">
		<input type="email" name="email" placeholder="Email" required>
		<input type="password" name="password" placeholder="Senha" required>
		<button type="submit">Entrar</button>
	</form>

	<c:if test="${not empty error}">
		 <p style="color:red">${error}</p>
	</c:if>
	<jsp:include page="../includes/footer.jsp" />
</body>
</html>