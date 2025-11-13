package src;
import javax.swing.*;	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField loginField;
    private JPasswordField senhaField;
    private JButton loginButton;
    ConexaoPostgreSQL usuarioDAO;

    public LoginGUI() {
    	    super("Tela de Login");
    	    this.usuarioDAO = new ConexaoPostgreSQL(); // Inicializa a variável de INSTÂNCIA
    	    // ...
    	 // Estas linhas devem estar ANTES de addActionListener
    	    loginField = new JTextField(15);
    	    senhaField = new JPasswordField(15);
    	    loginButton = new JButton("Login"); // ESTA LINHA É ESSENCIAL!
        // Ação do Botão
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String senha = new String(senhaField.getPassword());

                if (usuarioDAO.validarLogin(login, senha)) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                    // Fechar a tela de login e abrir a tela principal
                    // dispose(); 
                    // new TelaPrincipal().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Login ou senha incorretos!", 
                                                   "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Configurações da Janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // Ajusta o tamanho da janela aos componentes
        setLocationRelativeTo(null); // Centraliza a janela
        setVisible(false); // Torna a janela visível
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }
}