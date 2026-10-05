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
        Grafo g = new Grafo();
        g.agregarVertice(a);
        
        BFS.verticesAlcanzablesDesde(g, b);
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
        Grafo g = new Grafo();
        g.agregarVertice(a);

        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

        assertEquals(1, alcanzables.size());
    }

    @Test
    public void unVerticeAisladoContieneAlOrigenTest() {
        Grafo g = new Grafo();
        g.agregarVertice(a);

        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

        assertTrue(alcanzables.contains(a));
    }

    @Test
    public void dosVerticesAisladosNoSeAlcanzanElUnoAlOtroTest() {
        Grafo g = new Grafo();
        g.agregarVertice(a);
        g.agregarVertice(b);

        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

        assertFalse(alcanzables.contains(b));
    }

    @Test
    public void dosVerticesConectadosAlcanzanAlVecinoTest() {
        Grafo g = new Grafo();
        g.agregarVertice(a);
        g.agregarVertice(b);
        g.agregarArista(a, b, 1);

        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

        assertTrue(alcanzables.contains(b));
    }

    @Test
    public void grafoConexoCantidadDeAlcanzablesEsCorrectaTest() {
        Grafo g = crearGrafoConexo();

        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

        assertEquals(3, alcanzables.size());
    }

    @Test
    public void grafoInconexoNoAlcanzaComponenteAjenaTest() {
        Grafo g = crearGrafoInconexo();

        Set<Vertice> alcanzables = BFS.verticesAlcanzablesDesde(g, a);

        assertFalse(alcanzables.contains(d));
    }

    @Test
    public void grafoConexoTieneUnaSolaSeccionTest() {
        Grafo g = crearGrafoConexo();

        List<Set<Vertice>> secciones = BFS.obtenerSeccionesConexas(g);

        assertEquals(1, secciones.size());
    }

    @Test
    public void grafoInconexoTieneDosSeccionesTest() {
        Grafo g = crearGrafoInconexo();

        List<Set<Vertice>> secciones = BFS.obtenerSeccionesConexas(g);

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
}