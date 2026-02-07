<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Empréstimos</title>
</head>
<body>
	<jsp:include page="../includes/header.jsp" />
	<jsp:include page="../includes/nav-bar.jsp" />

	<main>
		<h1>Empréstimos</h1>

		<c:if test="empty loans">
			<p>Nenhum empréstimo encontrado.</p>
		</c:if>
		
		<c:if test="not empty loans">
			<table border="1">
				<thead>
					<tr>
						<th>ID</th>
						<th>Livro</th>
						<th>Usuário</th>
						<th>Data Empréstimo</th>
						<th>Data Devolução</th>
						<th>Status</th>
					</tr>
				</thead>	
				<tbody>
					<c:forEach var="loan" items="loans">
						<tr>
							<td>${loan.id}</td>
							<td>${loan.book.title}</td>
							<td>${loan.user.name}</td>
							<td>${loan.loanDate}</td>
							<td>${loan.returnDate}</td>
							<td>
								<c:choose>
									<c:when test="${loan.active}">
										Ativo
									</c:when>
									<c:otherwise>
										Inativo
									</c:otherwise>								
								</c:choose>
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