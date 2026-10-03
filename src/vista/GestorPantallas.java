package vista;

import javax.swing.JFrame;

import presenter.CargaDesdeJSONPresentador;
import presenter.ResultadoPresentador;

public class GestorPantallas implements GestorInterfaz {

	private PantallaInicio pantallaInicio;
	private PantallaCargarDatos pantallaCargaDatos;
	private PantallaResultado pantallaResultado;
	private PantallaCargaDesdeJSON pantallaCargaDesdeJSON;

	public void crearPantallaInicio() {
		pantallaInicio = new PantallaInicio(this);
		mostrarPantalla(pantallaInicio);
	}

	public void crearPantallaCargaDatos() {
		pantallaCargaDatos = new PantallaCargarDatos(this);
		mostrarPantalla(pantallaCargaDatos);
	}

	
	public PantallaResultado crearPantallaResultado() {
	    PantallaResultado pantalla = new PantallaResultado(this);
	    mostrarPantalla(pantalla);
	    return pantalla;
	}

	public void crearPantallaCargaDesdeJSON() {
	    pantallaCargaDesdeJSON = new PantallaCargaDesdeJSON(this);
//	    CargaDesdeJSONPresentador presentador = new CargaDesdeJSONPresentador(pantalla);
	    mostrarPantalla(pantallaCargaDesdeJSON);
	}
	
		public JFrame mostrarPantalla(JFrame pantalla) {
		pantalla.setVisible(true);
		ocultarPantallas(pantalla);
		return pantalla;
	}
		
	public void ocultarPantallas(JFrame pantallaActual) {
		JFrame[] todasLasPantallas = { pantallaInicio, pantallaCargaDatos, pantallaResultado,pantallaCargaDesdeJSON };

		for (JFrame pantalla : todasLasPantallas) {
			if (pantalla != pantallaActual && pantalla != null) {
				pantalla.setVisible(false);
			}
		}
	}
}