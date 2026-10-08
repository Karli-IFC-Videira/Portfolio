package dao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Projeto;

public class CadastroProjetos {

	public boolean CadastrarProjeto(Projeto projeto) throws Exception {

	    String sql = """
	        INSERT INTO project
	        (titulo, descricao, link, tecnologias, data_criacao)
	        VALUES (?, ?, ?, ?, ?)
	        """;

	    try (
	        Connection connect = ConexaoDB.getConexao();
	        PreparedStatement pStatement = connect.prepareStatement(sql)
	    ) {

	        pStatement.setString(1, projeto.getTitulo());
	        pStatement.setString(2, projeto.getDescricao());
	        pStatement.setString(3, projeto.getLink());
	        pStatement.setString(4, projeto.getTecnologias());
	        pStatement.setString(5, projeto.getData_criacao());

	        int registroCadastrado = pStatement.executeUpdate();

	        return registroCadastrado > 0;

	    } catch (SQLException e) {

	        throw new Exception(
	            "Erro ao cadastrar o Registro: " + e.getMessage(),
	            e
	        );
	    }
	}
  
	public void selecionarProjeto(Projeto projeto) throws Exception {

	    String sql = "SELECT * FROM project WHERE id = ?";

	    try (
	        Connection connect = ConexaoDB.getConexao();
	        PreparedStatement pStatement = connect.prepareStatement(sql)
	    ) {

	        pStatement.setInt(1, projeto.getId());

	        try (ResultSet rs = pStatement.executeQuery()) {

	            if (rs.next()) {

	                projeto.setId(rs.getInt("id"));
	                projeto.setTitulo(rs.getString("titulo"));
	                projeto.setDescricao(rs.getString("descricao"));
	                projeto.setLink(rs.getString("link"));
	                projeto.setTecnologias(rs.getString("tecnologias"));
	                projeto.setData_criacao(rs.getString("data_criacao"));
	            }
	        }

	    } catch (SQLException e) {

	        throw new Exception(
	            "Erro ao selecionar o Registro: " + e.getMessage(),
	            e
	        );
	    }
	}
    
	public ArrayList<Projeto> listaProjeto() throws Exception {

	    String sql = "SELECT * FROM project";

	    ArrayList<Projeto> listaProjeto = new ArrayList<>();

	    try (
	        Connection connect = ConexaoDB.getConexao();
	        PreparedStatement pStatement = connect.prepareStatement(sql);
	        ResultSet rs = pStatement.executeQuery()
	    ) {

	        while (rs.next()) {

	            Projeto projeto = new Projeto();

	            projeto.setId(rs.getInt("id"));
	            projeto.setTitulo(rs.getString("titulo"));
	            projeto.setDescricao(rs.getString("descricao"));
	            projeto.setLink(rs.getString("link"));
	            projeto.setTecnologias(rs.getString("tecnologias"));
	            projeto.setData_criacao(rs.getString("data_criacao"));

	            listaProjeto.add(projeto);
	        }

	    } catch (SQLException e) {

	        throw new Exception(
	            "Erro ao buscar o Registro: " + e.getMessage(),
	            e
	        );
	    }

	    return listaProjeto;
	}
     
	public boolean alterarProjeto(Projeto projeto) throws Exception {

	    String sql = """
	        UPDATE project
	        SET titulo = ?,
	            descricao = ?,
	            link = ?,
	            tecnologias = ?,
	            data_criacao = ?
	        WHERE id = ?
	        """;

	    try (
	        Connection connect = ConexaoDB.getConexao();
	        PreparedStatement pStatement = connect.prepareStatement(sql)
	    ) {

	        pStatement.setString(1, projeto.getTitulo());
	        pStatement.setString(2, projeto.getDescricao());
	        pStatement.setString(3, projeto.getLink());
	        pStatement.setString(4, projeto.getTecnologias());
	        pStatement.setString(5, projeto.getData_criacao());
	        pStatement.setInt(6, projeto.getId());

	        int registroAtualizado = pStatement.executeUpdate();

	        return registroAtualizado > 0;

	    } catch (SQLException e) {

	        throw new Exception(
	            "Erro ao alterar o Registro: " + e.getMessage(), e
	        );
	    }
	}
     
	public boolean deletarProjeto(int id) throws Exception {

	    String sql = "DELETE FROM project WHERE id = ?";

	    try (
	        Connection connect = ConexaoDB.getConexao();
	        PreparedStatement pStatement = connect.prepareStatement(sql)
	    ) {

	        pStatement.setInt(1, id);

	        int rowsAffected = pStatement.executeUpdate();

	        return rowsAffected > 0;

	    } catch (SQLException e) {

	        throw new Exception(
	            "Erro ao excluir o Registro: " + e.getMessage(),
	            e
	        );
	    }
	}
} 