package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kruskal {

	public List<Arista> arbolGeneradorMinimo(Grafo grafo) {
        validarNoNulo(grafo);

        List<Vertice> listaDeVertices = new ArrayList<>(grafo.obtenerVertices());
        Map<Vertice, Integer> mapaVerticePosicion = indexar(listaDeVertices);
        List<Arista> listaDeAristas = aristasOrdenadasPorPeso(grafo);

        UnionFind arbolUnionFind = new UnionFind(listaDeVertices.size());
        
        List<Arista> resultadoAristasMinimas = elegirAristas(listaDeAristas, mapaVerticePosicion, arbolUnionFind);

        validarConexo(arbolUnionFind); 
        return resultadoAristasMinimas;
    }	
	
	private List<Arista> aristasOrdenadasPorPeso(Grafo grafo) {
        List<Arista> aristas = grafo.obtenerTodasLasAristas(); 
        Collections.sort(aristas);
        return aristas;
    }
	
	private List<Arista> elegirAristas(List<Arista> listaDeAristas, Map<Vertice, Integer> mapaVerticePosicion, UnionFind uf) {
    	
        List<Arista> resultado = new ArrayList<>();
        int i = 0;
        while (i < listaDeAristas.size() && uf.cantidadComponentes() > 1) {
            Arista arista = listaDeAristas.get(i);
            if (puedeUnirComponentes(arista, mapaVerticePosicion, uf)) {
                resultado.add(arista);
            }
            i++;
        }
        return resultado;
    }
		
	private boolean puedeUnirComponentes(Arista arista, Map<Vertice, Integer> indice, UnionFind uf) {
        int origen = indice.get(arista.getOrigen());
        int destino = indice.get(arista.getDestino());
        return uf.union(origen, destino);
    }

	private void validarNoNulo(Grafo grafo) {
        if (grafo == null) throw new IllegalArgumentException("El grafo no puede ser null.");
    }
       
    private Map<Vertice, Integer> indexar(List<Vertice> vertices) {
        Map<Vertice, Integer> indice = new HashMap<>();
        for (Vertice v : vertices) {
            indice.put(v, indice.size());
        }
        return indice;
    }

    private void validarConexo(UnionFind uf) {
        if (uf.cantidadComponentes() > 1) {
            throw new IllegalArgumentException("El grafo no es conexo: no tiene arbol generador minimo");
        }
    }
}
