<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.Postagem" %>
<jsp:include page="header.jsp" />
<div class="container">
    <h1>Notícias e artigos</h1>
    <p>Publicações da comunidade.</p>
    <% List<Postagem> postagens = (List<Postagem>) request.getAttribute("postagens"); %>
    <% if (postagens == null || postagens.isEmpty()) { %>
        <div class="alert alert-info">Ainda não existem publicações.</div>
    <% } %>
    <% if (postagens != null) { for (Postagem postagem : postagens) { %>
        <article class="border rounded p-3 mb-4">
            <h2><%= postagem.getTitulo() %></h2>
            <% if (postagem.getImagem() != null) { %>
                <img class="img-fluid mb-3" src="<%= postagem.getImagem() %>" alt="Imagem da postagem">
            <% } %>
            <p><%= postagem.getConteudo() %></p>
            <small>Publicado por <strong><%= postagem.getAutorNome() %></strong> em
                <%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(postagem.getDataPublicacao()) %>
            </small>
        </article>
    <% } } %>
</div>
<jsp:include page="footer.jsp" />
