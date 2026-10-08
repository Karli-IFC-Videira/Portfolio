package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {

    public void cadastrar(Usuario usuario) throws Exception {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection connection = ConexaoDB.getConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNome().trim());
            statement.setString(2, usuario.getEmail().trim().toLowerCase());
            statement.setString(3, BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
            statement.executeUpdate();
        }
    }

    public Usuario autenticar(String email, String senha) throws Exception {
        String sql = "SELECT id, nome, email, senha FROM usuario WHERE email = ?";

        try (Connection connection = ConexaoDB.getConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email.trim().toLowerCase());

            try (ResultSet result = statement.executeQuery()) {
                if (result.next() && BCrypt.checkpw(senha, result.getString("senha"))) {
                    return new Usuario(
                            result.getInt("id"),
                            result.getString("nome"),
                            result.getString("email")
                    );
                }
            }
        }

        return null;
    }
}