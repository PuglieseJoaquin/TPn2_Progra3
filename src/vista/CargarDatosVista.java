package vista;

import java.util.List;
import java.util.Map;

public interface CargarDatosVista {
	void agregarVertice(String nombre);
 
	void agregarArista(String origen, String destino, String peso);
 
	void eliminarVertice(String nombre);
 
	void eliminarArista(String origen, String destino);
 
	void limpiarCampoVertice();
 
	void limpiarCampoPeso();
 
	void mostrarMensajeError(String mensaje);

	void dibujarConexion(String origen, String destino);

	void cargarCapitales(List<String> nombres, Map<String, double[]> coords);
}
