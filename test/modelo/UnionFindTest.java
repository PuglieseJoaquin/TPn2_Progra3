package modelo;

import static org.junit.Assert.*;
import org.junit.Test;

public class UnionFindTest 
{
	@Test(expected=IllegalArgumentException.class)
	public void nNegativoTest() {
		new UnionFind(-1);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void rootIndiceNegativoTest() {
		new UnionFind(5).root(-1);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void rootIndiceFueraDeRangoTest() {
		new UnionFind(5).root(5);
	}

	@Test
	public void unionRootVacioTest() {
		assertEquals(0, new UnionFind(0).cantidadComponentes());
	}

	@Test
	public void unionRootUnVerticeTest() {
		UnionFind uf = new UnionFind(1);
		assertEquals(1, uf.cantidadComponentes());
		assertEquals(0, uf.root(0));
	}

	@Test
	public void dosVerticesAisladosTest() {
		UnionFind uf = new UnionFind(2);
		assertFalse(uf.find(0, 1));
	}

	@Test
	public void dosVerticesUnidosTest() {
		UnionFind uf = new UnionFind(2);
		uf.union(0, 1);
		assertTrue(uf.find(0, 1));
	}

	@Test
	public void unionVerticeConsigoMismoTest() {
		UnionFind uf = new UnionFind(3);
		assertFalse(uf.union(1, 1));
	}

	@Test
	public void unionYaConectadosTest() {
		UnionFind uf = new UnionFind(3);
		uf.union(0, 1);
		assertFalse(uf.union(0, 1));
		assertEquals(2, uf.cantidadComponentes());
	}

	@Test
	public void inconexoTest() {
		UnionFind uf = inicializarInconexo();
		
		assertTrue(uf.find(0, 4));
		assertFalse(uf.find(0, 5));
		assertEquals(2, uf.cantidadComponentes());
	}

	@Test
	public void conexoTest() {
		UnionFind uf = inicializarConexo();
		
		assertTrue(uf.find(0, 6));
		assertEquals(1, uf.cantidadComponentes());
	}

	private UnionFind inicializarInconexo() {
		UnionFind uf = new UnionFind(7);
		uf.union(0, 1);
		uf.union(0, 2);
		uf.union(1, 2);
		uf.union(1, 3);
		uf.union(2, 4);
		uf.union(3, 4);
		uf.union(5, 6);
		
		return uf;		
	}

	private UnionFind inicializarConexo() {
		UnionFind uf = new UnionFind(7);
		uf.union(0, 1);
		uf.union(1, 2);
		uf.union(2, 3);
		uf.union(3, 4);
		uf.union(4, 5);
		uf.union(5, 6);
		
		return uf;		
	}
}