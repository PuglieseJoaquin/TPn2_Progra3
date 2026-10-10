package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modelo.Vertice;

public class VerticeTest {
	private Vertice vertice;
	private Vertice verticeConCoord;
	@Before
	public void inicializar() {
	vertice=new Vertice("Argentina");
	verticeConCoord=new Vertice("Argentina", -10, -20);
	
	}
	
	@Test
	public void obtenerNombreDelvuelveNombreTest() {
		assertEquals("Argentina",vertice.getNombre());
	}
	
	@Test
    public void constructorSimpleInicializaLatitudEnCeroTest() {
        assertEquals(0, vertice.getLat(), 0);
    }
	
	@Test
	public void constructorSimpleInicializaLongitudEnCeroTest() {
	    assertEquals(0, vertice.getLon(), 0);
	}
	
	@Test
	public void constructorConCoordenadasGuardaElNombreTest() {
	    assertEquals("Argentina", verticeConCoord.getNombre());
	}
	
	@Test
	public void constructorConCoordenadasGuardaLatitudTest() {
	    assertEquals(-10, verticeConCoord.getLat(), 0);
	}
	
	@Test
    public void constructorConCoordenadasGuardaLongitudTest() {
        assertEquals(-20, verticeConCoord.getLon(), 0);
    }
	
    @Test
    public void verticesConMismoNombreSonIgualesTest() {
        assertEquals(vertice,new Vertice("Argentina"));
    }
    
    @Test
    public void verticesConMismoNombreYDistintasCoordenadasSonIgualesTest() {
        assertEquals(verticeConCoord, new Vertice("Argentina", 3, 4));
    }
    
    @Test
    public void verticesIgualesTienenMismoHashCodeTest() {
        assertEquals(vertice.hashCode(), new Vertice("Argentina").hashCode());
    }

    @Test
    public void verticesConDistintoNombreSonDistintosTest() {
    	assertNotEquals(vertice,new Vertice("Brasil"));
    }
    
    @Test
    public void verticeNoEsIgualANullTest() {
        assertFalse(vertice.equals(null));
    }
    
    @Test
    public void verticeNoEsIgualAOtroTipoDeObjetoTest() {
        assertFalse(vertice.equals("Argentina"));
    }
    
    @Test
    public void equalsEsReflexivoTest() {
        assertTrue(vertice.equals(vertice));
    }
    
    @Test
    public void verticesConMismoNombreYDistintasCoordenadasTienenMismoHashCodeTest() {
        assertEquals(verticeConCoord.hashCode(), new Vertice("Argentina", 3, 4).hashCode());
    }
    
    @Test
    public void toStringDevuelveElNombre() {
        assertEquals("Argentina",vertice.toString());
    }
}
