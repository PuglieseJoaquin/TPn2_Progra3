package vista;

import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class VistaBaseCompartida extends JFrame implements CargarDatosVista{
	
	public void agregarVertice(String nombre) {}
	 
	public void agregarArista(String origen, String destino, String peso) {}
 
	public void eliminarVertice(String nombre) {}
 
	public void eliminarArista(String origen, String destino) {}
 
	public void limpiarCampoVertice() {}
 
	public void limpiarCampoPeso() {}
 
	public void mostrarMensajeError(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje, "Dato no válido", JOptionPane.ERROR_MESSAGE);
    }

	public void dibujarConexion(String origen, String destino) {}

	public void cargarCapitales(List<String> nombres, Map<String, double[]> coords) {}
	
}
