package presenter;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datos.JsonLoader;

import modelo.*;
import vista.*;
 
public class CargarDatosPresentador {
	private CargarDatosVista vista;
	private InterfazGestorPantalla gestorInterfaz;
	private Grafo grafo;
	private int cantidadRegiones;
    
	public CargarDatosPresentador(CargarDatosVista vista, InterfazGestorPantalla gestorInterfaz) {
		this.vista = vista;
		this.gestorInterfaz = gestorInterfaz;
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
		try {String nombreLimpio = validarString(nombre);
			grafo.agregarVertice(new Vertice(nombreLimpio));
			vista.agregarVertice(nombreLimpio);
			vista.limpiarCampoVertice();
	        
	    } catch (IllegalArgumentException e) {
	        vista.mostrarMensajeError(e.getMessage());
	        return;
	    }
	}	
	
	public void manejarClickAgregarVertice(String nombre, double lat, double lon) {
		try {String nombreLimpio = validarString(nombre);
			grafo.agregarVertice(new Vertice(nombreLimpio, lat, lon));
			vista.agregarVertice(nombreLimpio);
			vista.limpiarCampoVertice();
	        
	    } catch (IllegalArgumentException e) {
	        vista.mostrarMensajeError(e.getMessage());
	        return;
	    }
	}

	private String validarString(String nombre) {
		String nombreLimpio = nombre.trim();
		if (nombreLimpio.isEmpty()) {
		    throw new IllegalArgumentException("El nombre del vertice no puede estar vacio.");
		}

		if (existeNombre(nombreLimpio)) {
		    throw new IllegalArgumentException("Ya existe un vertice llamado \"" + nombreLimpio + "\".");
		}
		return nombreLimpio;
	}
	
	public void manejarClickAgregarArista(String origen, String destino, String peso) {
		try {
			Double pesoNumerico = convertirPeso(peso);
			validarStringNoNull(origen, destino);
			validarDoubleNoNull(pesoNumerico);
			
	        Vertice vOrigen = obternerOCrearVertice(origen);
	    
	        Vertice vDestino = obternerOCrearVertice(destino);
	        
	        grafo.agregarArista(vOrigen, vDestino, pesoNumerico);
	        vista.dibujarConexion(origen, destino);
	        vista.agregarArista(origen, destino, peso);
	        vista.limpiarCampoPeso();
	        
	    } catch (IllegalArgumentException e) {
	        vista.mostrarMensajeError(e.getMessage());
	        return;
	    }	
	}

	private Vertice obternerOCrearVertice(String nombreVertice) {
		Vertice vertice;
		try {
			vertice = grafo.getVertice(nombreVertice);
		} catch (IllegalArgumentException e) {
			vertice = new Vertice(nombreVertice);
		    grafo.agregarVertice(vertice);
		}
		return vertice;
	}

	private void validarDoubleNoNull(Double pesoNumerico) {
		if (pesoNumerico == null) {
			throw new IllegalArgumentException("El peso debe ser un número (por ejemplo 3 o 2.5).");
		}
	}

	private void validarStringNoNull(String origen, String destino) {
		if (origen == null || destino == null) {
			throw new IllegalArgumentException("Agregá al menos dos vértices antes de crear una conexión.");
		}
	}	
	
	public void manejarClickAgregarArista(String origen, String destino, String peso, Double origenLat, Double origenLon, Double destinoLat, Double destinoLon) {	
		try {
			Double pesoNumerico = convertirPeso(peso);
			validarStringNoNull(origen, destino);
			validarDoubleNoNull(pesoNumerico);
			
	        Vertice vOrigen = obternerOCrearVertice(origen, origenLat, origenLon);
	        Vertice vDestino = obternerOCrearVertice(destino, destinoLat, destinoLon);
	        
	        grafo.agregarArista(vOrigen, vDestino, pesoNumerico);
	        vista.dibujarConexion(origen, destino);
	        vista.agregarArista(origen, destino, peso);
	        vista.limpiarCampoPeso();
	    } catch (IllegalArgumentException e) {
	        vista.mostrarMensajeError(e.getMessage());
	        return;
	    }
	}
	
	private Vertice obternerOCrearVertice(String nombreVertice, Double verticeLat, Double verticeLon) {
		Vertice vertice;
		try {
			vertice = grafo.getVertice(nombreVertice);
		} catch (IllegalArgumentException e) {
			vertice = new Vertice(nombreVertice, verticeLat, verticeLon);
		    grafo.agregarVertice(vertice);
		}
		return vertice;
	}

	public void manejarClickEliminarArista(String origen, String destino) {
		grafo.eliminarArista(grafo.getVertice(origen), grafo.getVertice(destino));
		vista.eliminarArista(origen, destino);
	}	
	
	public void chequearSiBorrarVerticeDibujado(String origen, String destino) {
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
	
	public void manejarClickCalcular(int cantidadRegiones, TipoPantallaDeCarga origen) {
		
		this.cantidadRegiones = cantidadRegiones;
		int cantidadVertices = grafo.cantidadVertices();
 
		try {
			validarDatosPreSolucion(cantidadRegiones, cantidadVertices);
			visualizarSolucion(origen);
		} catch (IllegalArgumentException e){
			vista.mostrarMensajeError(e.getMessage());
	        return;
		}
	}

	private void validarDatosPreSolucion(int cantidadRegiones, int cantidadVertices) {
		if (cantidadVertices == 0) {
			throw new IllegalArgumentException("Agregá al menos un vértice.");
		}
		if (cantidadRegiones < 1 || cantidadRegiones > cantidadVertices) {
			throw new IllegalArgumentException("La cantidad de regiones debe estar entre 1 y " + cantidadVertices + ".");
		}
		if (!grafo.esConexo()) {
			throw new IllegalArgumentException("El grafo no es conexo: todos los vértices deben poder alcanzarse "
					+ "a través de alguna conexión.");
		}
	}
	
	private void visualizarSolucion(TipoPantallaDeCarga origen) {
		PantallaResultado pantallaResultado = gestorInterfaz.crearPantallaResultado();
		ResultadoPresentador resultadoPresentador = new ResultadoPresentador(pantallaResultado, gestorInterfaz, grafo, cantidadRegiones, origen);
		
		pantallaResultado.setPresentador(resultadoPresentador);
		resultadoPresentador.calcularSolucion();		
	}
	
	public void manejarClickVolverAlMenu() {
		gestorInterfaz.crearPantallaInicio();
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
	
	public Double getLatDeVertice(String nombre) {
	    Vertice v = grafo.getVertice(nombre);
	    if (v != null) {
	        return (v.getLat());
	    }
	    return null;
	}
	
	public Double getLonDeVertice(String nombre) {
	    Vertice v = grafo.getVertice(nombre);
	    if (v != null) {
	        return (v.getLon());
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
	        Map<String, double[]> coords = new HashMap<>();

	        for (CiudadSeleccionada c : capitales) {
	            nombres.add(c.getNombre());
	            coords.put(c.getNombre(), new double[]{c.getLat(), c.getLon()});
	        }

	        vista.cargarCapitales(nombres, coords);

	    } catch (Exception e) {
	        vista.mostrarMensajeError("Error al leer JSON: " + e.getMessage());
	    }
	}
}
