package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConexaoPostgreSQL {

    private static final String URL = "jdbc:postgresql://localhost:5432/login";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    // Retorna uma conexão com o banco (chame em try-with-resources)
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Insere um usuário na tabela 'usuarios' (assumindo colunas nome, usuario, email, senha)
    public static boolean inserirUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios (nome, usuario, email, senha) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getSenha());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Valida login (exemplo)
    public static boolean validarLogin(String login, String senha) {
        String sql = "SELECT 1 FROM usuarios WHERE usuario = ? AND senha = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}