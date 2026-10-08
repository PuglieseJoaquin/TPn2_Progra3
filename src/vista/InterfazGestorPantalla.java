package vista;

public interface InterfazGestorPantalla {
	
	void crearPantallaInicio();

	void crearPantallaCargaDatos();

	void crearPantallaCargaDesdeJSON();
	
	void crearPantallaCargaDesdeMapa();
	
	PantallaResultado crearPantallaResultado();
	
	void mostrarPantallaCargaManual();
	
	void mostrarPantallaCargaDesdeJSON();
	
	void mostrarPantallaCargaDesdeMapa();
}
