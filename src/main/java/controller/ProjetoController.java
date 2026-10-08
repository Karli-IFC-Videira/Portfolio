package controller;

import dao.CadastroProjetos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Projeto;
import model.Usuario;

/**
 * Controller responsável pelo gerenciamento das requisições
 * relacionadas aos projetos do portfólio.
 *
 * @author Jesus Munoz
 */
@WebServlet(
    name = "ProjetoController",
    urlPatterns = {
        "/ProjetoController",
        "/cadastroProjeto",
        "/listaProjeto",
        "/editarProjeto",
        "/selecaoProjeto",
        "/excluirProjeto"
    }
)
public class ProjetoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /*
     * Mapa utilizado para associar as rotas às páginas JSP.
     *
     * Exemplo:
     *
     * /cadastroProjeto → cadastroProjeto.jsp
     *
     * O Map é utilizado aqui para evitar a necessidade de criar
     * vários blocos if/else apenas para encaminhar o usuário
     * para uma página.
     */
    private Map<String, String> rotas;

    /*
     * Objeto responsável por realizar as operações relacionadas
     * aos projetos no banco de dados.
     */
    private CadastroProjetos projetoDao = new CadastroProjetos();

    /*
     * Objeto utilizado para armazenar os dados de um projeto
     * durante operações como seleção e edição.
     */
    private Projeto listaProjeto = new Projeto();

    /**
     * Método executado pelo container do servlet durante sua
     * inicialização.
     *
     * Aqui são configuradas as rotas que possuem um
     * encaminhamento direto para uma página JSP.
     */
    @Override
    public void init() throws ServletException {
        super.init();

        rotas = new HashMap<>();

        // Associação entre a rota e a página JSP correspondente.
        rotas.put("/cadastroProjeto", "cadastroProjeto.jsp");
    }

    /**
     * Trata as requisições HTTP do tipo GET.
     *
     * O método getServletPath() identifica qual das URLs
     * mapeadas no @WebServlet foi utilizada pelo usuário.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String rota = request.getServletPath();

        try {
        	// Verifica se o usuário está logado antes de continuar executando a requisição
            if (!exigirLogin(request, response)) {
                return;
            }

            /*
             * A rota /listaProjeto não apenas abre uma página.
             * Antes de encaminhar para a JSP, é necessário
             * consultar o banco de dados e obter os projetos.
             */
            if (rota.equals("/listaProjeto")) {

                listarProj(request, response);

            /*
             * A rota /selectprojeto recebe o ID de um projeto,
             * consulta seus dados no banco e encaminha essas
             * informações para a página de edição.
             */
            } else if (rota.equals("/selecaoProjeto")) {

                listarProjetos(request, response);

            /*
             * A exclusão também exige uma operação no banco
             * antes de retornar para a lista de projetos.
             */
            } else if (rota.equals("/excluirProjeto")) {

                excluirProjeto(request, response);

            } else {

                /*
                 * Para as demais rotas, verifica se existe uma
                 * página correspondente no Map.
                 */
                String pagina = rotas.get(rota);

                if (pagina != null) {

                    /*
                     * O forward encaminha a requisição internamente
                     * para a página JSP.
                     *
                     * Diferentemente do redirect, o navegador
                     * não realiza uma nova requisição.
                     */
                    request.getRequestDispatcher(pagina).forward(request, response);

                } else {

                    /*
                     * Caso a rota não esteja configurada,
                     * retorna o status HTTP 404.
                     */
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página não encontrada.");
                }
            }

        } catch (Exception ex) {

            /*
             * Registra no log do servidor qualquer exceção
             * ocorrida durante o processamento da requisição.
             */
            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, "Erro ao processar a requisição.", ex);
        }
    }

    /**
     * Trata as requisições HTTP do tipo POST.
     *
     * Geralmente utilizamos POST quando os dados enviados
     * pelo cliente precisam provocar alguma alteração no
     * servidor, como cadastrar ou editar um projeto.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rota = request.getServletPath();

        try {

            if (!exigirLogin(request, response)) {
                return;
            }

            /*
             * Recebe os dados enviados pelo formulário de cadastro
             * e chama o método responsável por inserir o projeto.
             */
            if (rota.equals("/cadastroProjeto")) {

                inserirProjeto(request, response);

            /*
             * Recebe os dados enviados pelo formulário de edição
             * e chama o método responsável por alterar o projeto.
             */
            } else if (rota.equals("/editarProjeto")) {

                editarProjeto(request, response);

            } else {

                /*
                 * Caso a requisição POST utilize uma rota que não
                 * possui uma ação configurada.
                 */
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada.");
            }

        } catch (Exception ex) {

            Logger.getLogger(ProjetoController.class.getName()).log(Level.SEVERE, "Erro ao processar a requisição.", ex);
        }
    }

    private boolean exigirLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            response.sendRedirect("login");
            return false;
        }

        return true;
    }

    /**
     * Consulta todos os projetos cadastrados no banco de dados
     * e encaminha a lista para a página listaProjeto.jsp.
     */
    protected void listarProj(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        /*
         * Consulta o banco de dados através do DAO.
         */
        ArrayList<Projeto> listaProjetos = projetoDao.listaProjeto();

        /*
         * Armazena a lista como um atributo da requisição.
         *
         * A JSP poderá acessar esse atributo para percorrer
         * e apresentar os projetos cadastrados.
         */
        request.setAttribute("listarProjeto", listaProjetos);

        /*
         * Encaminha a requisição para a página que apresenta
         * a lista de projetos.
         */
        request.getRequestDispatcher("listaProjeto.jsp").forward(request, response);
    }

    /**
     * Busca um projeto específico pelo seu ID.
     *
     * Esse método é utilizado quando o usuário deseja editar
     * um projeto existente.
     */
    protected void listarProjetos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        /*
         * Recupera o ID enviado através da requisição.
         *
         * Como getParameter() retorna uma String, é necessário
         * converter o valor para int.
         */
        int id = Integer.parseInt(request.getParameter("id")
        );

        /*
         * Define o ID do objeto que será utilizado na consulta.
         */
        listaProjeto.setId(id);

        /*
         * Consulta o projeto no banco de dados.
         */
        projetoDao.selecionarProjeto(listaProjeto);

        /*
         * Os dados do projeto são enviados para a requisição.
         *
         * A página editarProjeto.jsp poderá utilizar esses
         * atributos para preencher os campos do formulário.
         */
        request.setAttribute("id", listaProjeto.getId());
        request.setAttribute("titulo", listaProjeto.getTitulo());
        request.setAttribute("descricao", listaProjeto.getDescricao());
        request.setAttribute("link", listaProjeto.getLink());
        request.setAttribute("tecnologias", listaProjeto.getTecnologias());
        request.setAttribute("data_criacao", listaProjeto.getData_criacao()
        );

        /*
         * Encaminha os dados para o formulário de edição.
         */
        request.getRequestDispatcher("editarProjeto.jsp").forward(request, response);
    }

    /**
     * Recebe os dados enviados pelo formulário de cadastro
     * e insere um novo projeto no banco de dados.
     */
    protected void inserirProjeto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        /*
         * Define a codificação dos dados recebidos.
         *
         * Isso evita problemas com caracteres especiais,
         * como acentos e cedilha.
         */
        request.setCharacterEncoding("UTF-8");

        /*
         * Recupera os valores enviados pelo formulário.
         */
        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");
        String link = request.getParameter("link");
        String tecnologias = request.getParameter("tecnologias");
        String dataCriacao = request.getParameter("data");

        /*
         * Cria um objeto Projeto utilizando os dados recebidos.
         */
        Projeto projeto = new Projeto(titulo, descricao, link, tecnologias, dataCriacao);

        /*
         * Solicita ao DAO que cadastre o projeto no banco.
         *
         * O método retorna true quando o cadastro é realizado
         * com sucesso.
         */
        boolean cadastro = projetoDao.CadastrarProjeto(projeto);

        /*
         * Armazena uma mensagem na sessão para informar ao
         * usuário o resultado da operação.
         *
         * A sessão é utilizada porque logo depois será feito
         * um redirect.
         */
        if (cadastro) {

            request.getSession().setAttribute("successMessage", "Projeto cadastrado com sucesso!");

        } else {

            request.getSession().setAttribute("errorMessage", "Erro ao cadastrar o projeto!");
        }

        /*
         * Após o cadastro, redireciona para a rota responsável
         * pelo formulário de cadastro.
         *
         * Diferentemente do forward(), o redirect provoca
         * uma nova requisição HTTP.
         */
        response.sendRedirect("cadastroProjeto");
    }

    /**
     * Atualiza os dados de um projeto existente.
     */
    protected void editarProjeto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {

        request.setCharacterEncoding("UTF-8");

        /*
         * Recupera o ID do projeto que será alterado.
         */
        int id = Integer.parseInt(request.getParameter("id"));

        /*
         * Recupera os novos valores enviados pelo formulário.
         */
        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");
        String link = request.getParameter("link");
        String tecnologias = request.getParameter("tecnologias");
        String dataCriacao = request.getParameter("data");

        /*
         * Cria um objeto Projeto contendo o ID e os novos dados.
         */
        Projeto projeto = new Projeto(
                id,
                titulo,
                descricao,
                link,
                tecnologias,
                dataCriacao
        );

        /*
         * Solicita ao DAO que atualize o registro no banco.
         */
        boolean editado =
                projetoDao.alterarProjeto(projeto);

        /*
         * Armazena uma mensagem na sessão informando
         * o resultado da operação.
         */
        if (editado) {

            request.getSession().setAttribute("successMessage", "Registro editado com sucesso!");

        } else {

            request.getSession().setAttribute("errorMessage", "Erro ao editar o registro!");
        }

        /*
         * Após a edição, retorna para a lista de projetos.
         */
        response.sendRedirect("listaProjeto");
    }

    /**
     * Exclui um projeto utilizando seu ID.
     */
    protected void excluirProjeto(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, Exception {

        /*
         * Recupera o ID do projeto que será excluído.
         */
        int id = Integer.parseInt(
                request.getParameter("id")
        );

        /*
         * Solicita ao DAO a exclusão do projeto.
         */
        boolean deletado = projetoDao.deletarProjeto(id);

        /*
         * Armazena uma mensagem informando o resultado
         * da exclusão.
         */
        if (deletado) {

            request.getSession().setAttribute("successMessage", "Projeto excluído com sucesso!");

        } else {

            request.getSession().setAttribute("errorMessage", "Erro ao excluir o projeto!");
        }

        /*
         * Depois da exclusão, retorna para a lista de projetos.
         */
        response.sendRedirect("listaProjeto");
    }
}