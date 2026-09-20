package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kruskal {
	/*
	. Kruskal indica que arista entra al arbol. 
	. Union-Find chequeamos ciclos de casi O(1)
	Kruskal corra a O(m log m), m = aristas
	*/

	public List<Arista> arbolGeneradorMinimo(Grafo grafo) {
        validarNoNulo(grafo);

        List<Vertice> vertices = new ArrayList<>(grafo.getVertices());
        Map<Vertice, Integer> indice = indexar(vertices);
        List<Arista> aristas = aristasOrdenadasPorPeso(grafo);

        UnionFind uf = new UnionFind(vertices.size());
        List<Arista> resultado = elegirAristas(aristas, indice, uf);

        validarConexo(uf);
        return resultado;
    }

    private void validarNoNulo(Grafo grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo no puede ser null");
        }
    }

    // UnionFind trabaja con enteros: vertice - indice 0(n-1)
    private Map<Vertice, Integer> indexar(List<Vertice> vertices) {
        Map<Vertice, Integer> indice = new HashMap<>();
        for (Vertice v : vertices) {
            indice.put(v, indice.size());
        }
        return indice;
    }

    // Siempre se prueba primero la arista más barata
    private List<Arista> aristasOrdenadasPorPeso(Grafo grafo) {
        List<Arista> aristas = grafo.getTodasLasAristas(); // copia sin orden
        Collections.sort(aristas);   // usamos el compareTo de Arista
        return aristas;
    }

    // Se detiene cuando queda una sola componente (n-1 aristas) o se acaban las aristas
    private List<Arista> elegirAristas(List<Arista> aristas, Map<Vertice, Integer> indice, UnionFind uf) {
        List<Arista> resultado = new ArrayList<>();
        int i = 0;
        while (i < aristas.size() && uf.cantidadComponentes() > 1) {
            Arista arista = aristas.get(i);
            if (uneComponentes(arista, indice, uf)) {
                resultado.add(arista);
            }
            i++;
        }
        return resultado;
    }

    // true si la arista conecta dos componentes distintas (no forma ciclo)
    private boolean uneComponentes(Arista arista, Map<Vertice, Integer> indice, UnionFind uf) {
        int origen = indice.get(arista.getOrigen());
        int destino = indice.get(arista.getDestino());
        return uf.union(origen, destino);
    }

    private void validarConexo(UnionFind uf) {
        if (uf.cantidadComponentes() > 1) {
            throw new IllegalArgumentException("El grafo no es conexo: no tiene árbol generador mínimo");
        }
    }
}