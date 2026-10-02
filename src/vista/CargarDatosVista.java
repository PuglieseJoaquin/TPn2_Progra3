package vista;
 
public interface CargarDatosVista {
	void agregarVertice(String nombre);
 
	void agregarArista(String origen, String destino, String peso);
 
	void eliminarVertice(String nombre);
 
	void eliminarArista(String origen, String destino);
 
	void limpiarCampoVertice();
 
	void limpiarCampoPeso();
 
	void mostrarMensajeError(String mensaje);
}