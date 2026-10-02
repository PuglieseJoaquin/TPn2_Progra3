package vista;

import javax.swing.JFrame;

public class GestorPantallas implements GestorInterfaz {

    private PantallaInicio pantallaInicio;
    private PantallaCargaDatos pantallaCargaDatos;
    private PantallaResultado pantallaResultado;

    @Override
    public void crearPantallaInicio() {
        pantallaInicio = new PantallaInicio(this);
        mostrarPantalla(pantallaInicio);
    }

    public void crearPantallaCargaDatos() {
        pantallaCargaDatos = new PantallaCargaDatos(this);
        mostrarPantalla(pantallaCargaDatos);
    }

    public void crearPantallaResultado(String resultado) {
        PantallaResultado pantalla = new PantallaResultado(this, resultado);
        mostrarPantalla(pantalla);
    }

    public JFrame mostrarPantalla(JFrame pantalla) {
        pantalla.setVisible(true);
        ocultarPantallas(pantalla);
        return pantalla;
    }

    public void ocultarPantallas(JFrame pantallaActual) {
        JFrame[] todasLasPantallas = {
            pantallaInicio,
            pantallaCargaDatos,
            pantallaResultado
        };

        for (JFrame pantalla : todasLasPantallas) {
            if (pantalla != pantallaActual && pantalla != null) {
                pantalla.setVisible(false);
            }
        }
    }

	public Object crearPantallaCargaArchivo() {
		// TODO Auto-generated method stub
		return null;
	}
}