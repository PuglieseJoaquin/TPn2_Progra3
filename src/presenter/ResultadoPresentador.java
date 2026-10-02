package presenter;

import modelo.Grafo;
import modelo.Solver;
import vista.GestorPantallas;

public class ResultadoPresentador {
    private GestorPantallas gestorPantallas;
    private Grafo grafo;
    private int cantidadPartes;

	public ResultadoPresentador(GestorPantallas gestorPantallas, Grafo grafo, int partes) {
        this.gestorPantallas = gestorPantallas;
        this.grafo = grafo;
        this.cantidadPartes = partes;
	}

    public void resolverGrafo() {

        Solver solver = new Solver();
        solver.calcularAGM(grafo);
        solver.dividirEnRegiones(cantidadPartes);
                
        String resultado = solver.resultadoEnString();

        gestorPantallas.crearPantallaResultado(resultado);
    }
}
