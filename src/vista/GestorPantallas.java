package vista;

import javax.swing.JFrame;

import presenter.ResultadoPresentador;

public class GestorPantallas implements GestorInterfaz {

	private PantallaInicio pantallaInicio;
	private PantallaCargarDatosManual pantallaCargaDatos;
	private PantallaCargarDatosDesdeJSON pantallaCargaDesdeJSON;
	private PantallaCargarDatosDesdeMapa PantallaCargaDesdeMapa;
	private PantallaResultado pantallaResultado;
	
	public void crearPantallaInicio() {
		pantallaInicio = new PantallaInicio(this);
		mostrarPantalla(pantallaInicio);
	}

	public void crearPantallaCargaDatos() {
		pantallaCargaDatos = new PantallaCargarDatosManual(this);
		mostrarPantalla(pantallaCargaDatos);
	}

	public void crearPantallaCargaDesdeMapa() {
		PantallaCargaDesdeMapa = new PantallaCargarDatosDesdeMapa(this);
		mostrarPantalla(PantallaCargaDesdeMapa);	
	}
	
	public PantallaResultado crearPantallaResultado() {
	    PantallaResultado pantalla = new PantallaResultado(this);
	    mostrarPantalla(pantalla);
	    return pantalla;
	}

	public void crearPantallaCargaDesdeJSON() {
	    pantallaCargaDesdeJSON = new PantallaCargarDatosDesdeJSON(this);
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