package controller;

import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Usuario;

@WebServlet(urlPatterns = {"/login", "/cadastroUsuario", "/logout"})
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UsuarioDAO usuarioDao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rota = request.getServletPath();

        if ("/login".equals(rota)) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if ("/cadastroUsuario".equals(rota)) {
            request.getRequestDispatcher("cadastroUsuario.jsp").forward(request, response);
        } else if ("/logout".equals(rota)) {
            request.getSession().invalidate();
            response.sendRedirect("login");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String rota = request.getServletPath();

        try {
            if ("/login".equals(rota)) {
                entrar(request, response);
            } else if ("/cadastroUsuario".equals(rota)) {
                cadastrar(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception ex) {
            getServletContext().log("Erro ao processar a requisição: " + rota, ex);

            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    private void entrar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Usuario usuario = usuarioDao.autenticar(email, senha);

        if (usuario == null) {
            request.setAttribute("erro", "E-mail ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return; // Encerre a execução deste método imediatamente./Não execute mais nada neste método
        }

        request.getSession().setAttribute("usuarioLogado", usuario);
        response.sendRedirect("minhasPostagens");
    }

    private void cadastrar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (vazio(nome) || vazio(email) || vazio(senha)) {
            request.setAttribute("erro", "Preencha nome, e-mail e senha.");
            request.getRequestDispatcher("cadastroUsuario.jsp").forward(request, response);
            return;
        }

        usuarioDao.cadastrar(new Usuario(nome, email, senha));
        request.getSession().setAttribute("successMessage", "Usuário cadastrado. Faça login.");
        response.sendRedirect("login");
    }

    private boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}