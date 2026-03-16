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
						<th>Em Estoque</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="book" items="${books}">
					<tr>
						<td>${book.id}</td>
						<td>${book.title}</td>
						<td>${book.author}</td>
						<td>${book.isbn}</td>
						<td>
						<c:choose>
					    <c:when test="${book.available}">
					    	Disponível
					    </c:when>
					    <c:otherwise>
					    	Reservado
					    </c:otherwise>
					  </c:choose>
					  </td>
						<td>
							<c:choose>
						    <c:when test="${book.active}">
						    	Sim
						    </c:when>
						    <c:otherwise>
						    	Não
						    </c:otherwise>
						  </c:choose>
						</td>
						<td><a href="${pageContext.request.contextPath}/books/details?id=${book.id}">Acessar</a></td>	
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>

	</main>

	<jsp:include page="../includes/footer.jsp" />
</body>
</html>