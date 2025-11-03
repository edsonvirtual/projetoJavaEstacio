package src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;

public class AgendaFrame extends JFrame {
	

	private static final long serialVersionUID = 1L;
	protected static Object usuarioDAO;
	private JPanel contentPane;
	private JTextField DescricaoEvento;
	private JTextField DatadoEvento;
	private JTextField Email;
	private JTable table;
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgendaFrame frame = new AgendaFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AgendaFrame() {
		setBounds(100, 100, 664, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("tab1");
		tabbedPane.setBounds(10, 11, 5, 5);
		contentPane.add(tabbedPane);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 628, 440);
		contentPane.add(tabbedPane_1);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("Cadastro Eventos", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Descrição do Evento:");
		lblNewLabel.setBounds(10, 11, 115, 14);
		panel.add(lblNewLabel);
		
		DescricaoEvento = new JTextField();
		DescricaoEvento.setBounds(10, 29, 603, 20);
		panel.add(DescricaoEvento);
		DescricaoEvento.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data do Evento:");
		lblNewLabel_1.setBounds(10, 60, 89, 14);
		panel.add(lblNewLabel_1);
		
		DatadoEvento = new JTextField("dd/MM/yyyy");
		DatadoEvento.setBounds(109, 57, 177, 20);
		panel.add(DatadoEvento);
		DatadoEvento.setColumns(10);
				
		JLabel lblNewLabel_2 = new JLabel("Encaminhar e-mail:");
		lblNewLabel_2.setBounds(10, 89, 101, 14);
		panel.add(lblNewLabel_2);
		
		Email = new JTextField();
		Email.setBounds(109, 86, 246, 20);
		panel.add(Email);
		Email.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Periocidade do Evento:");
		lblNewLabel_3.setBounds(10, 124, 127, 14);
		panel.add(lblNewLabel_3);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Uma vez");
		rdbtnNewRadioButton.setBounds(141, 120, 75, 23);
		panel.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Semanal");
		rdbtnNewRadioButton_1.setBounds(226, 120, 75, 23);
		panel.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Mensal");
		rdbtnNewRadioButton_2.setBounds(303, 120, 109, 23);
		panel.add(rdbtnNewRadioButton_2);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Alarme");
		chckbxNewCheckBox.setBounds(2, 150, 97, 23);
		panel.add(chckbxNewCheckBox);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ação ao clicar no botão Salvar
				String descricao = DescricaoEvento.getText();
				String data = DatadoEvento.getText();
				String email = Email.getText();
				String periodicidade = "";
				String alarme = chckbxNewCheckBox.isSelected() ? "Sim" : "Não";
				String periodicidadeSelecionada = "";
				
				// Validação básica
				if (rdbtnNewRadioButton.isSelected()) {
					periodicidadeSelecionada = "Uma vez";
				} else if (rdbtnNewRadioButton_1.isSelected()) {
					periodicidadeSelecionada = "Semanal";
				} else if (rdbtnNewRadioButton_2.isSelected()) {
					periodicidadeSelecionada = "Mensal";
				} else {
					periodicidadeSelecionada = "Não selecionado";
				}
				// Adicionar nova linha na tabela
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{data, descricao, periodicidadeSelecionada, email, alarme});
				// Limpar campos após salvar
				DescricaoEvento.setText("");
				DatadoEvento.setText("dd/MM/yyyy");
				Email.setText("");
				rdbtnNewRadioButton.setSelected(false);
				rdbtnNewRadioButton_1.setSelected(false);
				rdbtnNewRadioButton_2.setSelected(false);
				chckbxNewCheckBox.setSelected(false);
				
			}
		});
		btnNewButton.setBounds(120, 150, 127, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Limpar");
		btnNewButton_1.setBounds(257, 150, 98, 23);
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("Lista de Eventos", null, panel_1, null);
		panel_1.setLayout(null);
		
		table = new JTable();
		table.setToolTipText("");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"Data", "Descri\u00E7\u00E3o", "Periodicidade", "E-mail", "Alarme"
			}
		));
		table.setBounds(10, 11, 603, 187);
		panel_1.add(table);

	}
}
