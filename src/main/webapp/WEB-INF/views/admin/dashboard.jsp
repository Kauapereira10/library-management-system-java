<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Admin</title>
</head>
<body>

    <jsp:include page="../includes/header.jsp" />
    <jsp:include page="../includes/nav-bar.jsp" />

    <main>

        <h1>Painel Administrativo</h1>
        <p>Bem-vindo, ${sessionScope.user.nickName}!</p>

        <section class="dashboard-cards">
            <div class="card">
                <h3>Total de Livros</h3>
                <span class="card-number">${totalBooks}</span>
            </div>
            <div class="card">
                <h3>Livros Disponíveis</h3>
                <span class="card-number">${availableBooks}</span>
            </div>
            <div class="card">
                <h3>Livros Emprestados</h3>
                <span class="card-number">${borrowedBooks}</span>
            </div>
            <div class="card">
                <h3>Total de Usuários</h3>
                <span class="card-number">${totalUsers}</span>
            </div>
            <div class="card">
                <h3>Empréstimos Ativos</h3>
                <span class="card-number">${activeLoans}</span>
            </div>
        </section>

        <hr>

        <section>
            <h2>Ações Rápidas</h2>
            <a href="${pageContext.request.contextPath}/admin/books/new">
                + Cadastrar Livro
            </a>
            <br>
            <a href="${pageContext.request.contextPath}/admin/books">
                Ver Todos os Livros
            </a>
        </section>

        <hr>

        <section>
            <h2>Empréstimos Recentes</h2>

            <c:choose>
                <c:when test="${empty recentLoans}">
                    <p>Nenhum empréstimo registrado ainda.</p>
                </c:when>
                <c:otherwise>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Usuário</th>
                                <th>Livro</th>
                                <th>Data</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="loan" items="${recentLoans}">
                                <tr>
                                    <td>${loan.user.fullName}</td>
                                    <td>${loan.book.title}</td>
                                    <td>${loan.loanDate}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${loan.active}">Ativo</c:when>
                                            <c:otherwise>Devolvido</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

        </section>

    </main>

    <jsp:include page="../includes/footer.jsp" />

</body>
</html>