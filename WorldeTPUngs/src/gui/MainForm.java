package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Label;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import logic.LogicMain;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class MainForm {
	private JFrame frame;
	private JTextField txtAdivinar;
	private LogicMain game = new LogicMain();
	
	private static final char AciertoG = "G".charAt(0);
	private static final char EquivocacionY = "Y".charAt(0);
	private static final char ErrorB = "B".charAt(0);
	private static final Color colorGreen = new Color(0, 255, 0);
	private static final Color colorYellow = new Color(255, 255, 0);
	private static final Color colorBlack = new Color(64, 64, 64);
	private static final int constanteAcomodoLabels = 56;
	
	int lineaActiva = 0;
	Label[][] labels = new Label[7][5]; //Se cambia solo mas adelante por [maxIntentos][5]
	private JTextField textCantidadIntentos, textTiempo;
	private JLabel lblTiempo, lblEstado;
	private JButton btnAdivinar, btnIniciarJuego;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
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
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnAdivinar = new JButton("Adivinar");
		btnAdivinarListener(btnAdivinar);
		btnAdivinar.setBounds(100, 427, 89, 23);
		btnAdivinar.setEnabled(false);
		frame.getContentPane().add(btnAdivinar);
		
		txtAdivinar = new JTextField();
		txtAdivinar.setHorizontalAlignment(SwingConstants.CENTER);
		txtAdivinar.setBounds(100, 396, 89, 20);
		frame.getContentPane().add(txtAdivinar);
		txtAdivinar.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(299, 11, 275, 439);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cantidad de intentos (3-7):");
		lblNewLabel.setBounds(10, 11, 171, 14);
		panel.add(lblNewLabel);
		
		JLabel lblTiemposeg = new JLabel("Tiempo (60 - 600 Seg):");
		lblTiemposeg.setVisible(false);
		lblTiemposeg.setBounds(10, 70, 171, 14);
		panel.add(lblTiemposeg);
		
		
		btnIniciarJuego = new JButton("Iniciar juego");
		btnIniciarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarJuego();
			}
		});
		btnIniciarJuego.setBounds(90, 405, 112, 23);
		panel.add(btnIniciarJuego);
		
		lblTiempo = new JLabel("Tiempo disponible: ");
		lblTiempo.setVisible(false);
		lblTiempo.setBounds(10, 380, 192, 14);
		panel.add(lblTiempo);
		
		lblEstado = new JLabel("Estado: ");
		lblEstado.setBounds(10, 355, 192, 14);
		panel.add(lblEstado);
		
		textCantidadIntentos = new JTextField();
		textCantidadIntentos.setText("7");
		textCantidadIntentos.setBounds(191, 8, 60, 20);
		panel.add(textCantidadIntentos);
		textCantidadIntentos.setColumns(10);
		
		textTiempo = new JTextField();
		textTiempo.setVisible(false);
		textTiempo.setText("600");
		textTiempo.setColumns(10);
		textTiempo.setBounds(191, 67, 60, 20);
		panel.add(textTiempo);
		
	}

/*	=================================
 * 		        Funciones	
 * 	================================= */	
	
	private boolean txtIniciarJuegoValido() {
		if (textCantidadIntentos.getText() == null || textCantidadIntentos.getText() == "") return false;
		if (textTiempo.getText() == null || textTiempo.getText() == "") return false;
		if (Integer.parseInt(textCantidadIntentos.getText()) > 7 || Integer.parseInt(textCantidadIntentos.getText())< 3) return false;
		if (Integer.parseInt(textTiempo.getText()) < 59 || Integer.parseInt(textTiempo.getText()) > 601) return false;
		return true;
	}
		
	private void iniciarJuego(){
		if (txtIniciarJuegoValido()) {
			game = new LogicMain(Integer.parseInt(textCantidadIntentos.getText()), Integer.parseInt(textTiempo.getText()));
			limpiarTodo();
		}
		
	}
	
	private void limpiarTodo() {
		while (lineaActiva>0) {
			frame.remove(labels[lineaActiva-1][0]);
			frame.remove(labels[lineaActiva-1][1]);
			frame.remove(labels[lineaActiva-1][2]);
			frame.remove(labels[lineaActiva-1][3]);
			frame.remove(labels[lineaActiva-1][4]);
			lineaActiva--;
		}
		labels = null;
		labels = new Label[Integer.parseInt(textCantidadIntentos.getText())][5];
		btnAdivinar.setEnabled(true);
		lineaActiva = 0;	//Deberia autosetearse en el while, peeeeero....
	}

	/**
	 * Accion del boton de Adivinar.
	 * @param btnAdivinar
	 */
	private void btnAdivinarListener(JButton btnAdivinar) {
		btnAdivinar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtAdivinarValido(txtAdivinar.getText())) {
					//char[] palabr = txtAdivinar.getText().toUpperCase().toCharArray();
					char[] colors = game.intentarAdivinar(txtAdivinar.getText().toUpperCase()).toCharArray();
					agregarLinea(colors);
				}
				resolverEstadoPartida();
				txtAdivinar.setText(null);
			}

		});
	}
	
	private void agregarLinea(char[] colors) {
		crearLineaLabels();
		rellenarLineaWordle(colors, txtAdivinar.getText().toUpperCase());
		lineaActiva++;
	}

	private boolean txtAdivinarValido(String palabra) {
		if (txtAdivinar.getText().length() != 5) return false;
		
		StringBuilder sb = new StringBuilder();
		char[] chars = palabra.toCharArray();
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				sb.append(c);
			}
		}
		if (sb.length() != 5) return false;
		return true;
	}
	
	private void crearLineaLabels() {
		for (int i = 0; i < 5; i++) {
			labels[lineaActiva][i] = new Label("");
			labels[lineaActiva][i].setAlignment(Label.CENTER);
			labels[lineaActiva][i].setForeground(new Color(255, 255, 255));
			labels[lineaActiva][i].setBackground(new Color(192, 192, 192));
			labels[lineaActiva][i].setFont(new Font("Times New Roman", Font.BOLD, 40));
			labels[lineaActiva][i].setBounds(10 + i * constanteAcomodoLabels, 10 + lineaActiva * constanteAcomodoLabels, 50, 50);
			frame.getContentPane().add(labels[lineaActiva][i]);
		}
	}
	
	private void rellenarLineaWordle(char[] colors, String palabra) {
		int i = 0;
		while (i<colors.length) {
			Character c = palabra.charAt(i);
			labels[lineaActiva][i].setText(c.toString());
			if (colors[i] == AciertoG) {
				labels[lineaActiva][i].setBackground(colorGreen);
			}
			if (colors[i] == EquivocacionY) {
				labels[lineaActiva][i].setBackground(colorYellow);
			}
			if (colors[i] == ErrorB) {
				labels[lineaActiva][i].setBackground(colorBlack);
			}			
			i++;
		}
	}

	
	private void resolverEstadoPartida() {
		if (game.getEstadoPartida() == 0) {
			lblEstado.setText("Estado: Esperando");
		}
		if (game.getEstadoPartida() == 1) {
			lblEstado.setText("Estado: En partida...");
		}
		if (game.getEstadoPartida() == 2) {
			lblEstado.setText("Estado: ¡¡¡VICTORIA!!!");
			btnAdivinar.setEnabled(false);
		}
		if (game.getEstadoPartida() == 3) {
			lblEstado.setText("Estado: DERROTA...");
			btnAdivinar.setEnabled(false);
		}
	}
}


