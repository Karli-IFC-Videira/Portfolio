<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<div class="container">
    <h1>Entrar</h1>
    <% if (session.getAttribute("successMessage") != null) { %>
        <div class="alert alert-success"><%= session.getAttribute("successMessage") %></div>
        <% session.removeAttribute("successMessage"); %>
    <% } %>
    <% if (request.getAttribute("erro") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("erro") %></div>
    <% } %>
    <form action="login" method="post">
        <div class="mb-3">
            <label class="form-label" for="email">E-mail</label>
            <input class="form-control" type="email" id="email" name="email" required>
        </div>
        <div class="mb-3">
            <label class="form-label" for="senha">Senha</label>
            <input class="form-control" type="password" id="senha" name="senha" required>
        </div>
        <button class="btn btn-primary" type="submit">Entrar</button>
        <a class="btn btn-secondary" href="cadastroUsuario">Criar usuário</a>
    </form>
</div>
<jsp:include page="footer.jsp" />
