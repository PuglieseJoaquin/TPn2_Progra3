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
		// TODO: 
	}

	public void manejarClickBotonCargaMapa() {
		// TODO Auto-generated method stub
	}
	
	public void manejarClickBotonSalir() {
	    System.exit(0);
	}
}