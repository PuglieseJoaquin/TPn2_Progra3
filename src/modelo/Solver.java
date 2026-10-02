package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {
    private List<Arista> aristasMinimasConexas;
    private Grafo agm;
    private List<Set<Vertice>> regiones;

    public void calcularAGM(Grafo grafoOriginal) {
        Kruskal kruskal = new Kruskal();
        this.aristasMinimasConexas = kruskal.arbolGeneradorMinimo(grafoOriginal);
        this.agm = new Grafo();
        
        crearGrafoDesdeAristasMinimas(grafoOriginal, aristasMinimasConexas);
    }

    private void crearGrafoDesdeAristasMinimas(Grafo grafoOriginal, List<Arista> aristasMinimasConexas) {
    	
        for (Vertice v : grafoOriginal.getVertices()) {
            agm.agregarVertice(v);
        }
        for (Arista a : aristasMinimasConexas) {
        		agm.agregarArista(a.getOrigen(), a.getDestino(), a.getPeso());
        }
	}

    public void dividirEnRegiones(int cantidadPartes) {
    	
        if (agm == null) throw new IllegalStateException("AGM no calculado");

        List<Arista> ordenadas = new ArrayList<>(agm.getAristasOrdenadasMayorAMenor());
        
        for (int i = 0; i < cantidadPartes-1; i++) {
            Arista masPesada = ordenadas.get(i);
            agm.eliminarArista(masPesada.getOrigen(), masPesada.getDestino());
        }
          
        regiones = BFS.obtenerSeccionesConexas(agm);
    }
    
    public String resultadoEnString() {
        if (regiones == null) {
            return "No se han calculado las regiones todavía.";
        }

        return BFS.componentesToString(regiones);
    }
}
