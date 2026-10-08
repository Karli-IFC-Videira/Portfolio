package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 *
 * @author Jesus Munoz
 */
@WebServlet(name = "PortfolioController", urlPatterns = {"/PortfolioController", "/home", "/contatos"})
public class PortfolioController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getServletPath();
        
        if(acao.equals("/home")){
            chamarHome(request, response);
        }
        
        if(acao.equals("/contatos")){
            chamarContatos(request, response);
        }
    }
    
    protected void chamarHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("home.jsp");
    }
    
    protected void chamarContatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("contatos.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}