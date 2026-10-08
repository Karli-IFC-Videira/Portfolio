<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page = "header.jsp" />
<div class="container">
    <h1>Cadastro de Projetos</h1>
    <% if (session.getAttribute("successMessage") != null) { %> <div class="alert alert-success"> <%= session.getAttribute("successMessage") %> <% session.removeAttribute("successMessage"); %> </div> <% } %> <% if (session.getAttribute("errorMessage") != null) { %> <div class="alert alert-danger"> <%= session.getAttribute("errorMessage") %> <% session.removeAttribute("errorMessage"); %> </div> <% } %>
    <form action="cadastroProjeto" method="post">
        <div class="mb-3">
            <label  class="form-label">Titulo</label>
            <input type="text" class="form-control" id="titulo" name="titulo">
        </div>
        
        <div class="mb-3">
            <label class="form-label">Descrição</label>
            <textarea class="form-control" id="descricao" name="descricao" rows="3"></textarea>
        </div>
        
        <div class="mb-3">
            <label  class="form-label">Link</label>
            <input class="form-control" id="link" name="link" rows="3">
        </div>
        
         <div class="mb-3">
            <label  class="form-label">Tecnologias</label>
            <input class="form-control" id="tecnologias" name="tecnologias" rows="3">
        </div>
        
         <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Data</label>
            <input class="form-control" id="data" name="data" rows="3">
         </div>
        <button type="submit" class="btn btn-primary">Cadastro</button>

    </form>
</div>
<jsp:include page = "footer.jsp" />
