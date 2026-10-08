package presenter;

import java.util.Set;

import modelo.*;
import vista.*;

public class ResultadoPresentador {
	private ResultadoVista resultadoVista;
	private InterfazGestorPantalla gestorInterfaz;
	private Grafo grafo;
	private Solver solver;
	private int cantidadRegionesActual;
	private TipoPantallaDeCarga origen;

	public ResultadoPresentador(ResultadoVista resultadoVista, InterfazGestorPantalla gestorInterfaz, Grafo grafo, int cantidadRegiones, TipoPantallaDeCarga origen) {
		this.gestorInterfaz = gestorInterfaz;
		this.resultadoVista = resultadoVista;
		this.grafo = grafo;
		this.cantidadRegionesActual = cantidadRegiones;
		this.origen = origen;
		this.solver = new Solver();
	}
	
	public void calcularSolucion() {
		try {
			solver.calcularAGM(grafo);
		} catch (IllegalArgumentException e) {
			resultadoVista.mostrarMensajeError(e.getMessage());
			return;
		}
		
		resultadoVista.setCantidadRegiones(cantidadRegionesActual);
		dividirYMostrar(cantidadRegionesActual);
	}
	
	private void dividirYMostrar(int cantidad) {
		try {
			solver.dividirEnRegiones(cantidad);		
			decidirComoMostrarResultado();
	        
		} catch (IllegalArgumentException e) {
			resultadoVista.mostrarMensajeError(e.getMessage());
			resultadoVista.setCantidadRegiones(cantidadRegionesActual);
			return;
		}

		cantidadRegionesActual = cantidad;
		resultadoVista.mostrarResultado(solver.resultadoEnString());
	}

	private void decidirComoMostrarResultado() {
		if (origen == TipoPantallaDeCarga.JSON || origen == TipoPantallaDeCarga.MAPA) {
		borrarDibujosEnMapas();
		dibujarAristasSolucion();
		dibujarVerticesSolucion();
		} else {
			resultadoVista.mostrarDatosSinMapa();
		}
	}

	private void borrarDibujosEnMapas() {
		resultadoVista.borrarDibujosEnMapa();		
	}

	private void dibujarAristasSolucion() {
		for (Arista arista : solver.getAristasDeRegiones()) {
		    Vertice origen = arista.getOrigen();
		    Vertice destino = arista.getDestino();
		    
		    resultadoVista.dibujarConexion(origen.getLat(), origen.getLon(), destino.getLat(), destino.getLon());
		}
	}	
	
	private void dibujarVerticesSolucion() {
		for (Set<Vertice> region : solver.getRegiones()) {
		    for (Vertice v : region) {
		        resultadoVista.dibujarVertice(v.getNombre(), v.getLat(), v.getLon());
		    }
		}
	}

	public void manejarClickRecalcular(int nuevaCantidad) {
		dividirYMostrar(nuevaCantidad);
	}

	
	public void manejarClickVolverAlMenu() {
		gestorInterfaz.crearPantallaInicio();
	}
	
	public void manejarClickModificarDatos() {
        switch (origen) {
        case MANUAL:
        	gestorInterfaz.mostrarPantallaCargaManual();
            break;
        case JSON:
        	gestorInterfaz.mostrarPantallaCargaDesdeJSON();
            break;
        case MAPA:
        	gestorInterfaz.mostrarPantallaCargaDesdeMapa();
            break;
		}
	}

	public void manejarClickBtnSalir() {
		System.exit(0);
	}
}
