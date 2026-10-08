package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
  
    public static String db = "portfolio";
    public static String url = "jdbc:mysql://localhost:3306/" + db + "?useSSL=false&serverTimezone=UTC";

    public static String usuario = "root";
    public static String senha = "";

    public static Connection getConexao() throws SQLException {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e);
        }

        try {
            conn = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar com o banco de dados", e);
        }
        return conn;
    }
}