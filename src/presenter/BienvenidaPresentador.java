package presenter;
 
import vista.InterfazGestorPantalla;
 
public class BienvenidaPresentador {
 
	private InterfazGestorPantalla gestorInterfaz;
 
	public BienvenidaPresentador(InterfazGestorPantalla gestorInterfaz) {
		this.gestorInterfaz = gestorInterfaz;
	}
 	
	public void manejarClickBotonCargaManual() {
		gestorInterfaz.crearPantallaCargaDatos();
	}
 	
	public void manejarClickBotonCargaAutomatica() {
		gestorInterfaz.crearPantallaCargaDesdeJSON();
	}
	
	public void manejarClickBotonCargaMapa() {
		gestorInterfaz.crearPantallaCargaDesdeMapa();
	}
		
	public void manejarClickBotonSalir() {
	    System.exit(0);
	}
}
