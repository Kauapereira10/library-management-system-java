<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Livros</title>
</head>
<body>
	
	<jsp:include page="../includes/header.jsp" />
	<jsp:include page="../includes/nav-bar.jsp" />
	
	<h1>Cadastrar livro</h1>
	
	<form action="${pageContext.request.contextPath}/admin/books/new" method="post">

    <input type="text" name="title" placeholder="Título do Livro" required>
    <input type="text" name="author" placeholder="Autor do Livro" required>
    <input type="text" name="isbn" placeholder="ISBN" required>

    <button type="submit">Cadastrar</button>

</form>
	
	<jsp:include page="../includes/footer.jsp" />
</body>
</html>