<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page = "header.jsp" />
<div class="container">
    <h1>Edição de Projetos</h1>
    <form action="editarProjeto" method="post">
         <input type="hidden" id="id" name="id" value="<%= request.getAttribute("id") %>">
        <div class="mb-3">
            <label  class="form-label">Titulo</label>
            <input type="text" class="form-control" id="titulo" name="titulo" value="<% out.print(request.getAttribute("titulo")); %>">
        </div>
        
        <div class="mb-3">
            <label class="form-label">Descrição</label>
            <textarea class="form-control" id="descricao" name="descricao" rows="3" ><% out.print(request.getAttribute("descricao")); %></textarea>
        </div>
        
        <div class="mb-3">
            <label  class="form-label">Link</label>
            <input class="form-control" id="link" name="link" rows="3" value="<% out.print(request.getAttribute("link")); %>">
        </div>
        
         <div class="mb-3">
            <label  class="form-label">Tecnologias</label>
            <input class="form-control" id="tecnologias" name="tecnologias" rows="3" value="<% out.print(request.getAttribute("tecnologias")); %>">
        </div>
        
         <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Data</label>
            <input class="form-control" id="data" name="data" rows="3" value="<% out.print(request.getAttribute("data_criacao")); %>">
         </div>
        <button type="submit" class="btn btn-primary">Cadastro</button>

    </form>
</div>
<jsp:include page = "footer.jsp" />