package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Postagem;

public class PostagemDAO {

    private static final String COLUNAS =
            "SELECT p.id, p.titulo, p.conteudo, p.imagem, p.autor_id, "
            + "p.data_publicacao, p.status, u.nome AS autor_nome "
            + "FROM postagem p INNER JOIN usuario u ON u.id = p.autor_id ";

    public void cadastrar(Postagem postagem) throws Exception {
        String sql = "INSERT INTO postagem " + "(titulo, conteudo, imagem, autor_id, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConexaoDB.getConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, postagem.getTitulo());
            statement.setString(2, postagem.getConteudo());
            statement.setString(3, postagem.getImagem());
            statement.setInt(4, postagem.getAutorId());
            statement.setString(5, "PUBLICADA");
            statement.executeUpdate();
        }
    }

    public List<Postagem> listarPublicadas() throws Exception {
        String sql = COLUNAS + "WHERE p.status = 'PUBLICADA' ORDER BY p.data_publicacao DESC";
        return consultar(sql);
    }

    public List<Postagem> listarPorAutor(int autorId) throws Exception {
        String sql = COLUNAS + "WHERE p.autor_id = ? ORDER BY p.data_publicacao DESC";
        List<Postagem> postagens = new ArrayList<>();

        try (Connection connection = ConexaoDB.getConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, autorId);
            preencherLista(statement, postagens);
        }

        return postagens;
    }

    public Postagem buscarPorIdEAutor(int id, int autorId) throws Exception {
        String sql = COLUNAS + "WHERE p.id = ? AND p.autor_id = ?";

        try (Connection connection = ConexaoDB.getConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, autorId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return criarPostagem(result);
                }
            }
        }

        return null;
    }

    public boolean atualizar(Postagem postagem) throws Exception {
        String sql = "UPDATE postagem SET titulo = ?, conteudo = ?, imagem = ? "
                + "WHERE id = ? AND autor_id = ?";

        try (Connection connection = ConexaoDB.getConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, postagem.getTitulo());
            statement.setString(2, postagem.getConteudo());
            statement.setString(3, postagem.getImagem());
            statement.setInt(4, postagem.getId());
            statement.setInt(5, postagem.getAutorId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id, int autorId) throws Exception {
        String sql = "DELETE FROM postagem WHERE id = ? AND autor_id = ?";

        try (Connection connection = ConexaoDB.getConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, autorId);
            return statement.executeUpdate() > 0;
        }
    }

    private List<Postagem> consultar(String sql) throws Exception {
        List<Postagem> postagens = new ArrayList<>();

        try (Connection connection = ConexaoDB.getConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            preencherLista(statement, postagens);
        }

        return postagens;
    }

    private void preencherLista(PreparedStatement statement, List<Postagem> postagens)
            throws Exception {
        try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                postagens.add(criarPostagem(result));
            }
        }
    }

    private Postagem criarPostagem(ResultSet result) throws Exception {
        Postagem postagem = new Postagem();
        postagem.setId(result.getInt("id"));
        postagem.setTitulo(result.getString("titulo"));
        postagem.setConteudo(result.getString("conteudo"));
        postagem.setImagem(result.getString("imagem"));
        postagem.setAutorId(result.getInt("autor_id"));
        postagem.setAutorNome(result.getString("autor_nome"));
        postagem.setDataPublicacao(result.getTimestamp("data_publicacao"));
        postagem.setStatus(result.getString("status"));
        return postagem;
    }
}