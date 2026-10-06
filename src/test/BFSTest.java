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
    public void inicializar() {
        a = new Vertice("A");
        b = new Vertice("B");
        c = new Vertice("C");
        d = new Vertice("D");
        e = new Vertice("E");
    }

    @Test(expected = NullPointerException.class)
    public void grafoNullLanzaExcepcionTest() {
        BFS.verticesAlcanzablesDesde(null, a);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void origenQueNoPerteneceAlGrafoLanzaExcepcionTest() {
        BFS.verticesAlcanzablesDesde(unVertice(), b);
    }

    @Test(expected = NullPointerException.class)
    public void seccionesConexasGrafoNullLanzaExcepcionTest() {
        BFS.obtenerSeccionesConexas(null);
    }

    @Test
    public void grafoVacioDevuelveSeccionesVaciasTest() {
        assertTrue(BFS.obtenerSeccionesConexas(new Grafo()).isEmpty());
    }

    @Test
    public void unVerticeAisladoSeAlcanzaASiMismoTest() {
        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(unVertice(), a);
        assertEquals(1, alcanzables.size());
    }

    @Test
    public void unVerticeAisladoContieneAlOrigenTest() {
        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(unVertice(), a);
        assertTrue(alcanzables.contains(a));
    }

    @Test
    public void dosVerticesAisladosNoSeAlcanzanElUnoAlOtroTest() {
        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(grafoAislado(), a);
        assertFalse(alcanzables.contains(b));
    }

    @Test
    public void dosVerticesConectadosAlcanzanAlVecinoTest() {
        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(grafoconectado(), a);
        assertTrue(alcanzables.contains(b));
    }

    @Test
    public void grafoConexoCantidadDeAlcanzablesEsCorrectaTest() {
        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(crearGrafoConexo(), a);
        assertEquals(3, alcanzables.size());
    }

    @Test
    public void grafoInconexoNoAlcanzaComponenteAjenaTest() {
        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(crearGrafoInconexo(), a);
        assertFalse(alcanzables.contains(d));
    }

    @Test
    public void grafoConexoTieneUnaSolaSeccionTest() {
        List<Set<Vertice>> secciones = BFS.obtenerSeccionesConexas(crearGrafoConexo());
        assertEquals(1, secciones.size());
    }

    @Test
    public void grafoInconexoTieneDosSeccionesTest() {
        List<Set<Vertice>> secciones = BFS.obtenerSeccionesConexas(crearGrafoInconexo());
        assertEquals(2, secciones.size());
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
    
    private Grafo grafoAislado() {
   	    Grafo g = new Grafo();
   	    g.agregarVertice(a);
   	    g.agregarVertice(b);
		return g;
    
    }
    
    private Grafo unVertice() {
    	Grafo g = new Grafo();
        g.agregarVertice(a);
        return g;
    }
    
    private Grafo grafoconectado() {
    	Grafo g = new Grafo();
   	    g.agregarVertice(a);
   	    g.agregarVertice(b);
   	    g.agregarArista(a, b, 1);
		return g;
    	
    }
}