package br.mafia.client.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.mafia.client.musicas.Musica;
import jaco.mp3.player.MP3Player;

public class LogadoFrame {

	private JFrame frame;
	private Cliente cliente;
	private LoginFrame login;
	private JTextField textField;
	private DefaultTableModel model;
	private DefaultTableModel model_2;
	private JComboBox comboBox;
	private JTable table;
	private JTable table_2;
	private int musicaselecionada;
	private int linhaselecionada;
	private int reproduzindo;
	private JButton btnBaixar;
	private ArrayList<Musica> musicas;
	private MP3Player mp;
	private JButton btnBack;
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnNewButton;
	private JButton btnFoward;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public LogadoFrame(Cliente cliente, LoginFrame login) {
		this.cliente = cliente;
		this.login = login;
		this.musicaselecionada = -1;
		this.linhaselecionada = -1;
		initialize();
		this.mp = new MP3Player();
		this.frame.setVisible(true);
		this.loadMusicas();
		this.btnBack.setEnabled(false);
		this.btnPlay.setEnabled(false);
		this.btnPause.setEnabled(false);
		this.btnNewButton.setEnabled(false);
		this.btnFoward.setEnabled(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(27, 63, 696, 525);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Procurar músicas", null, panel, null);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(29, 34, 305, 19);
		panel.add(textField);
		textField.setColumns(10);
		
		String[] buscaspossiveis = {"Nome", "Artista"};
		comboBox = new JComboBox(buscaspossiveis);
		comboBox.setBounds(346, 31, 158, 24);
		panel.add(comboBox);
		
		JButton btnProcurar = new JButton("Procurar");
		btnProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procurar();
			}
		});
		btnProcurar.setBounds(516, 31, 146, 25);
		panel.add(btnProcurar);
		
		this.model = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		this.model.addColumn("Id"); 
		this.model.addColumn("Nome"); 
		this.model.addColumn("Artista");
		this.model.addColumn("Duração"); 
		this.model.addColumn("Tamanho");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(29, 81, 633, 356);
		panel.add(panel_2);
		
		table = new JTable(this.model);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (evt.getClickCount() == 1) {
		        	if (row >= 0 && col >= 0) {
		        		selecionamusica(String.valueOf(model.getValueAt(row, 0)));
		        	}
		        }
		    }
		});
		
		
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.removeColumn(table.getColumnModel().getColumn(0));
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane(table);
		panel_2.add(scrollPane);
		
		btnBaixar = new JButton("Baixar");
		btnBaixar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				baixarselecionada();
			}
		});
		btnBaixar.setEnabled(false);
		btnBaixar.setBounds(545, 449, 117, 25);
		panel.add(btnBaixar);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Minhas músicas", null, panel_1, null);
		panel_1.setLayout(null);
		
		this.btnBack = new JButton("back");
		this.btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skip_b();
			}
		});
		this.btnBack.setBounds(29, 36, 117, 25);
		panel_1.add(this.btnBack);
		
		this.btnPlay = new JButton("play");
		this.btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tocarMusica();
			}
		});
		this.btnPlay.setBounds(164, 36, 117, 25);
		panel_1.add(this.btnPlay);
		
		this.btnPause = new JButton("pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		this.btnPause.setBounds(299, 36, 117, 25);
		panel_1.add(this.btnPause);
		
		this.btnNewButton = new JButton("stop");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		this.btnNewButton.setBounds(428, 36, 117, 25);
		panel_1.add(this.btnNewButton);
		
		this.btnFoward = new JButton("foward");
		this.btnFoward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skip_f();
			}
		});
		this.btnFoward.setBounds(557, 36, 117, 25);
		panel_1.add(this.btnFoward);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(29, 87, 638, 356);
		panel_1.add(panel_3);
		
		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desconectar();
			}
		});
		btnDesconectar.setBounds(582, 26, 141, 25);
		frame.getContentPane().add(btnDesconectar);
		
		// segunda tabela
		
		this.model_2 = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		this.model_2.addColumn("Path"); 
		this.model_2.addColumn("Nome"); 
		this.model_2.addColumn("Artista");
		this.model_2.addColumn("Duração"); 
		
		table_2 = new JTable(this.model_2);
		table_2.setFillsViewportHeight(true);
		table_2.setAutoCreateRowSorter(true);
		
		
		table_2.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table_2.rowAtPoint(evt.getPoint());
		        int col = table_2.columnAtPoint(evt.getPoint());
		        if (evt.getClickCount() == 1) {
		        	if (row >= 0 && col >= 0) {
		        		selecionaLinha(row);
		        	}
		        } 
		        if(evt.getClickCount() == 2) {
		        	if (row >= 0 && col >= 0) {
		        		selecionaLinha(row);
		        		tocarMusica();
		        	}
		        }
		    }
		});
		
		
		table_2.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_2.getColumnModel().getColumn(1).setPreferredWidth(250);
		table_2.getColumnModel().getColumn(2).setPreferredWidth(20);
		table_2.getColumnModel().getColumn(3).setPreferredWidth(20);
		table_2.removeColumn(table_2.getColumnModel().getColumn(0));
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane(table_2);
		panel_3.add(scrollPane_2);
	}
	
	public void desconectar() {
		this.cliente.logout();
		this.frame.setVisible(false);
		this.login.v();
	}
	
	public void procurar() {
		this.btnBaixar.setEnabled(false);
		int linhastabela = this.table.getRowCount();
		for (int i = linhastabela - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}
		
		String busca = this.textField.getText();
		String mod = this.comboBox.getSelectedItem().toString();
		ArrayList<Musica> musicas;
		if(mod.equals("Nome")) {
			this.musicas = musicas = this.cliente.procurarMusicaNome(busca);
		} else {
			this.musicas = musicas = this.cliente.procurarMusicaArtista(busca);
		}
		Musica atual;
		int minutos, segundos;
		for(int i = 0; i < musicas.size(); i++) {
			atual = musicas.get(i);
			minutos = atual.getDuracao() / 60;
			segundos = atual.getDuracao() % 60;
			this.model.addRow(new Object[]{String.valueOf(atual.getId()), atual.getNome(), atual.getArtista(), minutos + ":" + segundos, this.tamstring(atual.getTam())});
		}
	}
	
	private String tamstring(long tam) {
		int kb = (int)(tam / 1024);
		int mb = kb / 1024;
		int gb = mb / 1024;
		String r = "";
		if(gb > 0) r = gb + " GB";
		else if(mb > 0) r = mb + " MB";
		else if(kb > 0) r = kb + " KB";
		else r = tam + " Bytes";
		return r;
	}
	
	public void selecionamusica(String id) {
		int idint = Integer.parseInt(id);
		this.musicaselecionada = idint;
		this.btnBaixar.setEnabled(true);
	}
	
	public void baixarselecionada() {
		if(this.cliente.getMusica(this.musicaselecionada) == null) {
			new DownloadFrame(this.getMusica(this.musicaselecionada), this.cliente);
		} else {
			JOptionPane.showMessageDialog(frame, "Você já possui esta música em sua biblioteca", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private Musica getMusica(int id) {
		Musica musica = null;
		Musica atual = null;
		for(int i = 0; i < this.musicas.size(); i++) {
			atual = this.musicas.get(i);
			if(atual.getId() == id) {
				musica = atual;
			}
		}
		return musica;
	}
	
	//repositório cliente
	
	public void loadMusicas() {
		ArrayList<Musica> musicas = this.cliente.getMusicasDisponiveis();
		Musica atual; int min, sec;
		for(int i = 0; i < musicas.size(); i++) {
			atual = musicas.get(i);
			min = atual.getDuracao() / 60;
			sec = atual.getDuracao() % 60;
			addMusica(atual.getPath(), atual.getNome(), atual.getArtista(), min + ":" + sec);
		}
	}
	
	public void addMusica(String path, String nome, String artista, String duracao) {
		this.model_2.addRow(new Object[]{path, nome, artista, duracao});
	}
	
	public void selecionaLinha(int linha) {
		this.linhaselecionada = linha;
		this.btnBack.setEnabled(true);
		this.btnPlay.setEnabled(true);
		this.btnPause.setEnabled(true);
		this.btnNewButton.setEnabled(true);
		this.btnFoward.setEnabled(true);
	}
	
	public void tocarMusica() {
		this.mp.stop();
		String path = String.valueOf(model_2.getValueAt(this.linhaselecionada, 0));
    	this.mp = new MP3Player(new File(this.cliente.getPastaMusicas() + File.separator + path));
    	this.reproduzindo = this.linhaselecionada;
    	int linhastabela = this.table_2.getRowCount();
    	for(int i = 0; i < linhastabela; i++) {
    		this.mp.addToPlayList(new File(this.cliente.getPastaMusicas() + File.separator + String.valueOf(model_2.getValueAt(i, 0))));
    	}
    	this.mp.play();
	}
	
	public void skip_b() {
		int linhastabela = this.table_2.getRowCount();
		if(linhastabela > 0) {
			this.linhaselecionada--;
			if(this.linhaselecionada == -1) this.linhaselecionada = linhastabela - 1;
			table_2.setRowSelectionInterval(this.linhaselecionada, this.linhaselecionada);
			this.tocarMusica();
		}
	}
	
	public void skip_f() {
		int linhastabela = this.table_2.getRowCount();
		if(linhastabela > 0) {
			this.linhaselecionada = (this.linhaselecionada + 1) % linhastabela;
			table_2.setRowSelectionInterval(this.linhaselecionada, this.linhaselecionada);
			this.tocarMusica();
		}
	}
	
	public void stop() {
		this.mp.stop();
	}
	
	public void pause() {
		this.mp.pause();
	}
}