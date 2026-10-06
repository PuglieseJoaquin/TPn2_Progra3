package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {
    private Set<Vertice> vertices;
    private List<Arista> aristasAGM;
    private List<Arista> aristasDeRegiones;
    private List<Set<Vertice>> regiones;

    public void calcularAGM(Grafo grafoOriginal) {
    	
        Kruskal kruskal = new Kruskal();
        this.aristasAGM = kruskal.arbolGeneradorMinimo(grafoOriginal);
        this.vertices = grafoOriginal.obtenerVertices();
        this.aristasDeRegiones = null;
        this.regiones = null;
    }

    
    public void dividirEnRegiones(int cantidadPartes) {
        if (aristasAGM == null) throw new IllegalStateException("AGM no calculado");
        validarCantidadPartes(cantidadPartes);

        Grafo arbol = crearGrafoDesdeAristas(aristasAGM);
        List<Arista> ordenadas = arbol.obtenerAristasOrdenadasMayorAMenor();

        for (int i = 0; i < cantidadPartes - 1; i++) {
            Arista masPesada = ordenadas.get(i);
            arbol.eliminarArista(masPesada.getOrigen(), masPesada.getDestino());
        }

        regiones = BFS.obtenerSeccionesConexas(arbol);
        aristasDeRegiones = arbol.obtenerTodasLasAristas();
    }

    
    private void validarCantidadPartes(int cantidadPartes) {
        if (cantidadPartes < 1 || cantidadPartes > vertices.size())
            throw new IllegalArgumentException(
                    "La cantidad de regiones debe estar entre 1 y " + vertices.size() + ".");
    }

    
    private Grafo crearGrafoDesdeAristas(List<Arista> aristas) {
        Grafo grafo = new Grafo();
        for (Vertice v : vertices) {
            grafo.agregarVertice(v);
        }
        for (Arista a : aristas) {
            grafo.agregarArista(a.getOrigen(), a.getDestino(), a.getPeso());
        }
        return grafo;
    }

    
    public List<Set<Vertice>> getRegiones() {
        if (regiones == null) throw new IllegalStateException("Las regiones no fueron calculadas");

        List<Set<Vertice>> copia = new ArrayList<>();
        for (Set<Vertice> region : regiones) {
            copia.add(new HashSet<>(region));
        }
        return copia;
    }

    
    public List<Arista> getAristasDeRegiones() {
        if (aristasDeRegiones == null) throw new IllegalStateException("Las regiones no fueron calculadas");
        return new ArrayList<>(aristasDeRegiones);
    }

    
    public String resultadoEnString() {
        if (regiones == null) {
            return "No se han calculado las regiones todavía.";
        }
        return BFS.componentesToString(regiones);
    }
}
