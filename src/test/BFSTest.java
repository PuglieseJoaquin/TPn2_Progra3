package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import modelo.BFS;
import modelo.Grafo;
import modelo.Vertice;

public class BFSTest{
	private Vertice a, b, c, d, e;

	@Before
	public void inicializar(){
		a = new Vertice("A");
		b = new Vertice("B");
		c = new Vertice("C");
		d = new Vertice("D");
		e = new Vertice("E");
	}

	@Test(expected=NullPointerException.class)
    public void grafoNullTest(){
        BFS.verticesAlcanzablesDesde(null, a);
    }
	
	@Test(expected=IllegalArgumentException.class)
	public void origenQueNoPerteneceAlGrafoTest(){
		Grafo g = new Grafo();
		g.agregarVertice(a);

		BFS.verticesAlcanzablesDesde(g, b);
	}

	@Test(expected=IllegalArgumentException.class)
	public void origenNullTest(){
		Grafo g = new Grafo();
		g.agregarVertice(a);

		BFS.verticesAlcanzablesDesde(g, null);
	}

	@Test(expected=NullPointerException.class)
	public void seccionesConexasGrafoNullTest(){
		BFS.obtenerSeccionesConexas(null);
	}

	@Test
	public void grafoVacioSinSeccionesTest(){
		assertTrue(BFS.obtenerSeccionesConexas(new Grafo()).isEmpty());
	}

	@Test
	public void unVerticeSeAlcanzaASiMismoTest(){
		Grafo g = new Grafo();
		g.agregarVertice(a);

		Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

		assertEquals(1, alcanzables.size());
		assertTrue(alcanzables.contains(a));
	}

	@Test
	public void dosVerticesAisladosNoSeAlcanzanTest(){
		Grafo g = new Grafo();
		g.agregarVertice(a);
		g.agregarVertice(b);

		Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

		assertEquals(1, alcanzables.size());
		assertFalse(alcanzables.contains(b));
		assertEquals(2, BFS.obtenerSeccionesConexas(g).size());
	}

	@Test
	public void dosVerticesConectadosSeAlcanzanTest(){
		Grafo g = new Grafo();
		g.agregarVertice(a);
		g.agregarVertice(b);
		g.agregarArista(a, b, 1);

		Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

		assertEquals(2, alcanzables.size());
		assertTrue(alcanzables.contains(b));
	}

	@Test
	public void grafoConexoAlcanzaTodosLosVerticesTest()
	{
		Grafo g = crearGrafoConexo();

		Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

		assertEquals(3, alcanzables.size());
		assertTrue(alcanzables.contains(a));
		assertTrue(alcanzables.contains(b));
		assertTrue(alcanzables.contains(c));
	}

	@Test
	public void grafoConexoAlcanzaTodosDesdeCualquierOrigenTest(){
		Grafo g = crearGrafoConexo();

		assertEquals(3, BFS.verticesAlcanzablesDesde(g, a).size());
		assertEquals(3, BFS.verticesAlcanzablesDesde(g, b).size());
		assertEquals(3, BFS.verticesAlcanzablesDesde(g, c).size());
	}

	@Test
	public void grafoConCicloAlcanzaTodosSinRepetirTest(){
		Grafo g = crearGrafoConexo();
		g.agregarArista(a, c, 1);

		assertEquals(3, BFS.verticesAlcanzablesDesde(g, a).size());
	}

	@Test
	public void grafoInconexoNoAlcanzaOtraComponenteTest(){
		Grafo g = crearGrafoInconexo();

		Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

		assertEquals(3, alcanzables.size());
		assertFalse(alcanzables.contains(d));
		assertFalse(alcanzables.contains(e));
	}

	@Test
	public void grafoConexoTieneUnaSolaSeccionTest(){
		Grafo g = crearGrafoConexo();

		List<Set<Vertice>> secciones = BFS.obtenerSeccionesConexas(g);

		assertEquals(1, secciones.size());
		assertEquals(3, secciones.get(0).size());
	}

	@Test
	public void grafoInconexoTieneDosSeccionesTest(){
		Grafo g = crearGrafoInconexo();

		List<Set<Vertice>> secciones = BFS.obtenerSeccionesConexas(g);

		assertEquals(2, secciones.size());
		assertEquals(5, secciones.get(0).size() + secciones.get(1).size());
	}

	@Test
	public void cadaVerticeEstaEnUnaSolaSeccionTest(){
		Grafo g = crearGrafoInconexo();
		List<Set<Vertice>> secciones = BFS.obtenerSeccionesConexas(g);

		for (Vertice v : g.getVertices()){
			int apariciones = 0;
			for (Set<Vertice> seccion : secciones)
				if (seccion.contains(v))
					apariciones++;

			assertEquals(1, apariciones);
		}
	}

	// Auxiliares: A-B-C
	private Grafo crearGrafoConexo(){
		Grafo g = new Grafo();
		g.agregarVertice(a);
		g.agregarVertice(b);
		g.agregarVertice(c);
		g.agregarArista(a, b, 1);
		g.agregarArista(b, c, 1);
		return g;
	}

	// A-B-C    y    D-E
	private Grafo crearGrafoInconexo(){
		Grafo g = crearGrafoConexo();
		g.agregarVertice(d);
		g.agregarVertice(e);
		g.agregarArista(d, e, 1);
		return g;
	}
}