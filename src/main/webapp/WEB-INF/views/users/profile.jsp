<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Perfil</title>
</head>
<body>
	<jsp:include page="../includes/header.jsp" />
	<jsp:include page="../includes/nav-bar.jsp" />
	
	<main>
    	<div class="profile-container">
    		<section class="profile-header">
    			<h2>Bem-vindo, ${sessionScope.user.nickName}!</h2>
    			<p>Membro desde: 2026</p>
    		</section>
    		
    		<hr>
    		
    		<section class="profile-details">
    			<h3>Meus Dados</h3>
    			<p><strong>Nome Completo:</strong> ${sessionScope.user.fullName}</p>
    			<p><strong>E-mail:</strong> ${sessionScope.user.email}</p>
				<a href="edit-profile" class="btn-edit">Editar Perfil</a>
    		</section>
    		
    	</div>
	</main>
	
	<jsp:include page="../includes/footer.jsp" />
</body>
</html>