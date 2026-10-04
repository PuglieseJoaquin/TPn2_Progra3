package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Grafo {

    private final Map<Vertice, List<Arista>> mapaDeListaDeVecinos;

    public Grafo() {
        this.mapaDeListaDeVecinos = new HashMap<>();
    }

    public void agregarVertice(Vertice v) {
    	
    		if(!mapaDeListaDeVecinos.containsKey(v)) 
    		mapaDeListaDeVecinos.put(v, new ArrayList<>());    
    }

    public void agregarArista(Vertice origen, Vertice destino, double peso) {
    	
    	 	if (validarArista(origen, destino)) {
    	 		Arista arista = new Arista(origen, destino, peso);
    	 		mapaDeListaDeVecinos.get(origen).add(arista);
    	 		mapaDeListaDeVecinos.get(destino).add(arista);
    	 	}
    }
    public boolean contieneVertice(Vertice v) {
        return mapaDeListaDeVecinos.containsKey(v);
    }
    
    public void eliminarVertice(Vertice v) {
        if (!mapaDeListaDeVecinos.containsKey(v))
            throw new IllegalArgumentException("El vertice no pertenece al grafo");

        for (Arista arista : new ArrayList<>(mapaDeListaDeVecinos.get(v))) {
            mapaDeListaDeVecinos.get(arista.getOpuesto(v)).remove(arista);
        }
        mapaDeListaDeVecinos.remove(v);
    }
    
    public Vertice getVertice(String nombre) {
        for (Vertice v : mapaDeListaDeVecinos.keySet()) {
            if (v.getNombre().equals(nombre)) {
                return v;
            }
        }
        return null;
    }
    
    private boolean validarArista(Vertice origen, Vertice destino) {
    	
    		if (!mapaDeListaDeVecinos.containsKey(origen) || !mapaDeListaDeVecinos.containsKey(destino))
    			throw new IllegalArgumentException("Ambos vertices deben existir en el grafo antes de agregar la arista.");
	   
    		else if (origen.equals(destino))
			 throw new IllegalArgumentException("Origen debe ser distino a destino.");
		   
    		else if (buscarArista(origen, destino) != null)
			 throw new IllegalArgumentException("Ya existe una arista entre estos dos vertices");
    			
    		else {return true;}
    }
    
    public Set<Vertice> getVertices() {
    		return new HashSet<>(mapaDeListaDeVecinos.keySet());
    }

    public List<Arista> getAristasDe(Vertice v) {
    	
		if (mapaDeListaDeVecinos.containsKey(v)) {
			return new ArrayList<>(mapaDeListaDeVecinos.get(v));
		} else {
			return Collections.emptyList();
		}
    }
     
    public List<Arista> getTodasLasAristas() {
        Set<Arista> aristasUnicas = new HashSet<>();
        for (List<Arista> aristasDeUnVertice : mapaDeListaDeVecinos.values()) {
            aristasUnicas.addAll(aristasDeUnVertice);
        }
        return new ArrayList<>(aristasUnicas);
    }
   
    public int cantidadVertices() {
        return mapaDeListaDeVecinos.size();
    }

    public boolean esConexo() {
        if (mapaDeListaDeVecinos.isEmpty())
            return true;
        
        Vertice origen = mapaDeListaDeVecinos.keySet().iterator().next();
        
        int tamanioVerticesConectados = BFS.verticesAlcanzablesDesde(this, origen).size();
        
        
       return tamanioVerticesConectados == cantidadVertices();
    }

    
    public void eliminarArista(Vertice a, Vertice b) {
        if (!mapaDeListaDeVecinos.containsKey(a) || !mapaDeListaDeVecinos.containsKey(b)) {
            throw new IllegalArgumentException("Ambos vértices deben existir en el grafo");
        }
        Arista arista = buscarArista(a, b);
        if (arista == null) {
            throw new IllegalArgumentException("No existe una arista entre estos dos vértices");
        }
        mapaDeListaDeVecinos.get(a).remove(arista);
        mapaDeListaDeVecinos.get(b).remove(arista);
    }
    
    private Arista buscarArista(Vertice a, Vertice b) {
        for (Arista arista : mapaDeListaDeVecinos.get(a)) {
            if (arista.getOpuesto(a).equals(b)) {
                return arista;
            }
        }
        return null;
    }
    
    public List<Arista> getAristasOrdenadasMayorAMenor() {
        List<Arista> ordenadas = new ArrayList<>(getTodasLasAristas());
        Collections.sort(ordenadas, Collections.reverseOrder());
        return ordenadas;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grafo:\n");

        sb.append("Vértices: ");
        sb.append(getVertices());
        sb.append("\n");

        sb.append("Aristas:\n");
        for (Arista a : getTodasLasAristas()) {
            sb.append("  ").append(a).append("\n");
        }

        return sb.toString();
    }
    
}
