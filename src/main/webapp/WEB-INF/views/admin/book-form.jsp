<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulario do Livro</title>
</head>
<body>
	 
	<jsp:include page="../includes/header.jsp" />
	<jsp:include page="../includes/nav-bar.jsp" />
	
	<h1>${book != null ? "Editar Livro" : "Cadastrar Livro"}</h1>
	
	<form action="${pageContext.request.contextPath}/admin/${book != null ? 'books/update' : 'books/new'}" method="post">

	<input type="hidden" name="id" value="${book.id}"/>

    <input type="text" name="title" placeholder="Título do Livro" value="${book.title}" required>
    <input type="text" name="author" placeholder="Autor do Livro" value="${book.author}" required>
    <input type="text" name="isbn" placeholder="ISBN" value="${book.isbn}" required>

    <button type="submit">${book != null ? "Editar Livro" : "Cadastrar Livro"}</button>

</form>
	
	<jsp:include page="../includes/footer.jsp" />
</body>
</html>