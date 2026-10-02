package test;

import static org.junit.Assert.*;

import org.junit.Test;

import modelo.Vertice;

public class VerticeTest {

    @Test
    public void verticesConMismoNombreSonIgualesTest() {
        assertEquals(new Vertice("Argentina"), new Vertice("Argentina"));
    }

    @Test
    public void verticesIgualesTienenMismoHashCodeTest() {
        Vertice v1 = new Vertice("Argentina");
        Vertice v2 = new Vertice("Argentina");
        assertTrue(v1.hashCode() == v2.hashCode());
    }

    @Test
    public void verticesConDistintoNombreSonDistintosTest() {
        assertFalse(new Vertice("Argentina").equals(new Vertice("Brasil")));
    }

    @Test
    public void equalsEsReflexivoTest() {
        Vertice v = new Vertice("Argentina");
        assertTrue(v.equals(v));
    }

    @Test
    public void verticeNoEsIgualANullNiAOtroTipoTest() {
        Vertice v = new Vertice("Argentina");
        assertFalse(v.equals(null));
        assertFalse(v.equals("Argentina"));
    }

    @Test
    public void getNombreDevuelveElNombreTest() {
        assertEquals("Argentina", new Vertice("Argentina").getNombre());
    }

    @Test
    public void toStringDevuelveElNombre() {
        assertEquals("Argentina", new Vertice("Argentina").toString());
    }
}
