package vista;
 
public interface ResultadoVista {
	void mostrarResultado(String resultado);
 
	void setCantidadRegiones(int cantidad);
 
	void mostrarMensajeError(String mensaje);

	void dibujarVertice(String nombre, double lat, double lon);

	void dibujarConexion(double origenLat, double origenLon, double destinoLat, double destinoLon);

	void borrarDibujosEnMapa();

	void mostrarDatosSinMapa();
}