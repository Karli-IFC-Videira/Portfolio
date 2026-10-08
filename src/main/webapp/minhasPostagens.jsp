<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.Postagem" %>

<jsp:include page="header.jsp" />

<div class="container">
    <h1>Minhas postagens</h1>
    <% if (session.getAttribute("successMessage") != null) { %>
        <div class="alert alert-success"><%= session.getAttribute("successMessage") %></div>
        <% session.removeAttribute("successMessage"); %>
    <% } %>
    <a class="btn btn-primary mb-3" href="novaPostagem">Nova postagem</a>
    <% List<Postagem> postagens = (List<Postagem>) request.getAttribute("postagens"); %>
    <% if (postagens == null || postagens.isEmpty()) { %>
        <div class="alert alert-info">Você ainda não possui postagens.</div>
    <% } else { %>
        <table class="table table-bordered table-striped">
            <thead>
            	<tr>
            		<th>Título</th>
            		<th>Data</th>
            		<th>Status</th>
            		<th>Ações</th>
            	</tr>
            </thead>
            <tbody>
            <% for (Postagem postagem : postagens) { %>
                <tr>
                    <td><%= postagem.getTitulo() %></td>
                    <td><%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(postagem.getDataPublicacao()) %></td>
                    <td><%= postagem.getStatus() %></td>
                    <td>
                        <a class="btn btn-sm btn-primary" href="editarPostagem?id=<%= postagem.getId() %>">Editar</a>
                        <form id="formExcluirPostagem<%= postagem.getId() %>" action="excluirPostagem" method="post" class="d-inline">
                            <input type="hidden" name="id" value="<%= postagem.getId() %>">
                            <button class="btn btn-sm btn-danger" type="button" onclick="confirmDeletePostagem(<%= postagem.getId() %>)">Excluir</button>
                        </form>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>
    <% } %>
</div>

<div class="modal fade" id="confirmDeletePostagemModal" tabindex="-1" role="dialog" aria-labelledby="confirmDeletePostagemModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmDeletePostagemModalLabel">Confirmar exclusão</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
            </div>
            <div class="modal-body">
                Tem certeza de que deseja excluir esta postagem?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-danger" id="confirmDeletePostagemButton">Excluir</button>
            </div>
        </div>
    </div>
</div>

<script src="assets/js/confirmacao.js"></script>
<jsp:include page="footer.jsp" />