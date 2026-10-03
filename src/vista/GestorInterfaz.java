package vista;

import modelo.Grafo;

public interface GestorInterfaz {
	void crearPantallaInicio();

	void crearPantallaCargaDatos();

	PantallaResultado crearPantallaResultado();
	
	void crearPantallaCargaDesdeMapa();
}
