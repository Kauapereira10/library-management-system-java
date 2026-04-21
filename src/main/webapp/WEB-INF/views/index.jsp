<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Biblioteca Online</title>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<jsp:include page="includes/nav-bar.jsp" />
	<main>

		<!-- HERO -->
		<section class="hero">
			<h2>Bem-vindo à Biblioteca Online</h2>
			<p>Explore nosso catálogo e faça empréstimos de forma simples e
				rápida.</p>

			<div class="hero-actions">
				<a href="${pageContext.request.contextPath}/books"
					class="btn-primary"> Ver Catálogo </a>

				<c:if test="${empty sessionScope.user}">
					<a href="${pageContext.request.contextPath}/users/register"
						class="btn-secondary"> Criar Conta </a>
				</c:if>

				<c:if test="${not empty sessionScope.user}">
					<a href="${pageContext.request.contextPath}/loans"
						class="btn-secondary"> Meus Empréstimos </a>
				</c:if>
			</div>
		</section>

		<hr>

		<!-- SOBRE -->
		<section class="about">
			<h3>O que você pode fazer aqui?</h3>

			<div class="features">
				<div class="feature-card">
					<h4>📚 Explorar Livros</h4>
					<p>Navegue pelo catálogo completo e veja detalhes de cada
						livro.</p>
				</div>

				<div class="feature-card">
					<h4>🔖 Fazer Empréstimos</h4>
					<p>Reserve livros disponíveis e acompanhe seus empréstimos
						ativos.</p>
				</div>

				<div class="feature-card">
					<h4>👤 Gerenciar Perfil</h4>
					<p>Atualize seus dados pessoais a qualquer momento.</p>
				</div>
			</div>
		</section>

	</main>

	<jsp:include page="includes/footer.jsp" />

</body>
</html>