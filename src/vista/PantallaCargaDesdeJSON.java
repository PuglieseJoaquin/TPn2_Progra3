package vista;

import modelo.CiudadSeleccionada;
import presenter.CargaDesdeJSONPresentador;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class PantallaCargaDesdeJSON extends JFrame {

    private CargaDesdeJSONPresentador cargaDesdeJSONPresentador;
    private JPanel panelFondo;
    private JLabel lblTitulo;
    private JLabel lblMapa;
    private JLabel lblOrigen;
	private JLabel lblDestino;
	private JLabel lblPeso;
	private DefaultTableModel modeloAristas;
	
	private JLabel lblRegiones;
	private JSpinner spinnerRegiones;
	private JButton btnVolverAlMenu;
	private JButton btnCalcular;
	private JButton btnSalir;

    private JComboBox<String> comboOrigen;
    private JComboBox<String> comboDestino;
    private JTextField txtPeso;
    private JButton btnAgregarConexion;
    private JTable tablaAristas;
    private JMapViewer mapa;

    public PantallaCargaDesdeJSON(GestorPantallas gestorPantallas) {
        
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
		crearBtnAgregarConexion();
		crearTablaAristas();

		crearLblRegiones();
		crearSpinnerRegiones();
		crearBtnVolverAlMenu();
		crearBtnCalcular();
		crearBtnSalir();

        // conectar presentador
        cargaDesdeJSONPresentador = new CargaDesdeJSONPresentador(this, gestorPantallas);
    }

	private void configurarPantalla() {
    	setTitle("Diseñando regiones — Carga de datos");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 600);
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
		lblTitulo.setBounds(50, 20, 1000, 35);
		panelFondo.add(lblTitulo);
	}
	
	//Mapa
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
		//coordenada de argentina 
		mapa.setDisplayPosition(new Point(230, 170),new Coordinate(-38.4161, -63.6167),3);
		panelFondo.add(mapa);
    }
    
    //Aristas y vertices
    private void crearLblOrigen() {
		lblOrigen = new JLabel("2. ORIGEN:");
		estiloEtiqueta(lblOrigen);
		lblOrigen.setBounds(520, 80, 255, 22);
		panelFondo.add(lblOrigen);
	}

	private void crearComboOrigen() {
		comboOrigen = new JComboBox<String>();
		estiloCombo(comboOrigen);
		comboOrigen.setBounds(520, 108, 255, 34);
		panelFondo.add(comboOrigen);
	}

	private void crearLblDestino() {
		lblDestino = new JLabel("3. DESTINO:");
		estiloEtiqueta(lblDestino);
		lblDestino.setBounds(795, 80, 255, 22);
		panelFondo.add(lblDestino);
	}

	private void crearComboDestino() {
		comboDestino = new JComboBox<String>();
		estiloCombo(comboDestino);
		comboDestino.setBounds(795, 108, 255, 34);
		panelFondo.add(comboDestino);
	}

	private void crearLblPeso() {
		lblPeso = new JLabel("PESO (SIMILARIDAD):");
		estiloEtiqueta(lblPeso);
		lblPeso.setBounds(520, 150, 255, 22);
		panelFondo.add(lblPeso);
	}

	private void crearTxtPeso() {
		txtPeso = new JTextField();
		txtPeso.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtPeso.setForeground(new Color(15, 23, 42));
		txtPeso.setBackground(new Color(241, 245, 249));
		txtPeso.setHorizontalAlignment(SwingConstants.CENTER);
		txtPeso.setBounds(520, 175, 120, 34);
		panelFondo.add(txtPeso);
	}

	private void crearBtnAgregarConexion() {
		btnAgregarConexion = new JButton("Agregar arista");
		estiloBotonPrimario(btnAgregarConexion);
		btnAgregarConexion.setBounds(660, 175, 390, 34);

		agregarListenerBtnAgregarConexion();
		panelFondo.add(btnAgregarConexion);
	}

	private void agregarListenerBtnAgregarConexion() {
		btnAgregarConexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String origen = (String) comboOrigen.getSelectedItem();
				String destino = (String) comboDestino.getSelectedItem();

				try {
					int peso = Integer.parseInt(txtPeso.getText().trim());
					cargaDesdeJSONPresentador.manejarAgregarConexion(origen, destino);
					modeloAristas.addRow(new Object[] { origen, destino, peso });
				} catch (NumberFormatException ex) {
					mostrarError("El peso debe ser un numero.");
				}
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
		btnVolverAlMenu.setBounds(680, 470, 170, 42);

		agregarListenerBtnVolverAlMenu();
		panelFondo.add(btnVolverAlMenu);
	}

	private void agregarListenerBtnVolverAlMenu() {
		btnVolverAlMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargaDesdeJSONPresentador.manejarClickVolverAlMenu();
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
		btnCalcular.setBounds(870, 470, 180, 42);

		agregarListenerBtnCalcular();
		panelFondo.add(btnCalcular);
	}

	private void agregarListenerBtnCalcular() {
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//presenter pide calculo
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
		btnSalir.setBounds(460, 515, 180, 40);

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

	private void crearTablaAristas() {
		modeloAristas = new DefaultTableModel(new String[] { "Origen", "Destino", "Peso" }, 0) {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		tablaAristas = new JTable(modeloAristas);
		estiloTabla(tablaAristas);

		JScrollPane scroll = new JScrollPane(tablaAristas);
		scroll.getViewport().setBackground(new Color(15, 23, 42));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85)));
		scroll.setBounds(520, 225, 530, 223);
		panelFondo.add(scroll);
	}

    // 👉 Método que el presentador usa para cargar capitales
    public void cargarCapitales(List<CiudadSeleccionada> capitales) {
        for (CiudadSeleccionada c : capitales) {
            comboOrigen.addItem(c.getNombre());
            comboDestino.addItem(c.getNombre());

            // marcador en el mapa
            mapa.addMapMarker(new MapMarkerDot(c.getNombre(),
                    new Coordinate(c.getLat(), c.getLon())));
        }
    }

    // 👉 Dibujar la línea en el mapa
    public void dibujarConexion(CiudadSeleccionada origen, CiudadSeleccionada destino) {
        java.util.List<Coordinate> coords = Arrays.asList(
                new Coordinate(origen.getLat(), origen.getLon()),
                new Coordinate(destino.getLat(), destino.getLon()),
                new Coordinate(destino.getLat(), destino.getLon()) // repetir destino
        );
        MapPolygonImpl line = new MapPolygonImpl(coords);
        line.setColor(Color.RED);
        mapa.addMapPolygon(line);
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

}
