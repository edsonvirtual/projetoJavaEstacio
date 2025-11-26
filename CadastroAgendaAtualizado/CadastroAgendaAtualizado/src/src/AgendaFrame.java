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
import javax.swing.table.TableColumn;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import java.util.List;

public class AgendaFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	protected static Object usuarioDAO;
	private JPanel contentPane;
	private JTextField DescricaoEvento;
	private JTextField DatadoEvento;
	private JTextField Email;
	private JTable table;
	private ConexaoPostgreSQL conexaoDB;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JCheckBox chckbxNewCheckBox;
	
	private JTabbedPane tabbedPane_1;
	private JButton btnNewButton; // Botão Salvar/Atualizar
	
	private Integer idEventoEmEdicao = null;
	

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

	public AgendaFrame() {
		conexaoDB = new ConexaoPostgreSQL();
		
		setBounds(100, 100, 664, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("tab1");
		tabbedPane.setBounds(10, 11, 5, 5);
		contentPane.add(tabbedPane);
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
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
		rdbtnNewRadioButton = new JRadioButton("Uma vez");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(141, 120, 75, 23);
		panel.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton_1 = new JRadioButton("Semanal");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(226, 120, 75, 23);
		panel.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_2 = new JRadioButton("Mensal");
		buttonGroup.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setBounds(303, 120, 109, 23);
		panel.add(rdbtnNewRadioButton_2);
		chckbxNewCheckBox = new JCheckBox("Alarme");
		chckbxNewCheckBox.setBounds(2, 150, 97, 23);
		panel.add(chckbxNewCheckBox);

		
		btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String descricao = DescricaoEvento.getText();
				String data = DatadoEvento.getText();
				String email = Email.getText();
				boolean alarme = chckbxNewCheckBox.isSelected();

				if (data.trim().isEmpty() || data.equals("dd/MM/yyyy")) {
					JOptionPane.showMessageDialog(panel, "Por favor, digite uma data válida!", "Erro de Data", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				String periodicidadeSelecionada = "";
				if (rdbtnNewRadioButton.isSelected()) periodicidadeSelecionada = "Uma vez";
				else if (rdbtnNewRadioButton_1.isSelected()) periodicidadeSelecionada = "Semanal";
				else if (rdbtnNewRadioButton_2.isSelected()) periodicidadeSelecionada = "Mensal";
				else {
					JOptionPane.showMessageDialog(panel, "Por favor, selecione uma periodicidade.", "Erro", JOptionPane.WARNING_MESSAGE);
					return; // <--- ERRO ESTAVA AQUI
				}
				
				try {
					
					if (idEventoEmEdicao == null) {
						conexaoDB.inserirEvento(descricao, data, email, periodicidadeSelecionada, alarme);
						JOptionPane.showMessageDialog(panel, "Evento salvo com sucesso!");
					} else {
						conexaoDB.atualizarEvento(idEventoEmEdicao, descricao, data, email, periodicidadeSelecionada, alarme);
						JOptionPane.showMessageDialog(panel, "Evento atualizado com sucesso!");
					}
					
					limparCampos();
					atualizarTabela();
					tabbedPane_1.setSelectedIndex(1); // <--- ERRO ESTAVA AQUI
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(120, 150, 127, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Limpar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnNewButton_1.setBounds(257, 150, 98, 23);
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("Lista de Eventos", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 603, 310);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setToolTipText("");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"ID", "Data", "Descrição", "Periodicidade", "E-mail", "Alarme"
			}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
		scrollPane.setViewportView(table);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarEventoParaEdicao();
			}
		});
		btnEditar.setBounds(10, 332, 89, 23);
		panel_1.add(btnEditar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirEventoSelecionado();
			}
		});
		btnExcluir.setBounds(109, 332, 89, 23);
		panel_1.add(btnExcluir);
		
		atualizarTabela();
	}
	
	private int getSelectedEventId() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Por favor, selecione um evento na tabela primeiro.", "Nenhum evento selecionado", JOptionPane.WARNING_MESSAGE);
			return -1;
		}
		return (Integer) table.getModel().getValueAt(selectedRow, 0);
	}
	
	private void excluirEventoSelecionado() {
		int idParaDeletar = getSelectedEventId();
		if (idParaDeletar == -1) {
			return;
		}
		
		// <--- ERROS ESTAVAM AQUI
		int confirmacao = JOptionPane.showConfirmDialog(this,
				"Tem certeza que deseja excluir este evento?",
				"Confirmar exclusão",
				JOptionPane.YES_NO_OPTION);
		
		if (confirmacao == JOptionPane.YES_OPTION) {
			conexaoDB.deletarEvento(idParaDeletar);
			atualizarTabela();
			JOptionPane.showMessageDialog(this, "Evento excluído com sucesso.");
		}
	}
	
	private void carregarEventoParaEdicao() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Por favor, selecione um evento na tabela primeiro.", "Nenhum evento selecionado", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		this.idEventoEmEdicao = (Integer) model.getValueAt(selectedRow, 0);
		String data = (String) model.getValueAt(selectedRow, 1);
		String descricao = (String) model.getValueAt(selectedRow, 2);
		String periodicidade = (String) model.getValueAt(selectedRow, 3);
		String email = (String) model.getValueAt(selectedRow, 4);
		String alarmeStr = (String) model.getValueAt(selectedRow, 5);
		
		DescricaoEvento.setText(descricao);
		DatadoEvento.setText(data);
		Email.setText(email);
		chckbxNewCheckBox.setSelected(alarmeStr.equals("Sim"));
		
		if (periodicidade.equals("Uma vez")) rdbtnNewRadioButton.setSelected(true);
		else if (periodicidade.equals("Semanal")) rdbtnNewRadioButton_1.setSelected(true);
		else if (periodicidade.equals("Mensal")) rdbtnNewRadioButton_2.setSelected(true);
		
		btnNewButton.setText("Atualizar");
		tabbedPane_1.setSelectedIndex(0);
	}

	
	private void atualizarTabela() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // <--- ERRO ESTAVA AQUI
		
		List<Object[]> eventos = conexaoDB.listarEventos();
		for (Object[] evento : eventos) {
			model.addRow(evento);
		}
		
		TableColumn idColumn = table.getColumnModel().getColumn(0);
		idColumn.setMinWidth(0);
		idColumn.setMaxWidth(0);
		idColumn.setWidth(0);
	}
	
	private void limparCampos() {
		DescricaoEvento.setText("");
		DatadoEvento.setText("dd/MM/yyyy");
		Email.setText("");
		buttonGroup.clearSelection();
		chckbxNewCheckBox.setSelected(false);
		
		this.idEventoEmEdicao = null;
		btnNewButton.setText("Salvar");
	}
}