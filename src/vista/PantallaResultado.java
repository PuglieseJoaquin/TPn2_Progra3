package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import modelo.Grafo;
import presenter.ResultadoPresentador;

public class PantallaResultado extends JFrame implements ResultadoVista {

	private ResultadoPresentador resultadoPresentador;
	private JPanel panelFondo;
	private JLabel lblTitulo;
	private JTextArea txtResultado;
	private JLabel lblCantidadRegiones;
	private JSpinner spinnerRegiones;
	private JButton btnRecalcular;
	private JButton btnVolverAlMenu;
	private JButton btnSalir;

	public PantallaResultado(GestorPantallas gestorPantallas, Grafo grafo, int cantidadRegiones) {
		resultadoPresentador = new ResultadoPresentador(this, gestorPantallas, grafo, cantidadRegiones);

		configurarPantalla();
		crearLblTitulo();
		crearTxtResultado();
		crearLblCantidadRegiones();
		crearSpinnerRegiones();
		crearBtnRecalcular();
		crearBtnVolverAlMenu();
		crearBtnSalir();

		resultadoPresentador.actualizarVista();
	}

	private void configurarPantalla() {
		setTitle("Diseñando regiones — Resultado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		panelFondo = new JPanel();
		panelFondo.setBackground(new Color(30, 41, 59));
		setContentPane(panelFondo);
		panelFondo.setLayout(null);
	}

	private void crearLblTitulo() {
		lblTitulo = new JLabel("REGIONES RESULTANTES", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTitulo.setForeground(new Color(248, 250, 252));
		lblTitulo.setBounds(50, 15, 800, 35);
		panelFondo.add(lblTitulo);
	}

	private void crearTxtResultado() {
		txtResultado = new JTextArea();
		txtResultado.setEditable(false);
		txtResultado.setLineWrap(true);
		txtResultado.setWrapStyleWord(true);
		txtResultado.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtResultado.setBackground(new Color(15, 23, 42));
		txtResultado.setForeground(new Color(241, 245, 249));
		txtResultado.setMargin(new Insets(12, 12, 12, 12));

		JScrollPane scroll = new JScrollPane(txtResultado);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85)));
		scroll.setBounds(50, 65, 800, 410);
		panelFondo.add(scroll);
	}

	private void crearLblCantidadRegiones() {
		lblCantidadRegiones = new JLabel("CAMBIAR CANTIDAD DE REGIONES:");
		lblCantidadRegiones.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblCantidadRegiones.setForeground(new Color(203, 213, 225));
		lblCantidadRegiones.setBounds(50, 500, 250, 25);
		panelFondo.add(lblCantidadRegiones);
	}

	private void crearSpinnerRegiones() {
		spinnerRegiones = new JSpinner(new SpinnerNumberModel(2, 1, 999, 1));
		spinnerRegiones.setFont(new Font("Segoe UI", Font.BOLD, 14));
		spinnerRegiones.setBounds(305, 497, 70, 32);
		panelFondo.add(spinnerRegiones);
	}

	private void crearBtnRecalcular() {
		btnRecalcular = new JButton("Recalcular");
		btnRecalcular.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnRecalcular.setForeground(Color.WHITE);
		btnRecalcular.setBackground(new Color(16, 185, 129));
		btnRecalcular.setFocusPainted(false);
		btnRecalcular.setBorderPainted(false);
		btnRecalcular.setOpaque(true);
		btnRecalcular.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnRecalcular.setBounds(390, 492, 140, 40);

		agregarListenerBtnRecalcular();
		panelFondo.add(btnRecalcular);
	}

	private void agregarListenerBtnRecalcular() {
		btnRecalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nuevaCantidad = (Integer) spinnerRegiones.getValue();
				resultadoPresentador.manejarClickRecalcular(nuevaCantidad);
			}
		});
	}

	private void crearBtnVolverAlMenu() {
		btnVolverAlMenu = new JButton("Volver al menú");
		btnVolverAlMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnVolverAlMenu.setForeground(new Color(241, 245, 249));
		btnVolverAlMenu.setBackground(new Color(51, 65, 85));
		btnVolverAlMenu.setFocusPainted(false);
		btnVolverAlMenu.setBounds(680, 492, 170, 40);

		agregarListenerBtnVolverAlMenu();
		panelFondo.add(btnVolverAlMenu);
	}

	private void agregarListenerBtnVolverAlMenu() {
		btnVolverAlMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultadoPresentador.manejarClickVolverAlMenu();
			}
		});
	}
	
	private void crearBtnSalir() {
	    btnSalir = new JButton("Salir");
	    btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
	    btnSalir.setForeground(Color.WHITE);
	    btnSalir.setBackground(new Color(239, 68, 68)); // rojo
	    btnSalir.setFocusPainted(false);
	    btnSalir.setBorderPainted(false);
	    btnSalir.setOpaque(true);
	    btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    btnSalir.setBounds(550, 492, 100, 40); // centrado abajo

	    agregarListenerBtnSalir();

	    panelFondo.add(btnSalir);
	}

	private void agregarListenerBtnSalir() {
		btnSalir.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            System.exit(0);
	        }
	    });
	}

	// ------------------- MÉTODOS QUE LLAMA EL PRESENTADOR -------------------

	@Override
	public void mostrarResultado(String resultado) {
		txtResultado.setText(resultado);
		txtResultado.setCaretPosition(0);
	}

	@Override
	public void setCantidadRegiones(int cantidad) {
		spinnerRegiones.setValue(cantidad);
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Dato no válido", JOptionPane.ERROR_MESSAGE);
	}
}