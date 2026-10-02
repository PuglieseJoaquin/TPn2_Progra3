package vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import presenter.CargaDeDatosPresentador;

public class PantallaCargaDatos extends JFrame {
    private GestorPantallas gestorPantallas;
    private JPanel panelFondo;
    private JTextField txtProvincia;
    private JButton btnAgregarProvincia;
    private JComboBox<String> comboOrigen;
    private JComboBox<String> comboDestino;
    private JTextField txtPeso;
    private JButton btnCrearConexion;
    private JButton btnVerAristas;
    private JTable tablaAristas;
    private DefaultTableModel modeloTabla;
    private JTextField txtPartesConexas;

    private CargaDeDatosPresentador presentador;

    public PantallaCargaDatos(GestorPantallas gestorPantallas) {
        this.gestorPantallas = gestorPantallas;
        this.presentador = new CargaDeDatosPresentador(gestorPantallas);
        configurarPantalla();
        crearComponentes();
    }

    private void configurarPantalla() {
        setTitle("Carga de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        panelFondo = new JPanel();
        panelFondo.setLayout(null);
        setContentPane(panelFondo);
    }

    private void crearComponentes() {
        // Campo para escribir provincia
        txtProvincia = new JTextField();
        txtProvincia.setBounds(50, 50, 200, 30);
        panelFondo.add(txtProvincia);

        // Botón para agregar provincia
        btnAgregarProvincia = new JButton("Agregar Provincia");
        btnAgregarProvincia.setBounds(270, 50, 200, 30);
        btnAgregarProvincia.addActionListener(e -> {
            String nombre = txtProvincia.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre de la provincia no puede estar vacío.");
                return;
            }

            // Chequear si ya existe en el combo
            for (int i = 0; i < comboOrigen.getItemCount(); i++) {
                if (comboOrigen.getItemAt(i).equalsIgnoreCase(nombre)) {
                    JOptionPane.showMessageDialog(this, "La provincia '" + nombre + "' ya existe.");
                    return;
                }
            }

            comboOrigen.addItem(nombre);
            comboDestino.addItem(nombre);
            txtProvincia.setText("");

            presentador.agregarVertice(nombre);
        });
        panelFondo.add(btnAgregarProvincia);

        // Combos de origen y destino
        comboOrigen = new JComboBox<>();
        comboOrigen.setBounds(50, 100, 200, 30);
        panelFondo.add(comboOrigen);

        comboDestino = new JComboBox<>();
        comboDestino.setBounds(300, 100, 200, 30);
        panelFondo.add(comboDestino);

        // Campo para peso
        txtPeso = new JTextField();
        txtPeso.setBounds(50, 150, 100, 30);
        panelFondo.add(txtPeso);

        // Botón para crear conexión
        btnCrearConexion = new JButton("Crear Conexión");
        btnCrearConexion.setBounds(50, 200, 200, 30);
        btnCrearConexion.addActionListener(e -> {
            String origen = (String) comboOrigen.getSelectedItem();
            String destino = (String) comboDestino.getSelectedItem();
            String pesoStr = txtPeso.getText().trim();

            if (origen == null || destino == null || pesoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos.");
                return;
            }
            if (origen.equals(destino)) {
                JOptionPane.showMessageDialog(this, "El origen y destino no pueden ser la misma provincia.");
                return;
            }

            int peso;
            try {
                peso = Integer.parseInt(pesoStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El peso debe ser un número entero.");
                return;
            }

            try {
                presentador.agregarArista(origen, destino, peso);
                modeloTabla.addRow(new Object[]{origen, destino, peso});
                txtPeso.setText("");
            } catch (IllegalArgumentException ex) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.WARNING_MESSAGE
                );
            }

        });
        panelFondo.add(btnCrearConexion);

        txtPartesConexas = new JTextField();
        txtPartesConexas.setBounds(50, 425, 100, 30);
        panelFondo.add(txtPartesConexas);

        btnVerAristas = new JButton("Ver Aristas");
        btnVerAristas.setBounds(270, 200, 200, 30);
        btnVerAristas.addActionListener(e -> {
            String partesStr = txtPartesConexas.getText().trim();
            int partes;
            try {
                partes = Integer.parseInt(partesStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Debes ingresar un número entero para las partes conexas.");
                return;
            }

            if (presentador.esGrafoConexo()) {
                presentador.mostrarResultado(partes);
            } else {
                JOptionPane.showMessageDialog(this, "El grafo no es conexo, no se puede calcular el resultado.");
            }
        });
        panelFondo.add(btnVerAristas);

        // Tabla de aristas
        modeloTabla = new DefaultTableModel(new Object[]{"Origen", "Destino", "Peso"}, 0);
        tablaAristas = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaAristas);
        scrollTabla.setBounds(50, 250, 500, 180);
        panelFondo.add(scrollTabla);
    }
}

