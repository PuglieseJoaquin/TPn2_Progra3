package vista;

import modelo.Grafo;

public interface GestorInterfaz {
	void crearPantallaInicio();

	void crearPantallaCargaDatos();

	void crearPantallaResultado(Grafo grafo, int cantidadRegiones);
}
