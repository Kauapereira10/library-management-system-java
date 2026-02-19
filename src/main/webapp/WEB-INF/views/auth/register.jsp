<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fazer Cadastro</title>
</head>
<body>
	<jsp:include page="../includes/header.jsp" />
	<jsp:include page="../includes/nav-bar.jsp" />
	
	<h1>Fazer o Cadastro</h1>
	
	<form method="post" action="${pageContext.request.contextPath}/users/register">
		<input type="text" name=full_name placeholder="Nome Completo" required>
		<input type="text" name="nick_name" placeholder="Usuário" required>
		<input type="email" name="email" placeholder="Email" required>
		<input type="text" name="password" placeholder="Senha" required>
		<button type="submit">Cadastrar</button>
	</form>

	<jsp:include page="../includes/footer.jsp" />
</body>
</html>