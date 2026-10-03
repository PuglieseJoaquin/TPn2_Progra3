package presenter;
 
import vista.GestorPantallas;
 
public class BienvenidaPresentador {
 
	private GestorPantallas gestorPantallas;
 
	public BienvenidaPresentador(GestorPantallas gestorPantallas) {
		this.gestorPantallas = gestorPantallas;
	}
 
	public void manejarClickBotonCargaManual() {
		gestorPantallas.crearPantallaCargaDatos();
	}
 
	public void manejarClickBotonCargaAutomatica() {
		gestorPantallas.crearPantallaCargaDesdeJSON();
	}

	public void manejarClickBotonCargaMapa() {
		gestorPantallas.crearPantallaCargaDesdeMapa();
	}
	
	public void manejarClickBotonSalir() {
	    System.exit(0);
	}
}