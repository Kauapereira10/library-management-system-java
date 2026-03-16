<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalhes do Livro</title>
</head>
<body>
	<jsp:include page="../includes/header.jsp" />
	<jsp:include page="../includes/nav-bar.jsp" />

	<main>
		
		<div class="book-container">
			<h2>Detalhes do Livro</h2>
			
			<p><strong>Título:</strong> ${books.title}</p>
			<p><strong>Autor:</strong> ${books.author}</p>
			<p><strong>ISBN:</strong> ${books.isbn}</p>
			<p><strong>Status:</strong>
			  <c:choose>
			    <c:when test="${books.available}">
			    	Disponível
			    </c:when>
			    <c:otherwise>
			    	Reservado
			    </c:otherwise>
			  </c:choose>
			</p>
			<p><strong>Ativo:</strong> 
			<c:choose>
			<c:when test="${books.active}">
			    	Sim
			    </c:when>
			    <c:otherwise>
			    	Não
			    </c:otherwise>
			  </c:choose>
			</p>
			
		</div>
		
		<c:if test="${books.available}">
			<form method="post" action="${pageContext.request.contextPath}/loans/borrow">
				<input type="hidden" name="bookId" value="${books.id}">
				<button type="submit">Reservar Livro</button>
			</form>
		</c:if>
		
		<c:if test="${!books.available}">
			<p>Este livro já está reservado.</p>
		</c:if>
		
		
		<a href="${pageContext.request.contextPath}/books">Voltar</a>
		
	</main>
	
	<jsp:include page="../includes/footer.jsp" />
</body>
</html>