<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Postagem" %>
<% Postagem postagem = (Postagem) request.getAttribute("postagem"); %>
<jsp:include page="header.jsp" />
<div class="container">
    <h1>Editar postagem</h1>
    <form action="editarPostagem" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<%= postagem.getId() %>">
        <div class="mb-3">
            <label class="form-label" for="titulo">Título</label>
            <input class="form-control" type="text" id="titulo" name="titulo" maxlength="180" value="<%= postagem.getTitulo() %>" required>
        </div>
        <div class="mb-3">
            <label class="form-label" for="conteudo">Conteúdo</label>
            <textarea class="form-control" id="conteudo" name="conteudo" rows="8" required><%= postagem.getConteudo() %></textarea>
        </div>
        <div class="mb-3">
            <label class="form-label" for="imagem">Trocar imagem</label>
            <input class="form-control" type="file" id="imagem" name="imagem" accept=".jpg,.jpeg,.png,.gif">
        </div>
        <button class="btn btn-primary" type="submit">Salvar</button>
        <a class="btn btn-secondary" href="minhasPostagens">Cancelar</a>
    </form>
</div>
<jsp:include page="footer.jsp" />
