package controller;

import dao.PostagemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Postagem;
import model.Usuario;

@WebServlet(urlPatterns = { "/postagens", "/minhasPostagens", "/novaPostagem", "/editarPostagem", "/excluirPostagem" })
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class PostagemController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private final PostagemDAO POSTAGEM_DAO = new PostagemDAO();
	
	private static final Logger LOGGER = Logger.getLogger(PostagemController.class.getName());
	
	private static final Path DIRETORIO_UPLOAD = Paths.get(
			"C:/Users/Usuario/Documents/Projetos-Eclipse/Portfolio/src/main/webapp/assets/uploads")
			.toAbsolutePath().normalize();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String rota = request.getServletPath();

			if ("/postagens".equals(rota)) {
				List<Postagem> postagens = POSTAGEM_DAO.listarPublicadas();
				request.setAttribute("postagens", postagens);
				request.getRequestDispatcher("postagens.jsp").forward(request, response);
			} else if ("/minhasPostagens".equals(rota)) {
				Usuario usuario = exigirLogin(request, response);
				if (usuario == null)
					return;
				request.setAttribute("postagens", POSTAGEM_DAO.listarPorAutor(usuario.getId()));
				request.getRequestDispatcher("minhasPostagens.jsp").forward(request, response);
			} else if ("/novaPostagem".equals(rota)) {
				if (exigirLogin(request, response) == null)
					return;
				request.getRequestDispatcher("novaPostagem.jsp").forward(request, response);
			} else if ("/editarPostagem".equals(rota)) {
				editarFormulario(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ServletException("Erro ao carregar postagens.", ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			Usuario usuario = exigirLogin(request, response);
			if (usuario == null)
				return;

			String rota = request.getServletPath();
			if ("/novaPostagem".equals(rota)) {
				cadastrar(request, response, usuario);
			} else if ("/editarPostagem".equals(rota)) {
				atualizar(request, response, usuario);
			} else if ("/excluirPostagem".equals(rota)) {
				excluir(request, response, usuario);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ServletException("Erro ao salvar postagem.", ex);
		}
	}

	private void cadastrar(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws Exception {
		String titulo = request.getParameter("titulo");
		String conteudo = request.getParameter("conteudo");
		validarTexto(titulo, conteudo);
		String imagem = salvarImagem(request);

		POSTAGEM_DAO.cadastrar(new Postagem(titulo.trim(), conteudo.trim(), imagem, usuario.getId()));
		request.getSession().setAttribute("successMessage", "Postagem publicada com sucesso.");
		response.sendRedirect("minhasPostagens");
	}

	private void editarFormulario(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario usuario = exigirLogin(request, response);
		if (usuario == null)
			return;

		int id = Integer.parseInt(request.getParameter("id"));
		Postagem postagem = POSTAGEM_DAO.buscarPorIdEAutor(id, usuario.getId());
		
		if (postagem == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Postagem não encontrada.");
			return;
		}

		request.setAttribute("postagem", postagem);
		request.getRequestDispatcher("editarPostagem.jsp").forward(request, response);
	}

	private void atualizar(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		Postagem atual = POSTAGEM_DAO.buscarPorIdEAutor(id, usuario.getId());
		if (atual == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Postagem não encontrada.");
			return;
		}

		String titulo = request.getParameter("titulo");
		String conteudo = request.getParameter("conteudo");
		validarTexto(titulo, conteudo);
		String imagem = salvarImagem(request);
		
		if (imagem == null)
			imagem = atual.getImagem();

		Postagem postagem = new Postagem(id, titulo.trim(), conteudo.trim(), imagem, usuario.getId());
		
		if (!POSTAGEM_DAO.atualizar(postagem)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Você não pode editar esta postagem.");
			return;
		}

		request.getSession().setAttribute("successMessage", "Postagem atualizada com sucesso.");
		response.sendRedirect("minhasPostagens");
	}

	private void excluir(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		Postagem postagem = POSTAGEM_DAO.buscarPorIdEAutor(id, usuario.getId());
		if (postagem == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Postagem não encontrada.");
			return;
		}

		if (!POSTAGEM_DAO.excluir(id, usuario.getId())) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Você não pode excluir esta postagem.");
			return;
		}

		excluirImagem(postagem.getImagem());
		request.getSession().setAttribute("successMessage", "Postagem excluída com sucesso.");
		response.sendRedirect("minhasPostagens");
	}

	private void excluirImagem(String imagem) {

	    if (imagem == null || imagem.trim().isEmpty()) {
	        return;
	    }

	    try {

	        /*
	         * Obtém o diretório físico onde as imagens são armazenadas.
	         */
	        Path pastaUploads = DIRETORIO_UPLOAD;

	        /*
	         * O banco armazena algo como:
	         *
	         * assets/uploads/36dbe70c-bb4a-45ac-8185-decbbe896cae.png
	         *
	         * Precisamos somente do nome do arquivo.
	         */
	        String nomeArquivo = Paths.get(imagem).getFileName().toString();

	        /*
	         * Monta o caminho completo do arquivo.
	         */
	        Path arquivo = pastaUploads.resolve(nomeArquivo).normalize();

	        /*
	         * Garante que o arquivo está realmente dentro
	         * do diretório de uploads antes de excluí-lo.
	         */
	        if (arquivo.startsWith(pastaUploads)) {

	            Files.deleteIfExists(arquivo);
	        }

	    } catch (IOException ex) {

	        LOGGER.log(Level.WARNING,"Não foi possível excluir a imagem da postagem.", ex);
	    }
	}

	private Usuario exigirLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
		if (usuario == null) {
			response.sendRedirect("login");
		}
		return usuario;
	}

	private void validarTexto(String titulo, String conteudo) throws ServletException {
		if (titulo == null || titulo.trim().isEmpty() || conteudo == null || conteudo.trim().isEmpty()) {
			throw new ServletException("Título e conteúdo são obrigatórios.");
		}
	}

	private String salvarImagem(HttpServletRequest request) throws Exception {

	    Part parte = request.getPart("imagem");

	    if (parte == null || parte.getSize() == 0) {
	        return null;
	    }

	    String nomeOriginal = parte.getSubmittedFileName();

	    String extensao = "";

	    int ponto = nomeOriginal.lastIndexOf('.');

	    if (ponto >= 0) {
	        extensao = nomeOriginal.substring(ponto).toLowerCase();
	    }

	    if (!extensao.matches("\\.(jpg|jpeg|png|gif)")) {
	        throw new ServletException("A imagem deve ser JPG, PNG ou GIF.");
	    }

	    String nome = UUID.randomUUID() + extensao;

	    Path pastaUploads = DIRETORIO_UPLOAD;
	    Files.createDirectories(pastaUploads);

	    Path arquivo = pastaUploads.resolve(nome).normalize();
	    if (!arquivo.startsWith(pastaUploads)) {
	        throw new ServletException("Caminho de upload inválido.");
	    }

	    try (InputStream entrada = parte.getInputStream()) {
	        Files.copy(entrada, arquivo, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	    }

	    LOGGER.info("Imagem salva em: " + arquivo);

	    return "assets/uploads/" + nome;
	}
}