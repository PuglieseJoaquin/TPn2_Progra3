package presenter;

import modelo.Grafo;
import modelo.Vertice;
import vista.GestorPantallas;

public class CargaDeDatosPresentador {
	private GestorPantallas gestorPantallas;
    private Grafo grafo;

    public CargaDeDatosPresentador(GestorPantallas gestorPantallas) {
    		this.gestorPantallas = gestorPantallas;
        this.grafo = new Grafo();
    }

    public void agregarVertice(String nombre) {
        grafo.agregarVertice(new Vertice(nombre));
    }

    public void agregarArista(String origen, String destino, double peso) {
        Vertice v1 = grafo.getVertice(origen);
        Vertice v2 = grafo.getVertice(destino);
        
        grafo.agregarArista(v1, v2, peso);
    }

    public void mostrarResultado(int partes) {
        ResultadoPresentador resultadoPresentador = new ResultadoPresentador(gestorPantallas, grafo, partes);
        resultadoPresentador.resolverGrafo();
    }

	public boolean esGrafoConexo() {
		return grafo.esConexo();
	}
}
