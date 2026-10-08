package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/assets/uploads/*")
public class ImagemController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Path DIRETORIO_UPLOAD = Paths.get(
            "C:/Users/Aluno/Documents/projeto-eclipse/Portfolio/src/main/webapp/assets/uploads")
            .toAbsolutePath().normalize();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String caminho = request.getPathInfo();
        if (caminho == null || caminho.length() <= 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String nomeArquivo = Paths.get(caminho).getFileName().toString();
        Path arquivo = DIRETORIO_UPLOAD.resolve(nomeArquivo).normalize();
        if (!arquivo.startsWith(DIRETORIO_UPLOAD) || !Files.isRegularFile(arquivo)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = Files.probeContentType(arquivo);
        response.setContentType(contentType == null ? "application/octet-stream" : contentType);
        response.setContentLengthLong(Files.size(arquivo));
        Files.copy(arquivo, response.getOutputStream());
    }
}
