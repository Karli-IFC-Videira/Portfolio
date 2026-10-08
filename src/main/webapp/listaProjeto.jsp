<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Projeto"%>
<jsp:include page="header.jsp" />

<div class="container">
    <% if (session.getAttribute("successMessage") != null) { %>
        <div class="alert alert-success">
            <%= session.getAttribute("successMessage") %>
            <% session.removeAttribute("successMessage"); %>
        </div>
    <% } %>
    
    <% if (session.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger">
            <%= session.getAttribute("errorMessage") %>
            <% session.removeAttribute("errorMessage"); %>
        </div>
    <% } %>

    <h1>Lista de Projetos Cadastrados</h1>
    <table class="table table-responsive table-bordered table-dark table-striped">
        <thead class="thead-dark">
            <tr class="align-middle">
                <th scope="col">Id</th>
                <th scope="col">Titulo</th>
                <th scope="col">Descrição</th>
                <th class="text-center" scope="col">Ações</th>
            </tr>
        </thead>
        <tbody>
            <%  
                 if (request.getAttribute("listarProjeto") != null) {
                    List<?> listaprojt = (List<?>) request.getAttribute("listarProjeto");
                    for (int i = 0; i < listaprojt.size(); i++) {
                        Projeto projeto = (Projeto) listaprojt.get(i);
            %>
            <tr>
                <td scope="row"><%= projeto.getId() %></td>
                <td scope="row"><%= projeto.getTitulo() %></td>
                <td scope="row"><%= projeto.getDescricao() %></td>
                <td class="text-center" scope="row">
                    <a href="selecaoProjeto?id=<%= projeto.getId() %>">
                        <button type="button" class="btn btn-primary">Editar</button></a>
                    <button type="button" class="btn btn-danger" onclick="confirmDelete(<%= projeto.getId() %>)">Excluir</button>
                </td>
            </tr>
            <%        
                    }
                }
            %>  
        </tbody>
    </table>
    <br>
</div>

<!-- Modal de Confirmação de Exclusão -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmDeleteModalLabel">Confirmar Exclusão</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>

            </div>
            <div class="modal-body">
                Tem certeza de que deseja excluir este projeto?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-danger" id="confirmDeleteButton">Excluir</button>
            </div>
        </div>
    </div>
</div>

<script src="assets/js/confirmacao.js"></script>
<jsp:include page="footer.jsp" />