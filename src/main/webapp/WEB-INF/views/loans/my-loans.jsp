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
		
		<c:if test="${empty loans}">
			<p>>Você ainda não possui livros vinculados.</p>
		</c:if>
		
		<c:if test="${not empty loans}">
			
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
						<c:forEach var="loan" items="${loans}">
							<tr>
								<td>${loan.book.title}</td>
								<td>${loan.book.author}</td>
								<td>${loan.book.isbn}</td>
								<td>
									<c:choose>
										<c:when test="${loan.book.available}">
											Disponível
										</c:when>
										<c:otherwise>
											Emprestado
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<a href="${pageContext.request.contextPath}/loans/return?id=${loan.id}">
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