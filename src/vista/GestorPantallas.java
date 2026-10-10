package vista;

import javax.swing.JFrame;

public class GestorPantallas implements InterfazGestorPantalla {

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

	
	public void crearPantallaCargaDesdeJSON() {
	    pantallaCargaDesdeJSON = new PantallaCargarDatosDesdeJSON(this);
	    mostrarPantalla(pantallaCargaDesdeJSON);
	}
	
	
	public void crearPantallaCargaDesdeMapa() {
		PantallaCargaDesdeMapa = new PantallaCargarDatosDesdeMapa(this);
		mostrarPantalla(PantallaCargaDesdeMapa);	
	}
	
	
	public PantallaResultado crearPantallaResultado() {
		pantallaResultado = new PantallaResultado(this);
	    mostrarPantalla(pantallaResultado);
	    return pantallaResultado;
	}
	
	
	public void mostrarPantallaCargaManual() {
		mostrarPantalla(pantallaCargaDatos);
	}

	
	public void mostrarPantallaCargaDesdeJSON() {
	    mostrarPantalla(pantallaCargaDesdeJSON);
	}

	
	public void mostrarPantallaCargaDesdeMapa() {
		mostrarPantalla(PantallaCargaDesdeMapa);
	}

	private JFrame mostrarPantalla(JFrame pantalla) {
		pantalla.setVisible(true);
		ocultarPantallas(pantalla);
		return pantalla;
	}
		
	private void ocultarPantallas(JFrame pantallaActual) {
		JFrame[] todasLasPantallas = { pantallaInicio, pantallaCargaDatos, pantallaResultado, pantallaCargaDesdeJSON, PantallaCargaDesdeMapa };

		for (JFrame pantalla : todasLasPantallas) {
			if (pantalla != pantallaActual && pantalla != null) {
				pantalla.setVisible(false);
			}
		}
	}
}