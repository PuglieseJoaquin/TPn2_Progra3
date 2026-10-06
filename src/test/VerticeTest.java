package test;

import static org.junit.Assert.*;

import org.junit.Test;

import modelo.Vertice;

public class VerticeTest {
	@Test
	public void obtenerNombreDelvuelveNombreTest() {
		Vertice v= new Vertice("Argentina");
		assertEquals("Argentina",v.getNombre());
	}
	@Test
    public void constructorSimpleInicializaLatitudEnCeroTest() {
        Vertice v = new Vertice("Argentina");
        assertEquals(0, v.getLat(), 0);
    }
	@Test
	public void constructorSimpleInicializaLongitudEnCeroTest() {
	    Vertice v = new Vertice("Argentina");
	    assertEquals(0, v.getLon(), 0);
	}
	@Test
	public void constructorConCoordenadasGuardaElNombreTest() {
	    Vertice v = new Vertice("Argentina", -10, -20);
	    assertEquals("Argentina", v.getNombre());
	}
	@Test
	public void constructorConCoordenadasGuardaLatitudTest() {
	    Vertice v = new Vertice("Argentina", -10, -20);
	    assertEquals(-10, v.getLat(), 0);
	}
	@Test
    public void constructorConCoordenadasGuardaLongitudTest() {
        Vertice v = new Vertice("Argentina", -10, -20);
        assertEquals(-20, v.getLon(), 0);
    }
    @Test
    public void verticesConMismoNombreSonIgualesTest() {
    	Vertice v= new Vertice("Argentina");
        assertEquals(v,new Vertice("Argentina"));
    }
    @Test
    public void verticesConMismoNombreYDistintasCoordenadasSonIgualesTest() {
        Vertice v = new Vertice("Argentina", 1, 2);
        assertEquals(v, new Vertice("Argentina", 3, 4));
    }
    @Test
    public void verticesIgualesTienenMismoHashCodeTest() {
        Vertice v1 = new Vertice("Argentina");
        assertEquals(v1.hashCode(), new Vertice("Argentina").hashCode());
    }

    @Test
    public void verticesConDistintoNombreSonDistintosTest() {
        Vertice v= new Vertice("Argentina");
    	assertNotEquals(v,new Vertice("Brasil"));
    }
    @Test
    public void verticeNoEsIgualANullTest() {
        Vertice v = new Vertice("Argentina");
        assertFalse(v.equals(null));
    }
    @Test
    public void verticeNoEsIgualAOtroTipoDeObjetoTest() {
        Vertice v = new Vertice("Argentina");
        assertFalse(v.equals("Argentina"));
    }
    @Test
    public void equalsEsReflexivoTest() {
        Vertice v = new Vertice("Argentina");
        assertTrue(v.equals(v));
    }
    @Test
    public void verticesConMismoNombreYDistintasCoordenadasTienenMismoHashCodeTest() {
        Vertice v = new Vertice("Argentina", -10, -20);
        assertEquals(v.hashCode(), new Vertice("Argentina", 3, 4).hashCode());
    }
    @Test
    public void toStringDevuelveElNombre() {
    	Vertice v= new Vertice("Argentina");
        assertEquals("Argentina",v.toString());
    }
}
