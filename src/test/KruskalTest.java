package test;
import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

import modelo.Arista;
import modelo.Grafo;
import modelo.Kruskal;
import modelo.Vertice;

public class KruskalTest {
	private Kruskal kruskal = new Kruskal();

	@Test(expected=IllegalArgumentException.class)
	public void grafoNullTest() {
		kruskal.arbolGeneradorMinimo(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void grafoNoConexoTest() {
		Grafo g = new Grafo();
		g.agregarVertice(v("A"));
		g.agregarVertice(v("B"));
		g.agregarVertice(v("C"));
		g.agregarArista(v("A"), v("B"), 1);
		
		kruskal.arbolGeneradorMinimo(g); 
	}
	
	@Test
	public void grafoVacioTest() {
		Grafo g = new Grafo();
		assertTrue(kruskal.arbolGeneradorMinimo(g).isEmpty());
	}

	@Test
	public void grafoUnVerticeTest() {
		Grafo g = new Grafo();
		g.agregarVertice(v("A"));
		assertTrue(kruskal.arbolGeneradorMinimo(g).isEmpty());
	}

	@Test
	public void grafoDosVerticesUnaAristaTest() {
		Grafo g = new Grafo();
		g.agregarVertice(v("A"));
		g.agregarVertice(v("B"));
		g.agregarArista(v("A"), v("B"), 5);
		
		assertEquals(1, kruskal.arbolGeneradorMinimo(g).size());
	}

	@Test
	public void grafoConexoTieneNMenosUnoAristasTest() {
		Grafo g = inicializarConexo();
		
		assertEquals(3, kruskal.arbolGeneradorMinimo(g).size()); 
	}

	private List<Arista> agmConPesosEmpatados() {
	    return kruskal.arbolGeneradorMinimo(inicializarPesosEmpatados());
	}

	@Test
	public void pesosIgualesCantidadDeAristasTest() {
	    assertEquals(2, agmConPesosEmpatados().size());
	}

	@Test
	public void pesosIgualesPesoTotalTest() {
	    assertEquals(2.0, pesoTotal(agmConPesosEmpatados()), 0.0001);
	}

	private Vertice v(String nombre) {
		return new Vertice(nombre); 
	}

	private double pesoTotal(List<Arista> aristas) {
		double total = 0;
		for (Arista a : aristas) total += a.getPeso();
		return total;
	}
	

	private Grafo inicializarConexo() {
		Grafo g = new Grafo();
		g.agregarVertice(v("A"));
		g.agregarVertice(v("B"));
		g.agregarVertice(v("C"));
		g.agregarVertice(v("D"));
		g.agregarArista(v("A"), v("B"), 1);
		g.agregarArista(v("B"), v("C"), 2);
		g.agregarArista(v("C"), v("D"), 3);
		g.agregarArista(v("A"), v("D"), 4);
		g.agregarArista(v("A"), v("C"), 5);
		
		return g;
	}

	private Grafo inicializarPesosEmpatados() {
		Grafo g = new Grafo();
		g.agregarVertice(v("A"));
		g.agregarVertice(v("B"));
		g.agregarVertice(v("C"));
		g.agregarArista(v("A"), v("B"), 1);
		g.agregarArista(v("B"), v("C"), 1);
		g.agregarArista(v("A"), v("C"), 1);
		
		return g;
	}

}
