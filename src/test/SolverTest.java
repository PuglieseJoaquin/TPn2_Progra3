package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import modelo.Grafo;
import modelo.Solver;
import modelo.Vertice;

public class SolverTest {
	private Vertice a; 
	private Vertice b; 
	private Vertice c; 
	private Vertice d; 
	private Vertice e;
	private Solver solver;

	@Before
	public void inicializar(){
		a = new Vertice("A");
		b = new Vertice("B");
		c = new Vertice("C");
		d = new Vertice("D");
		e = new Vertice("E");
		solver = new Solver();
		solver.calcularAGM(inicializarGrafo());
	}

	@Test(expected=IllegalStateException.class)
	public void dividirSinCalcularAGMLanzaExcepcionTest(){
		Solver s= new Solver();
		s.dividirEnRegiones(2);
	}

	@Test(expected=IllegalStateException.class)
	public void getRegionesSinDividirLanzaExcepcionTest(){
		solver.getRegiones();
	}

	@Test(expected=IllegalStateException.class)
	public void getAristasDeRegionesSinDividirLanzaExcepcionTest(){
		solver.getAristasDeRegiones();
	}

	@Test(expected=IllegalArgumentException.class)
	public void cantidadDePartesCeroLanzaExcepcionTest(){
		solver.dividirEnRegiones(0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void cantidadDePartesMayorQueVerticesLanzaExcepcionTest(){
		solver.dividirEnRegiones(6);
	}

	@Test
	public void resultadoEnStringSinDividirDevuelveMensajeEsperadoTest(){
		assertEquals("No se han calculado las regiones todavía.", solver.resultadoEnString());
	}

	@Test
	public void unaSolaRegionCantidadDeRegionesEsUnoTest() {
	    solver.dividirEnRegiones(1);
	    assertEquals(1, solver.getRegiones().size());
	}

	@Test
	public void unaSolaRegionContieneTodosLosVerticesTest(){
		solver.dividirEnRegiones(1);
		List<Set<Vertice>> regiones = solver.getRegiones();
		assertEquals(5, regiones.get(0).size());
	}

	@Test
	public void unaRegionPorVerticeCantidadDeRegionesEsIgualAVerticesTest(){
		solver.dividirEnRegiones(5);
		List<Set<Vertice>> regiones = solver.getRegiones();
		assertEquals(5, regiones.size());
	}

	@Test
	public void unaRegionPorVerticeNoTieneAristasRestantesTest(){
		solver.dividirEnRegiones(5);
		assertTrue(solver.getAristasDeRegiones().isEmpty());
	}

	@Test
	public void tresRegionesCantidadDeRegionesEsTresTest(){
		solver.dividirEnRegiones(3);
		List<Set<Vertice>> regiones = solver.getRegiones();
		assertEquals(3, regiones.size());
	}

	@Test
	public void tresRegionesRegionQueContieneAVerticeATieneTresElementosTest(){
		solver.dividirEnRegiones(3);
		List<Set<Vertice>> regiones = solver.getRegiones();
		assertEquals(3, regionDe(regiones, a).size());
	}

	@Test
	public void tresRegionesRegionQueContieneAVerticeDEsUnitariaTest(){
		solver.dividirEnRegiones(3);
		List<Set<Vertice>> regiones = solver.getRegiones();
		assertEquals(1, regionDe(regiones, d).size());
	}

	@Test
	public void tresRegionesRegionQueContieneAVerticeEEsUnitariaTest(){
		solver.dividirEnRegiones(3);
		List<Set<Vertice>> regiones = solver.getRegiones();
		assertEquals(1, regionDe(regiones, e).size());
	}

	@Test
	public void tresRegionesCantidadDeAristasRestantesEsDosTest(){
		solver.dividirEnRegiones(3);

		assertEquals(2, solver.getAristasDeRegiones().size());
	}

	@Test
	public void sePuedeDividirEnDosPartesSinRecalcularAGMTest(){
		solver.dividirEnRegiones(2);
		assertEquals(2, solver.getRegiones().size());
	}

	@Test
	public void sePuedeDividirEnCuatroPartesDespuesDeDosSinRecalcularAGMTest(){
		solver.dividirEnRegiones(2);
		solver.dividirEnRegiones(4);
		assertEquals(4, solver.getRegiones().size());
	}

	@Test
	public void getRegionesDevuelveUnaCopiaYProtegeElEstadoInternoTest(){
		solver.dividirEnRegiones(2);
		solver.getRegiones().clear();
		assertEquals(2, solver.getRegiones().size());
	}
	
	@Test
	public void resultadoEnStringConRegionesCalculadasTest() {
	    solver.calcularAGM(unVertice());
	    solver.dividirEnRegiones(1);
	    assertEquals("Componentes conexas:\nComponente 1: [A]\n", solver.resultadoEnString());
	}

	private Grafo unVertice() {
		Grafo g= new Grafo();
		g.agregarVertice(a);
		return g;
	}
	private Grafo inicializarGrafo(){
		Grafo g = new Grafo();
		g.agregarVertice(a);
		g.agregarVertice(b);
		g.agregarVertice(c);
		g.agregarVertice(d);
		g.agregarVertice(e);
		g.agregarArista(a, b, 1);
		g.agregarArista(b, c, 2);
		g.agregarArista(c, d, 10);
		g.agregarArista(d, e, 3);
		return g;
	}

	private Set<Vertice> regionDe(List<Set<Vertice>> regiones, Vertice v){
		for (Set<Vertice> region : regiones)
			if (region.contains(v))
				return region;
		return null;
	}
}