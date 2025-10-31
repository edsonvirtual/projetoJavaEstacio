package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/login";
        String user = "postgres";
        String password = "1234";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso!");
            // Aqui você pode adicionar o código para interagir com o banco de dados
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

	public boolean validarLogin(String login, String senha) {
		// TODO Auto-generated method stub
		return false;
	}
}// 		JLabel lblNewLabel = new JLabel("LOGIN");
