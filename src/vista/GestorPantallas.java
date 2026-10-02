package vista;

import javax.swing.JFrame;

import modelo.Grafo;

public class GestorPantallas implements GestorInterfaz {

	private PantallaInicio pantallaInicio;
	private PantallaCargarDatos pantallaCargaDatos;
	private PantallaResultado pantallaResultado;

	public void crearPantallaInicio() {
		pantallaInicio = new PantallaInicio(this);
		mostrarPantalla(pantallaInicio);
	}

	public void crearPantallaCargaDatos() {
		pantallaCargaDatos = new PantallaCargarDatos(this);
		mostrarPantalla(pantallaCargaDatos);
	}

	
	public void crearPantallaResultado(Grafo grafo, int cantidadRegiones) {
		pantallaResultado = new PantallaResultado(this, grafo, cantidadRegiones);
		mostrarPantalla(pantallaResultado);
	}

	public JFrame mostrarPantalla(JFrame pantalla) {
		pantalla.setVisible(true);
		ocultarPantallas(pantalla);
		return pantalla;
	}

	public void ocultarPantallas(JFrame pantallaActual) {
		JFrame[] todasLasPantallas = { pantallaInicio, pantallaCargaDatos, pantallaResultado };

		for (JFrame pantalla : todasLasPantallas) {
			if (pantalla != pantallaActual && pantalla != null) {
				pantalla.setVisible(false);
			}
		}
	}
}