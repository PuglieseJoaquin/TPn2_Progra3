package presenter;

import modelo.Grafo;
import modelo.Solver;
import vista.GestorPantallas;
import vista.ResultadoVista;

public class ResultadoPresentador {

	private ResultadoVista resultadoVista;
	private GestorPantallas gestorPantallas;
	private Grafo grafo;
	private Solver solver;
	private int cantidadRegionesActual;

	public ResultadoPresentador(ResultadoVista resultadoVista, GestorPantallas gestorPantallas, Grafo grafo, int cantidadRegiones) {
		this.gestorPantallas = gestorPantallas;
		this.resultadoVista = resultadoVista;
		this.grafo = grafo;
		this.cantidadRegionesActual = cantidadRegiones;
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

	public void manejarClickRecalcular(int nuevaCantidad) {
		dividirYMostrar(nuevaCantidad);
	}

	public void manejarClickVolverAlMenu() {
		gestorPantallas.crearPantallaInicio();
	}

	private void dividirYMostrar(int cantidad) {
		try {
			solver.dividirEnRegiones(cantidad);
		} catch (IllegalArgumentException e) {
			resultadoVista.mostrarMensajeError(e.getMessage());
			resultadoVista.setCantidadRegiones(cantidadRegionesActual);
			return;
		}

		cantidadRegionesActual = cantidad;
		resultadoVista.mostrarResultado(solver.resultadoEnString());
	}
}