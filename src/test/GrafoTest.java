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

    @Before
    public void setUp() {
        grafo = new Grafo();
        a = new Vertice("A");
        b = new Vertice("B");
        c = new Vertice("C");
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
        grafo.getAristasDe(a);
    }

    @Test
    public void agregaVerticeNuevoYAumentaCantidadTest() {
        grafo.agregarVertice(a);
        assertEquals(1, grafo.cantidadVertices());
        assertTrue(grafo.getVertices().contains(a));
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
        assertEquals(1, grafo.getAristasDe(a).size());
        assertEquals(1, grafo.getAristasDe(b).size());
    }

    @Test
    public void aristaPuedeTenerPesoNegativoTest() {
        agregoAristaPesoNegativo();
        assertEquals(-5, grafo.getAristasDe(a).get(0).getPeso(),0);
    }

    @Test
    public void aristaPuedeTenerPesoCeroTest() {
    	agregoAristaPesoCero();
        assertEquals(0, grafo.getAristasDe(a).get(0).getPeso(),0);
    }

    @Test
    public void eliminaVerticeSinAristasTest() {
        grafo.agregarVertice(a);
        grafo.eliminarVertice(a);
        assertEquals(0, grafo.cantidadVertices());
        assertFalse(grafo.getVertices().contains(a));
    }
    
    @Test
    public void eliminaAristaExistente() {
        agregarYEliminarArista();
        grafo.eliminarArista(a, b);
        assertEquals(0, grafo.getAristasDe(a).size());
        assertEquals(0, grafo.getAristasDe(b).size());
    }
 
    @Test
    public void getAristasDeVerticeSinAristasDevuelveListaVaciaTest() {
        grafo.agregarVertice(a);
        assertTrue(grafo.getAristasDe(a).isEmpty());
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
        grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarVertice(c);
        grafo.agregarArista(a, b, 1.0);
        grafo.agregarArista(b, c, 2.0);
        assertTrue(grafo.esConexo());
    }

    @Test
    public void grafoConVerticesAisladosNoEsConexo() {
        grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        assertFalse(grafo.esConexo());
    }

    
    public void agregoAristaPesoPositivo() {
    	grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, 5);
    }
    public void agregoAristaPesoNegativo() {
    	grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, -5);
    }
    public void agregoAristaPesoCero() {
    	grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, 0);
    }
    public  void agregarYEliminarArista() {
    	grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarArista(a, b, 1.0);
    }
}
