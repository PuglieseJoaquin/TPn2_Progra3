package vista;

import org.openstreetmap.gui.jmapviewer.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import presenter.CargarDatosPresentador;
import presenter.TipoPantallaDeCarga;

public class PantallaCargarDatosDesdeJSON extends VistaBaseCompartida implements CargarDatosVista{

    private CargarDatosPresentador cargarDatosPresentador;
    
    private JPanel panelFondo;
    private JLabel lblTitulo, lblMapa, lblOrigen, lblDestino, lblPeso, lblRegiones, lblElegirMapa;
    private DefaultTableModel modeloAristas;
	private JSpinner spinnerRegiones;
	private JButton btnVolverAlMenu, btnCalcular, btnSalir, btnAgregarArista, btnCargarMapa;
    private JComboBox<String> comboOrigen, comboDestino, comboElegirMapa;
    private JTextField textPeso;
    private JTable tablaAristas;
    private JMapViewer mapa;
	private TipoPantallaDeCarga origenDeDatos;  
    private Map<String, MapPolygonImpl> lineas = new HashMap<>();
    private Map<String, Coordinate> coordsGuardadas = new HashMap<>();   
	private static final String eliminar = "X";

    public PantallaCargarDatosDesdeJSON(InterfazGestorPantalla gestorInterfaz) {
        
        configurarPantalla();
        crearLblTitulo();   
        crearLblMapa();
        crearMapa();        
        crearLblOrigen();
		crearComboOrigen();
		crearLblDestino();
		crearComboDestino();
		crearLblPeso();
		crearTxtPeso();
		crearBtnAgregarArista();
		crearTablaAristas();
		crearLblRegiones();
		crearSpinnerRegiones();
		crearBtnVolverAlMenu();
		crearBtnCalcular();
		crearBtnSalir();
		crearLblElegirMapa();
		crearComboElegirMapa();
		crearBotonCargarMapa();

		cargarDatosPresentador = new CargarDatosPresentador(this, gestorInterfaz);
		origenDeDatos = TipoPantallaDeCarga.JSON;
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
		lblTitulo = new JLabel("CARGA DE DATOS DESDE JSON", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTitulo.setForeground(new Color(248, 250, 252));
		lblTitulo.setBounds(50, 20, 490, 35);
		panelFondo.add(lblTitulo);
	}
	
	private void crearLblMapa() {
		lblMapa = new JLabel("1. CAPITALES PROVINCIALES EN EL MAPA:");
		estiloEtiqueta(lblMapa);
		lblMapa.setBounds(30, 80, 460, 22);
		panelFondo.add(lblMapa);
	}

    private void crearMapa() {
    		mapa = new JMapViewer();
		mapa.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85)));
		mapa.setBounds(30, 108, 460, 340);

		mapa.setDisplayPosition(new Point(230, 170),new Coordinate(-38.4161, -63.6167),3);
		panelFondo.add(mapa);
    }
    
    private void crearLblOrigen() {
		lblOrigen = new JLabel("2. ORIGEN:");
		estiloEtiqueta(lblOrigen);
		lblOrigen.setBounds(495, 80, 175, 22);
		panelFondo.add(lblOrigen);
	}

	private void crearComboOrigen() {
		comboOrigen = new JComboBox<String>();
		estiloCombo(comboOrigen);
		comboOrigen.setBounds(495, 108, 175, 34);
		panelFondo.add(comboOrigen);
	}

	private void crearLblDestino() {
		lblDestino = new JLabel("3. DESTINO:");
		estiloEtiqueta(lblDestino);
		lblDestino.setBounds(685, 80, 175, 22);
		panelFondo.add(lblDestino);
	}

	private void crearComboDestino() {
		comboDestino = new JComboBox<String>();
		estiloCombo(comboDestino);
		comboDestino.setBounds(685, 108, 175, 34);
		panelFondo.add(comboDestino);
	}

	private void crearLblPeso() {
		lblPeso = new JLabel("PESO (SIMILARIDAD):");
		estiloEtiqueta(lblPeso);
		lblPeso.setBounds(495, 150, 255, 22);
		panelFondo.add(lblPeso);
	}

	private void crearTxtPeso() {
		textPeso = new JTextField();
		textPeso.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textPeso.setForeground(new Color(15, 23, 42));
		textPeso.setBackground(new Color(241, 245, 249));
		textPeso.setHorizontalAlignment(SwingConstants.CENTER);
		textPeso.setBounds(495, 175, 120, 34);
		panelFondo.add(textPeso);
	}

	private void crearBtnAgregarArista() {
		btnAgregarArista = new JButton("Agregar arista");
		estiloBotonPrimario(btnAgregarArista);
		btnAgregarArista.setBounds(630, 175, 235, 34);

		agregarListenerBtnAgregarArista();
		
		panelFondo.add(btnAgregarArista);
	}

	private void agregarListenerBtnAgregarArista() {
		btnAgregarArista.addActionListener(e -> {
            try {
	            	String origen = (String) comboOrigen.getSelectedItem();
	            String destino = (String) comboDestino.getSelectedItem();
	            
	            double origenLat = coordsGuardadas.get(origen).getLat();
	            double origenLon = coordsGuardadas.get(origen).getLon();
	            
	            double destinoLat = coordsGuardadas.get(destino).getLat();
	            double destinoLon = coordsGuardadas.get(destino).getLon();
	            
	            double[][] coords = {
	            	    { origenLat, origenLon },
	            	    { destinoLat, destinoLon }
	            };
            
	            cargarDatosPresentador.manejarClickAgregarArista(origen, destino, textPeso.getText(), coords);
            } catch (NullPointerException ex){
                JOptionPane.showMessageDialog(
                        panelFondo,
                        "Error: faltan coordenadas para el origen o destino.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
            }
        });
	}
	
	private void crearLblRegiones() {
		lblRegiones = new JLabel("4. CANTIDAD DE REGIONES:");
		estiloEtiqueta(lblRegiones);
		lblRegiones.setBounds(30, 480, 220, 25);
		panelFondo.add(lblRegiones);
	}

	private void crearSpinnerRegiones() {
		spinnerRegiones = new JSpinner(new SpinnerNumberModel(2, 1, 999, 1));
		spinnerRegiones.setFont(new Font("Segoe UI", Font.BOLD, 14));
		spinnerRegiones.setBounds(250, 477, 70, 32);
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
		btnCalcular.addActionListener(e -> {
            int cantidadRegiones = (Integer) spinnerRegiones.getValue();
            cargarDatosPresentador.manejarClickCalcular(cantidadRegiones, origenDeDatos);
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
	
	private void crearComboElegirMapa() {
		comboElegirMapa = new JComboBox<>(new String[] {
			    "Capitales",
			    "Países de América",
			    "Países del Mundo"
			});
		comboElegirMapa.setForeground(new Color(15, 23, 42));
		comboElegirMapa.setFont(new Font("Segoe UI", Font.BOLD, 14));
		comboElegirMapa.setBackground(new Color(241, 245, 249));
		comboElegirMapa.setBounds(563, 23, 175, 35);
		panelFondo.add(comboElegirMapa);
	}
	
	private void crearBotonCargarMapa() {
	    btnCargarMapa = new JButton("Cargar");
	    btnCargarMapa.setForeground(new Color(240, 255, 240));
	    btnCargarMapa.setBackground(new Color(250, 128, 114));
	    btnCargarMapa.setBounds(748, 25, 128, 35);
	    
	    agregarListenerBtnCargarMapa();
	    panelFondo.add(btnCargarMapa);
	}
	
	private void agregarListenerBtnCargarMapa() {
		btnCargarMapa.addActionListener(e -> {
		
			String seleccion = (String) comboElegirMapa.getSelectedItem();
	
		    switch (seleccion) {
		        case "Capitales":
		            cargarDatosPresentador.cargarJSON(OpcionMapa.CAPITALES);
		            break;
		        case "Países de América":
		            cargarDatosPresentador.cargarJSON(OpcionMapa.AMERICA);
		            break;
		        case "Países del Mundo":
		            cargarDatosPresentador.cargarJSON(OpcionMapa.MUNDO);
		            break;
		        default:
		        		break;
		    }
		});
	}
	
	private void crearLblElegirMapa() {
		lblElegirMapa = new JLabel("Elegir Mapa");
		estiloEtiqueta(lblElegirMapa);
		lblElegirMapa.setBounds(481, 30, 79, 25);
		panelFondo.add(lblElegirMapa);
	}

    private void crearTablaAristas() {
        modeloAristas = crearModeloNoEditable(new String[]{"Origen", "Destino", "Peso", ""});
        tablaAristas = new JTable(modeloAristas);
        estiloTabla(tablaAristas);
        configurarColumnaEliminar(tablaAristas);
        
		agregarListenerEliminarArista();

        JScrollPane scroll = new JScrollPane(tablaAristas);
        scroll.getViewport().setBackground(new Color(15, 23, 42));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85)));
        scroll.setBounds(495, 225, 380, 225);
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
					cargarDatosPresentador.chequearSiBorrarVerticeDibujado(origen, destino);
					eliminarDibujoArista(origen, destino);
				}
			}
		});
		
		agregarCursorManoSobreCruz(tablaAristas);
	}
	
	private void eliminarDibujoArista(String origen, String destino) {
	    for (int i = modeloAristas.getRowCount() - 1; i >= 0; i--) {
	        if (modeloAristas.getValueAt(i, 0).equals(origen) &&
	            modeloAristas.getValueAt(i, 1).equals(destino)) {
	            modeloAristas.removeRow(i);
	        }
	    }

	    String clave = origen + "-" + destino;
	    MapPolygonImpl line = lineas.get(clave);
	    if (line != null) {
	        mapa.removeMapPolygon(line);
	        lineas.remove(clave);
	    }
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

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
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
	
	private DefaultTableModel crearModeloNoEditable(String[] columnas) {
		return new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
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
    
	private void borrarDibujosYDatosDelMapa() {
	    comboOrigen.removeAllItems();
	    comboDestino.removeAllItems();
	    modeloAristas.setRowCount(0);
	    coordsGuardadas.clear();
	    lineas.clear();
	    mapa.getMapMarkerList().clear();
	    mapa.getMapPolygonList().clear();
	}
	
	@Override
	public void agregarArista(String origen, String destino, String peso) {
        modeloAristas.addRow(new Object[]{origen, destino, peso, eliminar});
	}

	@Override
	public void limpiarCampoPeso() {
        textPeso.setText("");
	}

	@Override
    public void dibujarConexion(String origen, String destino) {
        Coordinate coordOrigen = new Coordinate(cargarDatosPresentador.getLatDeVertice(origen), cargarDatosPresentador.getLonDeVertice(origen));
        Coordinate coordDestino = new Coordinate(cargarDatosPresentador.getLatDeVertice(destino), cargarDatosPresentador.getLonDeVertice(destino));

        if (coordOrigen != null && coordDestino != null) {
            java.util.List<Coordinate> coords = Arrays.asList(
                coordOrigen,
                coordDestino,
                coordDestino
            );
            MapPolygonImpl line = new MapPolygonImpl(coords);
            line.setColor(Color.RED);
            mapa.addMapPolygon(line);

            String clave = origen + "-" + destino;
            lineas.put(clave, line);
        }
    }

	@Override
	public void cargarCiudades(List<String> nombres, Map<String, double[]> coords) {
		
		borrarDibujosYDatosDelMapa();
		
	    for (String nombre : nombres) {
	        double[] coord = coords.get(nombre);
	        Coordinate c = new Coordinate(coord[0], coord[1]);
	        coordsGuardadas.put(nombre, c);

	        comboOrigen.addItem(nombre);
	        comboDestino.addItem(nombre);
	        mapa.addMapMarker(new MapMarkerDot(nombre, c));
	    }
	}
}
