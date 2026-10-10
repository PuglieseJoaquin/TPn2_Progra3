package test;
import static org.junit.Assert.*;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modelo.Arista;
import modelo.Grafo;
import modelo.Kruskal;
import modelo.Vertice;

public class KruskalTest {
	private Kruskal kruskal = new Kruskal();
	private Vertice a;
	private Vertice b;
	private Vertice c;
	private Vertice d;
	
	@Before
	public void inicializar() {
		a=new Vertice("A");
		b=new Vertice("B");
		c=new Vertice("C");
		d=new Vertice("D");
	}

	@Test(expected=IllegalArgumentException.class)
	public void grafoNullTest() {
		kruskal.arbolGeneradorMinimo(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void grafoNoConexoTest() {
		kruskal.arbolGeneradorMinimo(grafoNoConexo()); 
	}
	
	@Test
	public void grafoVacioTest() {
		Grafo g = new Grafo();
		assertTrue(kruskal.arbolGeneradorMinimo(g).isEmpty());
	}

	@Test
	public void grafoUnVerticeTest() {
		assertTrue(kruskal.arbolGeneradorMinimo(grafoDeUnSoloVertice()).isEmpty());
	}

	@Test
	public void grafoDosVerticesUnaAristaTest() {
		assertEquals(1, kruskal.arbolGeneradorMinimo(grafoConDosVertices()).size());
	}

	@Test
	public void grafoConexoTieneNMenosUnoAristasTest() {
		assertEquals(3, kruskal.arbolGeneradorMinimo(inicializarConexo()).size()); 
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

	private double pesoTotal(List<Arista> aristas) {
		double total = 0;
		for (Arista a : aristas) total += a.getPeso();
		return total;
	}
	
	private Grafo grafoDeUnSoloVertice() {
		Grafo g = new Grafo();
		g.agregarVertice(a);
		return g;
	}
	private Grafo grafoConDosVertices() {
		Grafo g = new Grafo();
		g.agregarVertice(a);
		g.agregarVertice(b);
		g.agregarArista(a, b, 5);
		return g;
	}
	
	private Grafo inicializarConexo() {
		Grafo g = new Grafo();
		g.agregarVertice(a);
		g.agregarVertice(b);
		g.agregarVertice(c);
		g.agregarVertice(d);
		g.agregarArista(a,b, 1);
		g.agregarArista(b,c, 2);
		g.agregarArista(c,d, 3);
		g.agregarArista(a,d, 4);
		g.agregarArista(a,c, 5);
		
		return g;
	}

	private Grafo inicializarPesosEmpatados() {
		Grafo g = new Grafo();
		g.agregarVertice(a);
		g.agregarVertice(b);
		g.agregarVertice(c);
		g.agregarArista(a,b, 1);
		g.agregarArista(b,c, 1);
		g.agregarArista(a,c, 1);
		
		return g;
	}
	
	private Grafo grafoNoConexo() {
		Grafo g = new Grafo();
		g.agregarVertice(a);
		g.agregarVertice(b);
		g.agregarVertice(c);
		g.agregarArista(a,b, 1);
		return g;
	}

}
