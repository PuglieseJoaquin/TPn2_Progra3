package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PantallaInicio extends JFrame {
    
    private GestorPantallas gestorPantallas;
    private JPanel panelFondo;
    private JButton btnCargarDatos;
    private JButton btnCargarArchivo;

    public PantallaInicio(GestorPantallas gestorPantallas) {
        this.gestorPantallas = gestorPantallas;
        configurarPantalla();
        crearBotones();
    }

    private void configurarPantalla() {
        setTitle("Bienvenida");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        panelFondo = new JPanel();
        panelFondo.setLayout(null);
        setContentPane(panelFondo);
    }

    private void crearBotones() {
        btnCargarDatos = new JButton("CARGAR DATOS");
        btnCargarDatos.setBounds(200, 120, 200, 40);
        btnCargarDatos.addActionListener(e -> gestorPantallas.crearPantallaCargaDatos());
        panelFondo.add(btnCargarDatos);

        btnCargarArchivo = new JButton("CARGAR DESDE ARCHIVO");
        btnCargarArchivo.setBounds(200, 180, 200, 40);
        btnCargarArchivo.addActionListener(e -> gestorPantallas.crearPantallaCargaArchivo());
        panelFondo.add(btnCargarArchivo);
    }
}