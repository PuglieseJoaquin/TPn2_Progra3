package vista;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import presenter.CargarDatosPresentador;
import presenter.TipoPantallaDeCarga;

public class PantallaCargarDatosManual extends VistaBaseCompartida implements CargarDatosVista {

	private CargarDatosPresentador cargarDatosPresentador;
	
	private JPanel panelFondo;
	private JLabel lblTitulo, lblNombreVertice, lblOrigen, lblDestino, lblPeso, lblRegiones;
	private JTextField textNombreVertice, textPeso;
	private JButton btnAgregarVertice, btnAgregarArista, btnCalcular, btnVolverAlMenu, btnSalir;
	private DefaultTableModel modeloVertices, modeloAristas;
	private JTable tablaVertices, tablaAristas;
	private DefaultComboBoxModel<String> modeloOrigen, modeloDestino;
	private JComboBox<String> comboOrigen, comboDestino;
	private JSpinner spinnerRegiones;
	
	private TipoPantallaDeCarga origenDeDatos;
	
	private static final String eliminar = "X";

	public PantallaCargarDatosManual(InterfazGestorPantalla gestorInterfaz) {
		cargarDatosPresentador = new CargarDatosPresentador(this, gestorInterfaz);
		origenDeDatos = TipoPantallaDeCarga.MANUAL;
		
		configurarPantalla();
		crearLblTitulo();
		crearLblNombreVertice();
		crearTextNombreVertice();
		crearBtnAgregarVertice();
		crearTablaVertices();
		crearLblOrigen();
		crearComboOrigen();
		crearLblDestino();
		crearComboDestino();
		crearLblPeso();
		crearTextPeso();
		crearBtnAgregarArista();
		crearTablaAristas();
		crearLblRegiones();
		crearSpinnerRegiones();
		crearBtnVolverAlMenu();
		crearBtnCalcular();
		crearBtnSalir();
	}

