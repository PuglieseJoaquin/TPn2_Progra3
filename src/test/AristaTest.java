package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import modelo.Arista;
import modelo.Vertice;

public class AristaTest {

    private Vertice a;
    private Vertice b;

    @Before
    public void Inicializacion() {
        a = new Vertice("Argentina");
        b = new Vertice("Brasil");
    }

    @Test
    public void obtenerOrigenTest() {
        Arista arista = new Arista(a, b, 10);
        assertEquals(a, arista.getOrigen());
    }

    @Test
    public void obtenerDestinoTest() {
        Arista arista = new Arista(a, b, 10);
        assertEquals(b, arista.getDestino());
    }

    @Test
    public void obtenerPesoTest() {
        Arista arista = new Arista(a, b, 7);
        assertEquals(7, arista.getPeso(),0);
    }

    @Test
    public void obtenerExtremoTest() {
        Arista arista = new Arista(a, b, 10);
        assertEquals(b, arista.getOpuesto(a));
        assertEquals(a, arista.getOpuesto(b));
    }

    @Test
    public void compareToOrdenaDeMayorAMenorTest() {
        Arista liviana = new Arista(a, b, 1.0);
        Arista pesada = new Arista(a, b, 10.0);
        assertTrue(liviana.compareTo(pesada) < 0); 
        assertTrue(pesada.compareTo(liviana) > 0);
    }

    @Test
    public void compareToPesoTest() {
        Arista a1 = new Arista(a, b, 5.0);
        Arista a2 = new Arista(a, b, 5.0);
        assertEquals(0, a1.compareTo(a2));
    }

   
}