package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import presenter.BienvenidaPresentador;

public class PantallaInicio extends JFrame {

	private BienvenidaPresentador bienvenidaPresentador;
	private JPanel panelFondo;
	private JLabel lblTitulo;
	private JLabel lblSubtitulo;
	private JLabel lblImagen;
	private JButton btnCargaManual;
	private JButton btnCargaAutomatica;

	public PantallaInicio(GestorPantallas gestorPantallas) {
		bienvenidaPresentador = new BienvenidaPresentador(gestorPantallas);

		configurarPantalla();
		crearLblTitulo();
		crearLblSubtitulo();
		crearBtnCargaManual();
		crearBtnCargaAutomatica();
		crearImagenFondo(); 
	}

	private void configurarPantalla() {
		setTitle("Diseñando regiones — Bienvenida");
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
		lblTitulo = new JLabel("Diseñando regiones", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(100, 120, 700, 50);
		panelFondo.add(lblTitulo);
	}

	private void crearLblSubtitulo() {
		lblSubtitulo = new JLabel("¿Cómo querés cargar los datos?", SwingConstants.CENTER);
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblSubtitulo.setForeground(new Color(241, 245, 249));
		lblSubtitulo.setBounds(100, 180, 700, 30);
		panelFondo.add(lblSubtitulo);
	}

	private void crearBtnCargaManual() {
		btnCargaManual = new JButton("Carga manual");
		btnCargaManual.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCargaManual.setForeground(Color.WHITE);
		btnCargaManual.setBackground(new Color(16, 185, 129));
		btnCargaManual.setFocusPainted(false);
		btnCargaManual.setBorderPainted(false);
		btnCargaManual.setOpaque(true);
		btnCargaManual.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCargaManual.setBounds(300, 250, 300, 50);

		agregarListenerBtnCargaManual();
		panelFondo.add(btnCargaManual);
	}

	private void agregarListenerBtnCargaManual() {
		btnCargaManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bienvenidaPresentador.manejarClickBotonCargaManual();
			}
		});
	}

	private void crearBtnCargaAutomatica() {
		btnCargaAutomatica = new JButton("Carga automática (archivo)");
		btnCargaAutomatica.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCargaAutomatica.setForeground(Color.WHITE);
		btnCargaAutomatica.setBackground(new Color(37, 99, 235));
		btnCargaAutomatica.setFocusPainted(false);
		btnCargaAutomatica.setBorderPainted(false);
		btnCargaAutomatica.setOpaque(true);
		btnCargaAutomatica.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCargaAutomatica.setBounds(300, 320, 300, 50);

		agregarListenerBtnCargaAutomatica();
		panelFondo.add(btnCargaAutomatica);
	}

	private void agregarListenerBtnCargaAutomatica() {
		btnCargaAutomatica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bienvenidaPresentador.manejarClickBotonCargaAutomatica();
			}
		});
	}

	private void crearImagenFondo() {
		URL url = getClass().getResource("/imagenes/23322.jpg");
		if (url == null) {
			return; 
		}
		lblImagen = new JLabel();
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

		Image escalada = new ImageIcon(url).getImage().getScaledInstance(900, 570, Image.SCALE_SMOOTH);
		lblImagen.setIcon(new ImageIcon(escalada));
		lblImagen.setBounds(0, 0, 900, 570);
		panelFondo.add(lblImagen);
	}
}