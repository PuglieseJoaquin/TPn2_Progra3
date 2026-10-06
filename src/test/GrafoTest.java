package test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modelo.Grafo;
import modelo.Vertice;

public class GrafoTest {

    private Grafo grafo;
    private Vertice a;
    private Vertice b;
    private Vertice c;
    private Vertice d;
    private Vertice e;

    @Before
    public void inicializar() {
        grafo = new Grafo();
        a = new Vertice("A");
        b = new Vertice("B");
        c = new Vertice("C");
        d = new Vertice("D");
        e = new Vertice("E");
    }
    @Test(expected = IllegalArgumentException.class)
    public void eliminarAristaConVerticeInexistenteLanzaExcepcionTest() {
        grafo.agregarVertice(a);
        grafo.eliminarArista(a, b);
    }
    @Test(expected = IllegalArgumentException.class)
    public void aristaConDestinoInexistenteLanzaExcepcionTest() {
        grafo.agregarVertice(a);
        grafo.agregarArista(a, b, 1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void aristaDuplicadaLanzaExcepcionTest() {
        grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, 1);
        grafo.agregarArista(a, b, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aristaDuplicadaInvertidaLanzaExcepcionTest() {
        grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, 1);
        grafo.agregarArista(b, a, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aristaHaciaSiMismoLanzaExcepcionTest() {
        grafo.agregarVertice(a);
        grafo.agregarArista(a, a, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aristaConOrigenInexistenteLanzaExcepcionTest() {
        grafo.agregarVertice(b);
        grafo.agregarArista(new Vertice("Alemania"), b,1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void eliminarAristaInexistenteLanzaExcepcionTest() {
        grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, 1.0);
        grafo.eliminarArista(a, b);
        grafo.eliminarArista(a, b);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void eliminarVerticeInexistenteLanzaExcepcionTest() {
        grafo.eliminarVertice(a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void busquedaEnGrafoVacioLanzaExcepcionTest() {
        grafo.getVertice("Argentina");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getAristasDeVerticeInexistenteLanzaExcepcionTest() {
        grafo.obtenerAristasDe(a);
    }
    @Test
    public void getVerticePorNombreDevuelveElVerticeTest() {
        grafo.agregarVertice(a);
        assertEquals(a, grafo.getVertice("A"));
    }
    @Test
    public void agregaVerticeNuevoYAumentaCantidadTest() {
        grafo.agregarVertice(a);
        assertEquals(1, grafo.cantidadVertices());
        assertTrue(grafo.obtenerVertices().contains(a));
    }

    @Test
    public void verticesConMismoNombreSonElMismoVerticeTest() {
        grafo.agregarVertice(new Vertice("Argentina"));
        grafo.agregarVertice(new Vertice("Argentina"));
        assertEquals(1, grafo.cantidadVertices());
    }

    @Test
    public void agregaAristaValidaYApareceEnAmbosVerticesTest() {
        agregoAristaPesoPositivo();
        assertEquals(1, grafo.obtenerAristasDe(a).size());
    }

    @Test
    public void aristaPuedeTenerPesoNegativoTest() {
        agregoAristaPesoNegativo();
        assertEquals(-5, grafo.obtenerAristasDe(a).get(0).getPeso(),0);
    }

    @Test
    public void aristaPuedeTenerPesoCeroTest() {
    	agregoAristaPesoCero();
        assertEquals(0, grafo.obtenerAristasDe(a).get(0).getPeso(),0);
    }

    @Test
    public void eliminaVerticeSinAristasTest() {
        grafo.agregarVertice(a);
        grafo.eliminarVertice(a);
        assertFalse(grafo.obtenerVertices().contains(a));
    }
    
    @Test
    public void eliminaAristaExistente() {
    	agregarYEliminarArista();
        assertEquals(0, grafo.obtenerAristasDe(a).size());
    }
 
    @Test
    public void getAristasDeVerticeSinAristasDevuelveListaVaciaTest() {
        grafo.agregarVertice(a);
        assertTrue(grafo.obtenerAristasDe(a).isEmpty());
    }

    @Test
    public void grafoVacioEsConexoTest() {
        assertTrue(grafo.esConexo());
    }

    @Test
    public void grafoConUnVerticeEsConexoTest() {
        grafo.agregarVertice(a);
        assertTrue(grafo.esConexo());
    }
   
    @Test
    public void grafoEnLineaEsConexo() {
    	crearGrafoConexo();
        assertTrue(crearGrafoConexo().esConexo());
    }
  
    @Test
    public void grafoConVerticesAisladosNoEsConexo() {
        assertFalse(crearGrafoInconexo().esConexo());
    }
    @Test
    public void contieneVerticeAgregadoTest() {
        grafo.agregarVertice(a);
        assertTrue(grafo.contieneVertice(a));
    }

    @Test
    public void noContieneVerticeNoAgregadoTest() {
        assertFalse(grafo.contieneVertice(a));
    }
    @Test
    public void eliminarVerticeConAristasLoQuitaDelGrafoTest() {
        agregoAristaPesoPositivo();
        grafo.eliminarVertice(a);
        assertFalse(grafo.contieneVertice(a));
    }

    @Test
    public void eliminarVerticeConAristasQuitaLaAristaDelVecinoTest() {
        agregoAristaPesoPositivo();
        grafo.eliminarVertice(a);
        assertTrue(grafo.obtenerAristasDe(b).isEmpty());
    }
    @Test
    public void eliminarAristaTambienLaQuitaDelOtroVerticeTest() {
        agregarYEliminarArista();
        assertTrue(grafo.obtenerAristasDe(b).isEmpty());
    }
    @Test
    public void todasLasAristasDeGrafoVacioEsListaVaciaTest() {
        assertTrue(grafo.obtenerTodasLasAristas().isEmpty());
    }

    @Test
    public void todasLasAristasNoDuplicaLaAristaCompartidaTest() {
        agregoAristaPesoPositivo();
        assertEquals(1, grafo.obtenerTodasLasAristas().size());
    }

    @Test
    public void todasLasAristasDeGrafoEnLineaTest() {
        assertEquals(2, crearGrafoConexo().obtenerTodasLasAristas().size());
    }
    @Test
    public void aristasOrdenadasDeGrafoVacioEsListaVaciaTest() {
        assertTrue(grafo.obtenerAristasOrdenadasMayorAMenor().isEmpty());
    }

    @Test
    public void aristasOrdenadasPrimeraEsLaDeMayorPesoTest() {
        agregoAristasConPesosDistintos();
        assertEquals(7, grafo.obtenerAristasOrdenadasMayorAMenor().get(0).getPeso(), 0);
    }

    @Test
    public void aristasOrdenadasUltimaEsLaDeMenorPesoTest() {
        agregoAristasConPesosDistintos();
        assertEquals(1, grafo.obtenerAristasOrdenadasMayorAMenor().get(1).getPeso(), 0);
    }
    @Test
    public void modificarVerticesObtenidosNoAfectaAlGrafoTest() {
        grafo.agregarVertice(a);
        grafo.obtenerVertices().clear();
        assertEquals(1, grafo.cantidadVertices());
    }

    @Test
    public void modificarAristasObtenidasNoAfectaAlGrafoTest() {
        agregoAristaPesoPositivo();
        grafo.obtenerAristasDe(a).clear();
        assertEquals(1, grafo.obtenerAristasDe(a).size());
    }
    @Test
    public void toStringIncluyeEncabezadoDelGrafoTest() {
        assertTrue(grafo.toString().contains("Grafo:"));
    }

    @Test
    public void toStringIncluyeLaSeccionDeAristasTest() {
        agregoAristaPesoPositivo();
        assertTrue(grafo.toString().contains("Aristas:"));
    }
    private void agregoAristasConPesosDistintos() {
        grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarVertice(c);
        grafo.agregarArista(a, b, 1);
        grafo.agregarArista(b, c, 7);
    }
    
    private void agregoAristaPesoPositivo() {
    	grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, 5);
    }
    private void agregoAristaPesoNegativo() {
    	grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, -5);
    }
    private void agregoAristaPesoCero() {
    	grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, 0);
    }
    private  void agregarYEliminarArista() {
    	grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, 1);
        grafo.eliminarArista(a, b);
    }
    
    private Grafo crearGrafoConexo() {
        Grafo g = new Grafo();
        g.agregarVertice(a);
        g.agregarVertice(b);
        g.agregarVertice(c);
        g.agregarArista(a, b, 1);
        g.agregarArista(b, c, 1);
        return g;
    }
    
    private Grafo crearGrafoInconexo() {
        Grafo g = crearGrafoConexo();
        g.agregarVertice(d);
        g.agregarVertice(e);
        g.agregarArista(d, e, 1);
        return g;
    }

}
