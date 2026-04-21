<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>
<nav>

	<!-- SEMPRE VISIVEL -->
	<a href="${pageContext.request.contextPath}/home">Início</a>
	<a href="${pageContext.request.contextPath}/books">Catálogo</a>

	<!-- NÃO LOGADO -->
	<c:if test="${ empty sessionScope.user }">
		<a href="${pageContext.request.contextPath}/users/register">Cadastrar</a>
		<a href="${pageContext.request.contextPath}/users/login">Login</a>
	</c:if>
		
	 <!-- LOGADO (User e Admin) -->	
	<c:if test="${ not empty sessionScope.user }">
		<a href="${pageContext.request.contextPath}/loans">Meus Empréstimos</a>
	</c:if>
	
	<!-- SOMENTE ADMIN -->
	<c:if test="${ not empty sessionScope.user and sessionScope.user.role == 'Admin'}">
		<a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
		<a href="${pageContext.request.contextPath}/admin/books">Livros</a>
		<a href="${pageContext.request.contextPath}/admin/books/new">Novo Livro</a>
	</c:if>
	
	 <!-- LOGADO (User e Admin) -->	
	<c:if test="${ not empty sessionScope.user }">
		<a href="${pageContext.request.contextPath}/users/profile">Perfil</a>
		<a href="${pageContext.request.contextPath}/users/logout">Sair</a>
	</c:if>
	
	
</nav>
</div>