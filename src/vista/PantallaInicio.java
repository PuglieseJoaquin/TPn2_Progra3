package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PantallaInicio extends JFrame {
    
    private GestorPantallas gestorPantallas;
    private JPanel panelFondo;
    private JLabel lblTitulo;

    public PantallaInicio(GestorPantallas gestorPantallas) {
        this.gestorPantallas = gestorPantallas;
        configurarPantalla();
        crearComponentes();
    }
    
    private void configurarPantalla() {
        setTitle("Pantalla de Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400); 
        setLocationRelativeTo(null);
        setResizable(false);

        panelFondo = new JPanel();
        panelFondo.setBackground(Color.WHITE);
        setContentPane(panelFondo);
        panelFondo.setLayout(null);
    }
    
    private void crearComponentes() {
        lblTitulo = new JLabel("TP N°2: Diseñando Regiones", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBounds(100, 150, 400, 40);
        panelFondo.add(lblTitulo);
    }
}