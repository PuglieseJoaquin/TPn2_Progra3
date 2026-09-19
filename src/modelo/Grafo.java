package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Grafo {

    private final Map<Vertice, List<Arista>> listaDeVecinos;

    public Grafo() {
        this.listaDeVecinos = new HashMap<>();
    }

    public void agregarVertice(Vertice v) {
    	if(!listaDeVecinos.containsKey(v)) {
    		listaDeVecinos.put(v, new ArrayList<>());
    	}
       
    }

    public void agregarArista(Vertice origen, Vertice destino, double peso) {
    	 if (!listaDeVecinos.containsKey(origen) || !listaDeVecinos.containsKey(destino)) {
    	        throw new IllegalArgumentException("Ambos vertices deben existir en el grafo antes de agregar la arista");
    	   }
    	 if (origen.equals(destino)) {
    	        throw new IllegalArgumentException("no puede tener loop");
    	   }
    	 if (existeArista(origen, destino)) {
    	        throw new IllegalArgumentException("Ya existe una arista entre estos dos vertices");
    	   }
    	    Arista arista = new Arista(origen, destino, peso);
    	    listaDeVecinos.get(origen).add(arista);
    	    listaDeVecinos.get(destino).add(arista);
    }
    
    private boolean existeArista(Vertice a, Vertice b) {
        return buscarArista(a, b) != null;
    }
    

    public Set<Vertice> getVertices() {
    	return new HashSet<>(listaDeVecinos.keySet());
    }

    public List<Arista> getAristasDe(Vertice v) {
    	if (!listaDeVecinos.containsKey(v)) {
            throw new IllegalArgumentException("El vertice no pertenece al grafo");
        }
    	return new ArrayList<>(listaDeVecinos.get(v));
    }

    
     //aristas del grafo, sin duplicados
     
    public List<Arista> getTodasLasAristas() {
        Set<Arista> aristasUnicas = new HashSet<>();
        for (List<Arista> aristasDeUnVertice : listaDeVecinos.values()) {
            aristasUnicas.addAll(aristasDeUnVertice);
        }
        return new ArrayList<>(aristasUnicas);
    }

    
    public int cantidadVertices() {
        return listaDeVecinos.size();
    }


    public boolean esConexo() {
        if (listaDeVecinos.isEmpty()) {
            return true;
        }
        Vertice origen = listaDeVecinos.keySet().iterator().next();
        return alcanzablesDesde(origen).size() == cantidadVertices();
    }

    private Set<Vertice> alcanzablesDesde(Vertice origen) {
        Set<Vertice> visitados = new HashSet<>();
        List<Vertice> porVisitar = new LinkedList<>();

        porVisitar.add(origen);
        visitados.add(origen);

        while (!porVisitar.isEmpty()) {
            Vertice actual = porVisitar.remove(0);
            agregarVecinosPendientes(actual, visitados, porVisitar);
        }
        return visitados;
    }

    private void agregarVecinosPendientes(Vertice actual, Set<Vertice> visitados, List<Vertice> porVisitar) {
        for (Arista arista : listaDeVecinos.get(actual)) {
            Vertice vecino = arista.getOpuesto(actual);
            if (!visitados.contains(vecino)) {
                visitados.add(vecino);
                porVisitar.add(vecino);
            }
        }
    }
    public void eliminarArista(Vertice a, Vertice b) {
        if (!listaDeVecinos.containsKey(a) || !listaDeVecinos.containsKey(b)) {
            throw new IllegalArgumentException("Ambos vértices deben existir en el grafo");
        }
        Arista arista = buscarArista(a, b);
        if (arista == null) {
            throw new IllegalArgumentException("No existe una arista entre estos dos vértices");
        }
        listaDeVecinos.get(a).remove(arista);
        listaDeVecinos.get(b).remove(arista);
    }
    
    private Arista buscarArista(Vertice a, Vertice b) {
        for (Arista arista : listaDeVecinos.get(a)) {
            if (arista.getOpuesto(a).equals(b)) {
                return arista;
            }
        }
        return null;
    }
    
    
}
