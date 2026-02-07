<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogos</title>
</head>
<body>
	<jsp:include page="../includes/header.jsp" />
	<jsp:include page="../includes/nav-bar.jsp" />

	<main>

		<h1>Catalogos dos Livros</h1>
		<div class="table-container">
			<table border="1" >
				<thead>
					<tr>
						<th>Numero Unico</th>
						<th>Titulo</th>
						<th>Autor</th>
						<th>ISBN</th>
						<th>Status</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="book" items="${books}">
					<tr>
						<td>${book.id}</td>
						<td>${book.getTitle}</td>
						<td>${book.getAuthor}</td>
						<td>${book.getIsbn}</td>
						<td>${book.isAvailable}</td>
						<td>${book.isActive}</td>
						<a href="${pageContext.request.contextPath}/books/details?id=${book.id}">Acessar</a>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>

	</main>

	<jsp:include page="../includes/footer.jsp" />
</body>
</html>