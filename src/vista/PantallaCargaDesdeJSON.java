package vista;

import modelo.CapitalesArgentinas;
import presenter.CargaDesdeJSONPresentador;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class PantallaCargaDesdeJSON extends JFrame {

    private CargaDesdeJSONPresentador cargaDesdeJSONPresentador;

    private JComboBox<String> comboOrigen;
    private JComboBox<String> comboDestino;
    private JTextField txtPeso;
    private JButton btnAgregarConexion;
    private JTable tablaAristas;
    private JMapViewer mapa;

    public PantallaCargaDesdeJSON(GestorPantallas gestorPantallas) {
        setTitle("Diseñando regiones — Carga de datos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        crearMapa();
        crearPanelDerecho();

        // conectar presentador
        cargaDesdeJSONPresentador = new CargaDesdeJSONPresentador(this, gestorPantallas);
    }

    // 👉 Panel izquierdo con el mapa
    private void crearMapa() {
        mapa = new JMapViewer();
        add(mapa, BorderLayout.CENTER);
    }

    // 👉 Panel derecho con controles y botones
    private void crearPanelDerecho() {
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));

        comboOrigen = new JComboBox<>();
        comboDestino = new JComboBox<>();
        txtPeso = new JTextField(5);
        btnAgregarConexion = new JButton("Agregar arista");

        panelDerecho.add(new JLabel("Origen:"));
        panelDerecho.add(comboOrigen);
        panelDerecho.add(new JLabel("Destino:"));
        panelDerecho.add(comboDestino);
        panelDerecho.add(new JLabel("Peso (similaridad):"));
        panelDerecho.add(txtPeso);
        panelDerecho.add(btnAgregarConexion);

        // Tabla de aristas
        tablaAristas = new JTable(new DefaultTableModel(
            new Object[]{"Origen", "Destino", "Peso"}, 0
        ));
        panelDerecho.add(new JScrollPane(tablaAristas));

        // Botones inferiores
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnVolver = new JButton("Volver al menú");
        JButton btnCalcular = new JButton("Calcular regiones");
        JButton btnSalir = new JButton("Salir");
        panelBotones.add(btnVolver);
        panelBotones.add(btnCalcular);
        panelBotones.add(btnSalir);

        panelDerecho.add(panelBotones);

        add(panelDerecho, BorderLayout.EAST);

        // Acción del botón agregar
        btnAgregarConexion.addActionListener(e -> {
            String origen = (String) comboOrigen.getSelectedItem();
            String destino = (String) comboDestino.getSelectedItem();
            String input = txtPeso.getText();

            try {
                int peso = Integer.parseInt(input);
                cargaDesdeJSONPresentador.manejarAgregarConexion(origen, destino, peso);
                ((DefaultTableModel) tablaAristas.getModel()).addRow(new Object[]{origen, destino, peso});
            } catch (NumberFormatException ex) {
                mostrarError("El peso debe ser un número entero.");
            }
        });
    }

    // 👉 Método que el presentador usa para cargar capitales
    public void cargarCapitales(List<CapitalesArgentinas> capitales) {
        for (CapitalesArgentinas c : capitales) {
            comboOrigen.addItem(c.getNombre());
            comboDestino.addItem(c.getNombre());

            // marcador en el mapa
            mapa.addMapMarker(new MapMarkerDot(c.getNombre(),
                    new Coordinate(c.getLat(), c.getLon())));
        }
    }

    // 👉 Dibujar la línea en el mapa
    public void dibujarConexion(CapitalesArgentinas origen, CapitalesArgentinas destino, int peso) {
        java.util.List<Coordinate> coords = Arrays.asList(
                new Coordinate(origen.getLat(), origen.getLon()),
                new Coordinate(destino.getLat(), destino.getLon()),
                new Coordinate(destino.getLat(), destino.getLon()) // repetir destino
        );
        MapPolygonImpl line = new MapPolygonImpl(coords);
        line.setColor(Color.RED);
        mapa.addMapPolygon(line);

        JOptionPane.showMessageDialog(this,
                "Conexión creada entre " + origen.getNombre() + " y " + destino.getNombre() +
                        " con peso " + peso);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
