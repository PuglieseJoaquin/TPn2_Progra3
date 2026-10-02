package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class PantallaResultado extends JFrame {
    private GestorPantallas gestorPantallas;
    private JPanel panelFondo;
    private JTextArea txtResultado;

    public PantallaResultado(GestorPantallas gestorPantallas, String resultado) {
        this.gestorPantallas = gestorPantallas;
        configurarPantalla();
        crearComponentes(resultado);
    }

    private void configurarPantalla() {
        setTitle("Resultado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        panelFondo = new JPanel();
        panelFondo.setLayout(null);
        setContentPane(panelFondo);
    }

    private void crearComponentes(String resultado) {
        txtResultado = new JTextArea(resultado);
        txtResultado.setBounds(50, 50, 500, 250);
        txtResultado.setEditable(false);

        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setBounds(50, 50, 500, 250);
        panelFondo.add(scroll);
    }
}