	private void configurarPantalla() {
		setTitle("Diseñando regiones — Carga de datos");
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
		lblTitulo = new JLabel("CARGA MANUAL DE DATOS", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTitulo.setForeground(new Color(248, 250, 252));
		lblTitulo.setBounds(50, 20, 800, 35);
		panelFondo.add(lblTitulo);
	}

	private void crearLblNombreVertice() {
		lblNombreVertice = new JLabel("1. NOMBRE DEL VÉRTICE (PROVINCIA):");
		lblNombreVertice.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNombreVertice.setForeground(new Color(203, 213, 225));
		lblNombreVertice.setBounds(40, 80, 360, 22);
		panelFondo.add(lblNombreVertice);
	}

	private void crearTextNombreVertice() {
		textNombreVertice = new JTextField();
		textNombreVertice.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textNombreVertice.setForeground(new Color(15, 23, 42));
		textNombreVertice.setBackground(new Color(241, 245, 249));
		textNombreVertice.setBounds(40, 108, 220, 34);
		panelFondo.add(textNombreVertice);
	}

	private void crearBtnAgregarVertice() {
		btnAgregarVertice = new JButton("Agregar vértice");
		estiloBotonPrimario(btnAgregarVertice);
		btnAgregarVertice.setBounds(270, 108, 130, 34);

		agregarListenerBtnAgregarVertice();
		panelFondo.add(btnAgregarVertice);
	}

	private void agregarListenerBtnAgregarVertice() {
		btnAgregarVertice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatosPresentador.manejarClickAgregarVertice(textNombreVertice.getText());
			}
		});
	}

	private void crearTablaVertices() {
		modeloVertices = crearModeloNoEditable(new String[] { "Vértice", "" });
		tablaVertices = new JTable(modeloVertices);
		estiloTabla(tablaVertices);
		configurarColumnaEliminar(tablaVertices);

		agregarListenerEliminarVertice();

		JScrollPane scroll = new JScrollPane(tablaVertices);
		scroll.getViewport().setBackground(new Color(15, 23, 42));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85)));
		scroll.setBounds(40, 160, 360, 290);
		panelFondo.add(scroll);
	}

	private void agregarListenerEliminarVertice() {
		tablaVertices.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tablaVertices.rowAtPoint(e.getPoint());
				int columna = tablaVertices.columnAtPoint(e.getPoint());
				if (fila >= 0 && columna == tablaVertices.getColumnCount() - 1) {
					String nombre = (String) modeloVertices.getValueAt(fila, 0);
					cargarDatosPresentador.manejarClickEliminarVertice(nombre);
				}
			}
		});
		agregarCursorManoSobreCruz(tablaVertices);
	}

	private void crearLblOrigen() {
		lblOrigen = new JLabel("2. ORIGEN:");
		estiloEtiqueta(lblOrigen);
		lblOrigen.setBounds(460, 80, 180, 22);
		panelFondo.add(lblOrigen);
	}

	private void crearComboOrigen() {
		modeloOrigen = new DefaultComboBoxModel<String>();
		comboOrigen = new JComboBox<String>(modeloOrigen);
		estiloCombo(comboOrigen);
		comboOrigen.setBounds(460, 108, 180, 34);
		panelFondo.add(comboOrigen);
	}

	private void crearLblDestino() {
		lblDestino = new JLabel("DESTINO:");
		estiloEtiqueta(lblDestino);
		lblDestino.setBounds(660, 80, 180, 22);
		panelFondo.add(lblDestino);
	}

	private void crearComboDestino() {
		modeloDestino = new DefaultComboBoxModel<String>();
		comboDestino = new JComboBox<String>(modeloDestino);
		estiloCombo(comboDestino);
		comboDestino.setBounds(660, 108, 180, 34);
		panelFondo.add(comboDestino);
	}

	private void crearLblPeso() {
		lblPeso = new JLabel("PESO (SIMILARIDAD):");
		estiloEtiqueta(lblPeso);
		lblPeso.setBounds(460, 150, 180, 22);
		panelFondo.add(lblPeso);
	}

	private void crearTextPeso() {
		textPeso = new JTextField();
		textPeso.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textPeso.setForeground(new Color(15, 23, 42));
		textPeso.setBackground(new Color(241, 245, 249));
		textPeso.setHorizontalAlignment(SwingConstants.CENTER);
		textPeso.setBounds(460, 175, 120, 34);
		panelFondo.add(textPeso);
	}

	private void crearBtnAgregarArista() {
		btnAgregarArista = new JButton("Agregar arista");
		estiloBotonPrimario(btnAgregarArista);
		btnAgregarArista.setBounds(600, 175, 240, 34);

		agregarListenerBtnAgregarArista();
		panelFondo.add(btnAgregarArista);
	}

	private void agregarListenerBtnAgregarArista() {
		btnAgregarArista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String origen = (String) comboOrigen.getSelectedItem();
				String destino = (String) comboDestino.getSelectedItem();
				cargarDatosPresentador.manejarClickAgregarArista(origen, destino, textPeso.getText());
			}
		});
	}

	private void crearTablaAristas() {
		modeloAristas = crearModeloNoEditable(new String[]{ "Origen", "Destino", "Peso", "" });
		tablaAristas = new JTable(modeloAristas);
		estiloTabla(tablaAristas);
		configurarColumnaEliminar(tablaAristas);

		agregarListenerEliminarArista();

		JScrollPane scroll = new JScrollPane(tablaAristas);
		scroll.getViewport().setBackground(new Color(15, 23, 42));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85)));
		scroll.setBounds(460, 225, 380, 225);
		panelFondo.add(scroll);
	}

	private void agregarListenerEliminarArista() {
		tablaAristas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tablaAristas.rowAtPoint(e.getPoint());
				int columna = tablaAristas.columnAtPoint(e.getPoint());
				if (fila >= 0 && columna == tablaAristas.getColumnCount() - 1) {
					String origen = (String) modeloAristas.getValueAt(fila, 0);
					String destino = (String) modeloAristas.getValueAt(fila, 1);
					cargarDatosPresentador.manejarClickEliminarArista(origen, destino);
				}
			}
		});
		
		agregarCursorManoSobreCruz(tablaAristas);
	}

	private void crearLblRegiones() {
		lblRegiones = new JLabel("3. CANTIDAD DE REGIONES:");
		estiloEtiqueta(lblRegiones);
		lblRegiones.setBounds(40, 480, 220, 25);
		panelFondo.add(lblRegiones);
	}

	private void crearSpinnerRegiones() {
		spinnerRegiones = new JSpinner(new SpinnerNumberModel(2, 1, 999, 1));
		spinnerRegiones.setFont(new Font("Segoe UI", Font.BOLD, 14));
		spinnerRegiones.setBounds(260, 477, 70, 32);
		panelFondo.add(spinnerRegiones);
	}

	private void crearBtnVolverAlMenu() {
		btnVolverAlMenu = new JButton("Volver al menú");
		btnVolverAlMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnVolverAlMenu.setForeground(new Color(241, 245, 249));
		btnVolverAlMenu.setBackground(new Color(51, 65, 85));
		btnVolverAlMenu.setFocusPainted(false);
		btnVolverAlMenu.setBounds(470, 470, 170, 42);

		agregarListenerBtnVolverAlMenu();
		panelFondo.add(btnVolverAlMenu);
	}

	private void agregarListenerBtnVolverAlMenu() {
		btnVolverAlMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatosPresentador.manejarClickVolverAlMenu();
			}
		});
	}

	private void crearBtnCalcular() {
		btnCalcular = new JButton("Calcular regiones");
		btnCalcular.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnCalcular.setForeground(Color.WHITE);
		btnCalcular.setBackground(new Color(16, 185, 129));
		btnCalcular.setFocusPainted(false);
		btnCalcular.setBorderPainted(false);
		btnCalcular.setOpaque(true);
		btnCalcular.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCalcular.setBounds(660, 470, 180, 42);

		agregarListenerBtnCalcular();
		panelFondo.add(btnCalcular);
	}

	private void agregarListenerBtnCalcular() {
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cantidadRegiones = (Integer) spinnerRegiones.getValue();
				cargarDatosPresentador.manejarClickCalcular(cantidadRegiones, origenDeDatos);
			}
		});
	}

	private DefaultTableModel crearModeloNoEditable(String[] columnas) {
		return new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
	}

	private void estiloTabla(JTable tabla) {
		tabla.setShowVerticalLines(false);
		tabla.setGridColor(new Color(51, 65, 85));
		tabla.setBackground(new Color(15, 23, 42));
		tabla.setForeground(new Color(241, 245, 249));
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tabla.setRowHeight(28);
		tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tabla.getTableHeader().setBackground(new Color(51, 65, 85));
		tabla.getTableHeader().setForeground(new Color(248, 250, 252));
	}

	private void configurarColumnaEliminar(JTable tabla) {
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		render.setBackground(new Color(15, 23, 42));
		render.setForeground(new Color(239, 68, 68));
		render.setFont(new Font("Segoe UI", Font.BOLD, 15));

		int ultima = tabla.getColumnCount() - 1;
		tabla.getColumnModel().getColumn(ultima).setCellRenderer(render);
		tabla.getColumnModel().getColumn(ultima).setMinWidth(45);
		tabla.getColumnModel().getColumn(ultima).setMaxWidth(45);
	}

	private void agregarCursorManoSobreCruz(final JTable tabla) {
		tabla.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int fila = tabla.rowAtPoint(e.getPoint());
				int columna = tabla.columnAtPoint(e.getPoint());
				boolean sobreCruz = fila >= 0 && columna == tabla.getColumnCount() - 1;
				tabla.setCursor(Cursor.getPredefinedCursor(sobreCruz ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
			}
		});
	}

	private void estiloEtiqueta(JLabel label) {
		label.setFont(new Font("Segoe UI", Font.BOLD, 13));
		label.setForeground(new Color(203, 213, 225));
	}

	private void estiloCombo(JComboBox<String> combo) {
		combo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		combo.setForeground(new Color(15, 23, 42));
		combo.setBackground(new Color(241, 245, 249));
	}

	private void estiloBotonPrimario(JButton boton) {
		boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color(37, 99, 235));
		boton.setFocusPainted(false);
		boton.setBorderPainted(false);
		boton.setOpaque(true);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
	    btnSalir.setBounds(360, 515, 180, 40);

	    agregarListenerBtnSalir();

	    panelFondo.add(btnSalir);
	}

	private void agregarListenerBtnSalir() {
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatosPresentador.manejarClickBtnSalir();
			}
	    });
	}
    
	@Override
	public void agregarVertice(String nombre) {
		modeloVertices.addRow(new Object[] { nombre, eliminar });
		modeloOrigen.addElement(nombre);
		modeloDestino.addElement(nombre);
	}

	@Override
	public void agregarArista(String origen, String destino, String peso) {
		modeloAristas.addRow(new Object[] { origen, destino, peso, eliminar });
	}

	@Override
	public void limpiarCampoVertice() {
		textNombreVertice.setText("");
		textNombreVertice.requestFocusInWindow();
	}

	@Override
	public void limpiarCampoPeso() {
		textPeso.setText("");
	}

	@Override
	public void eliminarVertice(String nombre) {
		for (int i = modeloVertices.getRowCount() - 1; i >= 0; i--) {
			if (modeloVertices.getValueAt(i, 0).equals(nombre)) {
				modeloVertices.removeRow(i);
			}
		}
		modeloOrigen.removeElement(nombre);
		modeloDestino.removeElement(nombre);
	}

	@Override
	public void eliminarArista(String origen, String destino) {
		for (int i = modeloAristas.getRowCount() - 1; i >= 0; i--) {
			if (modeloAristas.getValueAt(i, 0).equals(origen) && modeloAristas.getValueAt(i, 1).equals(destino)) {
				modeloAristas.removeRow(i);
			}
		}
	}
}
