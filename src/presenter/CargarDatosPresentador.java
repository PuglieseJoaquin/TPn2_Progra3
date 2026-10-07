package presenter;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import datos.JsonLoader;
import modelo.Arista;
import modelo.CiudadSeleccionada;
import modelo.Grafo;
import modelo.Vertice;
import vista.CargarDatosVista;
import vista.GestorPantallas;
import vista.PantallaResultado;
 
public class CargarDatosPresentador {
 
	private CargarDatosVista vista;
	private GestorPantallas gestorPantallas;
	private Grafo grafo;
	private int cantidadRegiones;
    
 
	public CargarDatosPresentador(CargarDatosVista vista, GestorPantallas gestorPantallas) {
		this.vista = vista;
		this.gestorPantallas = gestorPantallas;
		this.grafo = new Grafo();
		this.cantidadRegiones = 0;
	}
 
	
	public int getCantidadRegiones() {
		return cantidadRegiones;
	}

	
	public Grafo getGrafo() {
		return grafo;
	}

	
	public void manejarClickAgregarVertice(String nombre) {
		String nombreLimpio = nombre.trim();
		if (nombreLimpio.isEmpty()) {
			vista.mostrarMensajeError("El nombre del vértice no puede estar vacío.");
			return;
		}
 
		if (existeNombre(nombreLimpio)) {
			vista.mostrarMensajeError("Ya existe un vértice llamado \"" + nombreLimpio + "\".");
			return;
		}
 
		grafo.agregarVertice(new Vertice(nombreLimpio));
		vista.agregarVertice(nombreLimpio);
		vista.limpiarCampoVertice();
	}
	
	
	public void manejarClickAgregarVertice(String nombre, double lat, double lon) {
		String nombreLimpio = nombre.trim();
		if (nombreLimpio.isEmpty()) {
		    throw new IllegalArgumentException("El nombre del vertice no puede estar vacio.");
		}

		if (existeNombre(nombreLimpio)) {
		    throw new IllegalArgumentException("Ya existe un vertice llamado \"" + nombreLimpio + "\".");
		}
 
		grafo.agregarVertice(new Vertice(nombreLimpio, lat, lon));
		vista.agregarVertice(nombreLimpio);
		vista.limpiarCampoVertice();
	}
 
	
	public void manejarClickAgregarArista(String origen, String destino, String peso) {
		Double pesoNumerico = convertirPeso(peso);
		
		if (origen == null || destino == null) {
			vista.mostrarMensajeError("Agregá al menos dos vértices antes de crear una conexión.");
			return;
		}
		
		if (pesoNumerico == null) {
			vista.mostrarMensajeError("El peso debe ser un número (por ejemplo 3 o 2.5).");
			return;
		}
 
		try {
	        Vertice vOrigen;
	        try {
	            vOrigen = grafo.getVertice(origen);
	        } catch (IllegalArgumentException e) {
	            vOrigen = new Vertice(origen);
	            grafo.agregarVertice(vOrigen);
	        }
	    
	        Vertice vDestino;
	        try {
	            vDestino = grafo.getVertice(destino);
	        } catch (IllegalArgumentException e) {
	            vDestino = new Vertice(destino);
	            grafo.agregarVertice(vDestino);
	        }
	        
	        grafo.agregarArista(vOrigen, vDestino, pesoNumerico);
	        vista.dibujarConexion(origen, destino);
	        
	    } catch (IllegalArgumentException e) {
	        vista.mostrarMensajeError(e.getMessage());
	        return;
	    }
 
		vista.agregarArista(origen, destino, FormatoPesoArista.aTexto(pesoNumerico));
		vista.limpiarCampoPeso();
	}
	
	
	public void manejarClickAgregarArista(String origen, String destino, String peso, Coordinate coordOrigen,
			Coordinate coordDestino) {
		Double pesoNumerico = convertirPeso(peso);
		
		if (pesoNumerico == null) {
			vista.mostrarMensajeError("El peso debe ser un número (por ejemplo 3 o 2.5).");
			return;
		}
		
	    try {
	        grafo.getVertice(origen);
	    } catch (IllegalArgumentException e) {
	        double lat = coordOrigen.getLat();
	        double lon = coordOrigen.getLon();
	        grafo.agregarVertice(new Vertice(origen, lat, lon));
	    }
	    
	    try {
	        grafo.getVertice(destino);
	    } catch (IllegalArgumentException e) {
	        double lat = coordDestino.getLat();
	        double lon = coordDestino.getLon();
	        grafo.agregarVertice(new Vertice(destino, lat, lon));
	    }

		try {
			grafo.agregarArista(grafo.getVertice(origen), grafo.getVertice(destino), pesoNumerico);
			vista.dibujarConexion(origen, destino);
		} catch (IllegalArgumentException e) {
			vista.mostrarMensajeError(e.getMessage());
			return;
		}
 
		vista.agregarArista(origen, destino, FormatoPesoArista.aTexto(pesoNumerico));
		vista.limpiarCampoPeso();
	}
 
	
	public void manejarClickEliminarArista(String origen, String destino) {
		grafo.eliminarArista(grafo.getVertice(origen), grafo.getVertice(destino));
		vista.eliminarArista(origen, destino);
	}
	
	
	public void chequearSiBorrarVertices(String origen, String destino) {
	    Vertice vOrigen = grafo.getVertice(origen);
	    Vertice vDestino = grafo.getVertice(destino);

	    if (grafo.obtenerAristasDe(vOrigen).isEmpty()) {
	        grafo.eliminarVertice(vOrigen);
	    }

	    if (grafo.obtenerAristasDe(vDestino).isEmpty()) {
	        grafo.eliminarVertice(vDestino);
	    }
	}
 
	
	public void manejarClickEliminarVertice(String nombre) {
		Vertice vertice = grafo.getVertice(nombre);
 
		for (Arista arista : grafo.obtenerAristasDe(vertice)) {
			vista.eliminarArista(arista.getOrigen().getNombre(), arista.getDestino().getNombre());
		}

		grafo.eliminarVertice(vertice);
		vista.eliminarVertice(nombre);
	}
 
	
	public void manejarClickCalcular(int cantidadRegiones) {
		
		this.cantidadRegiones = cantidadRegiones;
		
		int cantidadVertices = grafo.cantidadVertices();
 
		if (cantidadVertices == 0) {
			vista.mostrarMensajeError("Agregá al menos un vértice.");
			return;
		}
		if (cantidadRegiones < 1 || cantidadRegiones > cantidadVertices) {
			vista.mostrarMensajeError("La cantidad de regiones debe estar entre 1 y " + cantidadVertices + ".");
			return;
		}
		if (!grafo.esConexo()) {
			vista.mostrarMensajeError("El grafo no es conexo: todos los vértices deben poder alcanzarse "
					+ "a través de alguna conexión.");
			return;
		}
 
		visualizarSolucion();

	}
 
	
	private void visualizarSolucion() {
		PantallaResultado pantallaResultado = gestorPantallas.crearPantallaResultado();
		ResultadoPresentador resultadoPresentador = new ResultadoPresentador(pantallaResultado, gestorPantallas, grafo, cantidadRegiones);
		
		pantallaResultado.setPresentador(resultadoPresentador);
		resultadoPresentador.calcularSolucion();
		
	}

	
	public void manejarClickVolverAlMenu() {
		gestorPantallas.crearPantallaInicio();
	}
 
	
	private boolean existeNombre(String nombre) {
		for (Vertice existente : grafo.obtenerVertices()) {
			if (existente.getNombre().equalsIgnoreCase(nombre)) {
				return true;
			}
		}
		return false;
	}
 
	
	private Double convertirPeso(String texto) {
		try {
			double peso = Double.parseDouble(texto.trim().replace(',', '.'));
			return (Double.isNaN(peso) || Double.isInfinite(peso)) ? null : peso;
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	
	public Coordinate getCoordenadaDeVertice(String nombre) {
	    Vertice v = grafo.getVertice(nombre);
	    if (v != null) {
	        return new Coordinate(v.getLat(), v.getLon());
	    }
	    return null;
	}

	
	public void manejarClickBtnSalir() {
		System.exit(0);
	}

	
	public void cargarJSON() {
	    try {
	        List<CiudadSeleccionada> capitales = JsonLoader.cargarCapitales("/capitales.json");

	        List<String> nombres = new ArrayList<>();
	        Map<String, Coordinate> coords = new HashMap<>();

	        for (CiudadSeleccionada c : capitales) {
	            nombres.add(c.getNombre());
	            coords.put(c.getNombre(), new Coordinate(c.getLat(), c.getLon()));
	        }

	        vista.cargarCapitales(nombres, coords);

	    } catch (Exception e) {
	        vista.mostrarMensajeError("Error al leer JSON: " + e.getMessage());
	    }
	}
}