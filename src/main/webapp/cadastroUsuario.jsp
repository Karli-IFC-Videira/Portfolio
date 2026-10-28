<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<div class="container">
    <h1>Criar usuário</h1>
    <% if (request.getAttribute("erro") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("erro") %></div>
    <% } %>
    <form action="cadastroUsuario" method="post">
        <div class="mb-3">
            <label class="form-label" for="nome">Nome</label>
            <input class="form-control" type="text" id="nome" name="nome" required>
        </div>
        <div class="mb-3">
            <label class="form-label" for="email">E-mail</label>
            <input class="form-control" type="email" id="email" name="email" required>
        </div>
        <div class="mb-3">
            <label class="form-label" for="senha">Senha</label>
            <input class="form-control" type="password" id="senha" name="senha" minlength="6" required>
        </div>
        <button class="btn btn-primary" type="submit">Cadastrar</button>
        <a class="btn btn-secondary" href="login">Voltar</a>
    </form>
</div>
<jsp:include page="footer.jsp" />

<!-- 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="header.jsp" />

<div class="container">
    <h1>Criar usuário</h1>

    <c:if test="${not empty erro}">
        <div class="alert alert-danger">
            <c:out value="${erro}" />
        </div>
    </c:if>

    <form action="cadastroUsuario" method="post">
        <div class="mb-3">
            <label class="form-label" for="nome">Nome</label>
            <input class="form-control"
                   type="text"
                   id="nome"
                   name="nome"
                   required>
        </div>

        <div class="mb-3">
            <label class="form-label" for="email">E-mail</label>
            <input class="form-control"
                   type="email"
                   id="email"
                   name="email"
                   required>
        </div>

        <div class="mb-3">
            <label class="form-label" for="senha">Senha</label>
            <input class="form-control"
                   type="password"
                   id="senha"
                   name="senha"
                   minlength="6"
                   required>
        </div>

        <button class="btn btn-primary" type="submit">
            Cadastrar
        </button>

        <a class="btn btn-secondary" href="login">
            Voltar
        </a>
    </form>
</div>

<jsp:include page="footer.jsp" />
 -->