package presenter;

import modelo.Grafo;
import modelo.Solver;
import vista.GestorPantallas;
import vista.ResultadoVista;

public class ResultadoPresentador {

	private ResultadoVista vista;
	private GestorPantallas gestorPantallas;
	private Grafo grafo;
	private Solver solver;
	private int cantidadRegionesActual;

	public ResultadoPresentador(ResultadoVista vista, GestorPantallas gestorPantallas, Grafo grafo,
			int cantidadRegiones) {
		this.vista = vista;
		this.gestorPantallas = gestorPantallas;
		this.grafo = grafo;
		this.solver = new Solver();
		this.cantidadRegionesActual = cantidadRegiones;
	}

	/** La pantalla lo llama cuando termina de construirse. */
	public void actualizarVista() {
		try {
			solver.calcularAGM(grafo);
		} catch (IllegalArgumentException e) {
			vista.mostrarMensajeError(e.getMessage());
			return;
		}
		vista.setCantidadRegiones(cantidadRegionesActual);
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
			vista.mostrarMensajeError(e.getMessage());
			vista.setCantidadRegiones(cantidadRegionesActual); // vuelve al último valor válido
			return;
		}

		cantidadRegionesActual = cantidad;
		vista.mostrarResultado(solver.resultadoEnString());
	}
}