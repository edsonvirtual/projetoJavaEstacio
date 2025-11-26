package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import src.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ConexaoPostgreSQL {

    private static final String URL = "jdbc:postgresql://localhost:5432/Agenda";
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
    
    private java.sql.Date formatarDataParaSQL(String dataString) throws ParseException {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date dataUtil = formatador.parse(dataString);
        return new java.sql.Date(dataUtil.getTime());
    }

    /**
     * Converte um java.sql.Date de volta para a String "dd/MM/yyyy".
     */
    private String formatarDataParaTabela(java.sql.Date dataSQL) {
        if (dataSQL == null) return "";
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(dataSQL);
    }

    /**
     * Insere um novo evento no banco de dados.
     */
    public void inserirEvento(String descricao, String data, String email, String periodicidade, boolean alarme) {
        // Assumindo que sua tabela é 'compromisso' e tem estas colunas
        String sql = "INSERT INTO compromisso (descricao, data_evento, email, periodicidade, alarme) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            java.sql.Date dataSQL = formatarDataParaSQL(data); // Converte a data
            
            stmt.setString(1, descricao);
            stmt.setDate(2, dataSQL); // Usa setDate, não setString!
            stmt.setString(3, email);
            stmt.setString(4, periodicidade);
            stmt.setBoolean(5, alarme);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL ao inserir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Data em formato inválido! Use dd/MM/yyyy.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Busca todos os eventos do banco de dados para listar na tabela.
     */
    
    public List<Object[]> listarEventos() {
        List<Object[]> eventos = new ArrayList<>();
        // CORREÇÃO: Adicionada a vírgula entre 'id' e 'data_evento'
        String sql = "SELECT id, data_evento, descricao, periodicidade, email, alarme FROM compromisso ORDER BY data_evento";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Pega os dados do banco
                int id = rs.getInt("id"); // <--- PEGA O ID
                java.sql.Date dataSQL = rs.getDate("data_evento");
                String descricao = rs.getString("descricao");
                String periodicidade = rs.getString("periodicidade");
                String email = rs.getString("email");
                boolean alarme = rs.getBoolean("alarme");
                
                // Formata para a tabela
                String dataFormatada = formatarDataParaTabela(dataSQL);
                String alarmeStr = alarme ? "Sim" : "Não";
                
                // Adiciona na lista (ID é o primeiro item!)
                eventos.add(new Object[]{id, dataFormatada, descricao, periodicidade, email, alarmeStr});
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL ao listar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return eventos;
    }

    /**
     * 2. NOVO MÉTODO: Deletar Evento
     * Deleta um evento baseado no seu ID.
     */
    public void deletarEvento(int id) {
        String sql = "DELETE FROM compromisso WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL ao deletar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 3. NOVO MÉTODO: Atualizar Evento
     * Atualiza um evento existente baseado no seu ID.
     */
    public void atualizarEvento(int id, String descricao, String data, String email, String periodicidade, boolean alarme) {
        String sql = "UPDATE compromisso SET descricao = ?, data_evento = ?, email = ?, periodicidade = ?, alarme = ? " +
                     "WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            java.sql.Date dataSQL = formatarDataParaSQL(data); // Converte a data
            
            stmt.setString(1, descricao);
            stmt.setDate(2, dataSQL);
            stmt.setString(3, email);
            stmt.setString(4, periodicidade);
            stmt.setBoolean(5, alarme);
            stmt.setInt(6, id); // O ID vai no WHERE
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL ao atualizar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Data em formato inválido! Use dd/MM/yyyy.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}