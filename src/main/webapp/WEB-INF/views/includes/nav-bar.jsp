<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>
<nav>
    <a href="${pageContext.request.contextPath}/">Início</a>
    <a href="${pageContext.request.contextPath}/books">Catálogo</a>
    <a href="${pageContext.request.contextPath}/loans">Meus Empréstimos</a>	
    <a href="${pageContext.request.contextPath}/loans?action=borrow">Emprestar Livro</a>

    

	<c:if test="${not empty sessionScope.user and sessionScope.user.role == 'Admin'}">
		<a href="${pageContext.request.contextPath}/admin">Dashboard</a>
		<a href="${pageContext.request.contextPath}/admin/books">Livros</a>
		<a href="${pageContext.request.contextPath}/admin/books/new">Novo Livro</a>
	</c:if>
	
	
	<!-- MOSTRA SE NÃO ESTIVER LOGADO -->
    <c:if test="${empty sessionScope.user}">
        <a href="${pageContext.request.contextPath}/users/register">Cadastrar</a>
        <a href="${pageContext.request.contextPath}/users/login">Login</a>
    </c:if>

    <!-- MOSTRA SE ESTIVER LOGADO -->
    <c:if test="${not empty sessionScope.user}">
        <a href="${pageContext.request.contextPath}/users/profile">Perfil</a>
        <a href="${pageContext.request.contextPath}/users/logout">Sair</a>
    </c:if>
</nav>
</div>