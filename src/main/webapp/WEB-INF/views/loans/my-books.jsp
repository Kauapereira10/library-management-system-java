<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Meus Livros</title>
</head>
<body>
	<jsp:include page="../includes/header.jsp" />
	<jsp:include page="../includes/nav-bar.jsp" />
	
	<main>
		<h1>Meus Livros</h1>
		
		<c:if test="${empty books}">
			<p>>Você ainda não possui livros vinculados.</p>
		</c:if>
		
		<c:if test="${not empty books}">
			
			<table>
				<thead>
					<tr>
						<th>Título</th>
						<th>Autor</th>
						<th>ISBN</th>
						<th>Status</th>
						<th>Ações</th>
					</tr>
				</thead>
				
				<tbody>
						<c:forEach var="book" items="books">
							<tr>
								<td>${book.title}</td>
								<td>${book.author}</td>
								<td>${book.isbn}</td>
								<td>
									<c:choose>
										<c:when test="${book.available}">
											Disponível
										</c:when>
										<c:otherwise>
											Emprestado
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<a href="${pageContext.request.contextPath}/books/return?id=${book.id}">
										 Devolver
									</a>
								</td>
							</tr>
						</c:forEach>
				</tbody>
			</table>
			
		</c:if>
	</main>
	
	<jsp:include page="../includes/footer.jsp" />
</body>
</html>