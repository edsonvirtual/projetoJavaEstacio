package src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.Label;
import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaLogin extends LoginGUI {

	private JFrame frame;
	private JTextField txtLogin;
	private JTextField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setEnabled(false);
		frame.getContentPane().setForeground(new Color(255, 255, 0));
		frame.setBounds(100, 100, 446, 344);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SISTEMA");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(108, 26, 102, 45);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Login:");
		lblNewLabel_1.setBounds(109, 83, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(109, 108, 235, 20);
		frame.getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Senha:");
		lblNewLabel_2.setBounds(109, 132, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		txtSenha = new JTextField();
		txtSenha.setBounds(109, 154, 235, 20);
		frame.getContentPane().add(txtSenha);
		txtSenha.setColumns(10);
		
		JButton EntrarButton = new JButton("Entrar");
		EntrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				String login = txtLogin.getText();
				String senha = txtSenha.getText();
				if (loginGUI.usuarioDAO.validarLogin(login, senha)) {
					System.out.println("Login bem-sucedido!");
					// Aqui você pode adicionar o código para abrir a tela principal
				} else {
					System.out.println("Login ou senha incorretos!");
				}
			}
		});
		EntrarButton.setBounds(255, 185, 89, 23);
		frame.getContentPane().add(EntrarButton);
		
		JButton EsqueceuSenhaButton = new JButton("Esqueceu a Senha");
		EsqueceuSenhaButton.setBounds(109, 185, 143, 23);
		frame.getContentPane().add(EsqueceuSenhaButton);

		
		JButton btnNewButton_2 = new JButton("Cadastrar-se");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastro telaCadastro = new TelaCadastro();
				telaCadastro.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(109, 212, 235, 23);
		frame.getContentPane().add(btnNewButton_2);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		System.out.println("Tela de Login Visível" + TelaLogin.class);
		
	}
}
