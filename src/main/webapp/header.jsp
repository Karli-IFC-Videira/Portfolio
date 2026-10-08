<!doctype html>
<html lang="pt-br">
    <head>
        <title>Notícias</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="assets/js/jquery-3.7.1.min.js" type="text/javascript"></script>
        <script src="assets/js/bootstrap.bundle.min.js" type="text/javascript"></script>
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="assets/css/estilos.css"/>
        <link rel="shortcut icon" href="assets/img/cursos.png" type="image/x-icon">
    </head>
    <body>
        <header>
            <div class="container">
                <nav class="navbar navbar-expand-lg bg-success-subtle">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#">
                            <img src="assets/img/cursos.png" alt="Bootstrap" width="80" height="55">
                        </a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNavDropdown">
                            <ul class="navbar-nav">
                                <% if (session.getAttribute("usuarioLogado") == null) { %>
                                    <li class="nav-item">
                                        <a class="nav-link active" aria-current="page" href="index.jsp">Inicio</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="home">Home</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="contatos">Contatos</a>
                                    </li>
                                <% } %>
                                <li class="nav-item">
                                    <a class="nav-link" href="postagens">Notícias e artigos</a>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        Opções
                                    </a>
                                    <ul class="dropdown-menu">
                                        <% if (session.getAttribute("usuarioLogado") != null) { %>
                                            <li><a class="dropdown-item" href="cadastroProjeto">Inserir Projetos</a></li>
                                            <li><a class="dropdown-item" href="listaProjeto">Listar Projetos</a></li>
                                            <li><hr class="dropdown-divider"></li>
                                        <% } %>
                                        <% if (session.getAttribute("usuarioLogado") != null) { %>
                                            <li><a class="dropdown-item" href="minhasPostagens">Minhas postagens</a></li>
                                            <li><a class="dropdown-item" href="logout">Sair</a></li>
                                        <% } else { %>
                                            <li><a class="dropdown-item" href="login">Iniciar Sessão</a></li>
                                        <% } %>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </div>
        </header>