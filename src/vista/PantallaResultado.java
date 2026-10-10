package vista;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.openstreetmap.gui.jmapviewer.*;

import presenter.ResultadoPresentador;

public class PantallaResultado extends JFrame implements ResultadoVista {

	private ResultadoPresentador resultadoPresentador;
	private JPanel panelFondo;
	private JLabel lblTitulo, lblCantidadRegiones;
	private JTextArea txtResultado;
	private JSpinner spinnerRegiones;
	private JButton btnRecalcular, btnVolverAlMenu, btnSalir, btnModificarDatos;
    private JMapViewer mapa; 
    private List<MapPolygonImpl> lineasDibujadas = new ArrayList<>();
    private List<MapMarkerDot> verticesDibujados = new ArrayList<>();

	public PantallaResultado(InterfazGestorPantalla gestorInterfaz) {

		configurarPantalla();
		crearLblTitulo();
		crearTxtResultado();
		crearLblCantidadRegiones();
		crearSpinnerRegiones();
		crearBtnRecalcular();
		crearBtnVolverAlMenu();
		crearBtnSalir();
		crearBtnModificarDatos();
		crearMapa();
	}

	public void setPresentador(ResultadoPresentador resultadoPresentador) {
		this.resultadoPresentador = resultadoPresentador;
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
		scroll.setBounds(50, 61, 294, 410);
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
		spinnerRegiones.setBounds(281, 495, 70, 32);
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
		btnRecalcular.setBounds(355, 490, 135, 40);

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
		btnVolverAlMenu.setBounds(642, 491, 140, 40);

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
	    btnSalir.setBackground(new Color(239, 68, 68));
	    btnSalir.setFocusPainted(false);
	    btnSalir.setBorderPainted(false);
	    btnSalir.setOpaque(true);
	    btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    btnSalir.setBounds(786, 491, 88, 40);

	    agregarListenerBtnSalir();

	    panelFondo.add(btnSalir);
	}

	private void agregarListenerBtnSalir() {
		btnSalir.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	resultadoPresentador.manejarClickBtnSalir();
	        }
	    });
	}
	
	private void crearBtnModificarDatos() {
		btnModificarDatos = new JButton("Modificar datos");  
	    btnModificarDatos.setOpaque(true);
	    btnModificarDatos.setForeground(Color.WHITE);
	    btnModificarDatos.setFont(new Font("Segoe UI", Font.BOLD, 15));
	    btnModificarDatos.setFocusPainted(false);
	    btnModificarDatos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    btnModificarDatos.setBorderPainted(false);
	    btnModificarDatos.setBackground(new Color(255, 128, 64));
	    btnModificarDatos.setBounds(495, 490, 142, 40);
	    panelFondo.add(btnModificarDatos);
	    
	    agregarListenerBtnModificarDatos();
	    
	    panelFondo.add(btnSalir);
	}

	private void agregarListenerBtnModificarDatos() {
		btnModificarDatos.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	resultadoPresentador.manejarClickModificarDatos();
	        }
	    });
	}
	
    private void crearMapa() {
	    mapa = new JMapViewer();
	    mapa.setBounds(395, 61, 460, 410);
	    mapa.setDisplayPosition(new Coordinate(-38.4161, -63.6167), 4);
	
	    panelFondo.add(mapa);
    }

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

	@Override
	public void dibujarConexion(double origenLat, double origenLon, double destinoLat, double destinoLon) {

		Coordinate coordOrigen = new Coordinate (origenLat, origenLon);
		Coordinate coordDestino = new Coordinate (destinoLat, destinoLon);
		
		java.util.List<Coordinate> coords = Arrays.asList(
                coordOrigen,
                coordDestino,
                coordDestino);
		
        MapPolygonImpl line = new MapPolygonImpl(coords);
        
        line.setColor(Color.black);
        mapa.addMapPolygon(line);
        lineasDibujadas.add(line);
	}
	
	@Override
	public void dibujarVertice(String nombre, double lat, double lon) {
		
		Coordinate coord = new Coordinate(lat, lon);
		MapMarkerDot nuevo = new MapMarkerDot(nombre, coord);
		nuevo.setColor(Color.MAGENTA);
		nuevo.setBackColor(Color.PINK);
		mapa.addMapMarker(nuevo);
		verticesDibujados.add(nuevo);
	}

	@Override
	public void borrarDibujosEnMapa() {
	    for (MapPolygonImpl linea : lineasDibujadas) {
	        mapa.removeMapPolygon(linea);
	    }
	    lineasDibujadas.clear();

	    for (MapMarkerDot vertice : verticesDibujados) {
	        mapa.removeMapMarker(vertice);
	    }
	    verticesDibujados.clear();
	}

	@Override
	public void mostrarDatosSinMapa() {
	    mapa.setVisible(false);
	    
	    
		URL url = getClass().getResource("/imagenes/23322.jpg");
		if (url == null) {
			return; 
		}
		JLabel lblImagen = new JLabel();
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

		Image escalada = new ImageIcon(url).getImage().getScaledInstance(510, 410, Image.SCALE_SMOOTH);
		lblImagen.setIcon(new ImageIcon(escalada));
		lblImagen.setBounds(395, 60, 460, 410);
		panelFondo.add(lblImagen);
		panelFondo.setComponentZOrder(lblImagen, 0);
	}
}
