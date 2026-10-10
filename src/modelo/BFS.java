package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BFS {
	
    public static Set<Vertice> verticesAlcanzablesDesde(Grafo grafo, Vertice origen) {
    	if (grafo == null)
    		throw new NullPointerException("El grafo no puede ser null.");
        if (origen == null || !grafo.contieneVertice(origen))
        	throw new IllegalArgumentException("El vertice origen no pertenece al grafo.");
    	
    	Set<Vertice> visitados = new HashSet<>();
        Queue<Vertice> porVisitar = new LinkedList<>();

        porVisitar.add(origen);
        visitados.add(origen);

        while (!porVisitar.isEmpty()) {
            Vertice actual = porVisitar.poll();
            agregarVecinosPendientes(grafo, actual, visitados, porVisitar);
        }
        return visitados;
    }
    
    
	private static void agregarVecinosPendientes(Grafo grafo, Vertice actual, Set<Vertice> visitados, Queue<Vertice> porVisitar) {
		
		for (Arista arista : grafo.obtenerAristasDe(actual)) {
		    Vertice vecino = arista.obtenerOpuesto(actual);
		    
		    if (!visitados.contains(vecino)) {
		        visitados.add(vecino);
		        porVisitar.add(vecino);
		    }
		}
	}
	
	
	public static List<Set<Vertice>> obtenerSeccionesConexas(Grafo grafo) {
	        List<Set<Vertice>> componentes = new ArrayList<>();
	        Set<Vertice> noVisitados = new HashSet<>(grafo.obtenerVertices());

	        while (!noVisitados.isEmpty()) {
	            Vertice origen = noVisitados.iterator().next();
	            Set<Vertice> componente = BFS.verticesAlcanzablesDesde(grafo, origen);

	            componentes.add(componente);
	            noVisitados.removeAll(componente);
	        }
	        return componentes;    
	}	
	
	
	public static String componentesToString(List<Set<Vertice>> componentes) {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Componentes conexas:\n");

	    int i = 1;
	    for (Set<Vertice> comp : componentes) {
	        sb.append("Componente ").append(i).append(": ");
	        sb.append(comp);
	        sb.append("\n");
	        i++;
	    }
	    return sb.toString();
	}
}
