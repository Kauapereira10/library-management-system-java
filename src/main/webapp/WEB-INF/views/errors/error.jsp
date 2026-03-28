<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Erro</title>
<style>
body {
	font-family: Arial;
	text-align: center;
	background-color: #f8f9fa;
}

.box {
	margin-top: 100px;
}
</style>
</head>
<body>

	<div class="box">
		<h1>😕 Algo deu errado</h1>
		<p>Estamos trabalhando para corrigir isso.</p>
		<a href="${pageContext.request.contextPath}/home">Voltar para o início</a>
	</div>
</body>
</html>