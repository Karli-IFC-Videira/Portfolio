<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<div class="container">
    <h1>Nova postagem</h1>
    <form action="novaPostagem" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label class="form-label" for="titulo">Título</label>
            <input class="form-control" type="text" id="titulo" name="titulo" maxlength="180" required>
        </div>
        <div class="mb-3">
            <label class="form-label" for="conteudo">Conteúdo</label>
            <textarea class="form-control" id="conteudo" name="conteudo" rows="8" required></textarea>
        </div>
        <div class="mb-3">
            <label class="form-label" for="imagem">Imagem (JPG, PNG ou GIF)</label>
            <input class="form-control" type="file" id="imagem" name="imagem" accept=".jpg,.jpeg,.png,.gif">
        </div>
        <button class="btn btn-primary" type="submit">Publicar</button>
        <a class="btn btn-secondary" href="minhasPostagens">Cancelar</a>
    </form>
</div>
<jsp:include page="footer.jsp" />
