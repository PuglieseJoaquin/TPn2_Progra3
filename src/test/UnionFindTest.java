package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modelo.UnionFind;

public class UnionFindTest {
	private UnionFind uf;
	
	@Before
	public void incializar() {
		uf= new UnionFind(7);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nNegativoTest() {
		new UnionFind(-1);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void rootIndiceNegativoTest() {
		uf.raiz(-1);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void rootIndiceFueraDeRangoTest() {
		uf.raiz(7);
	}

	@Test
	public void unionRootVacioTest() {
		assertEquals(0, new UnionFind(0).cantidadComponentes());
	}

	@Test
	public void unVerticeCantidadComponentesTest() {
		assertEquals(1, new UnionFind(1).cantidadComponentes());
	}

	@Test
	public void unVerticeRaizEsSiMismoTest() {
		assertEquals(0, new UnionFind(1).raiz(0));
	}

	@Test
	public void dosVerticesAisladosTest() {
		assertFalse(uf.find(0, 1));
	}

	@Test
	public void dosVerticesUnidosTest() {
		uf.union(0, 1);
		assertTrue(uf.find(0, 1));
	}

	@Test
	public void unionVerticeConsigoMismoTest() {
		assertFalse(uf.union(1, 1));
	}

	@Test
	public void unionYaConectadosDevuelveFalseTest() {
		uf.union(0, 1);
		assertFalse(uf.union(0, 1));
	}

	@Test
	public void unionYaConectadosNoCambiaComponentesTest() {
		uf.union(0, 1);
		uf.union(0, 1);
		assertEquals(6, uf.cantidadComponentes());
	}
	
	@Test
	public void inconexoVerticeMismaCompTest() {
		unirInconexo();
		assertTrue(uf.find(0, 4));
	}
	
	@Test
	public void conexoExtremosConecTest() {
		unirConexo();
		assertTrue(uf.find(0, 6));
	}
	
	@Test
	public void conexoCantidadComponentesTest() {
		unirConexo();
		assertEquals(1, uf.cantidadComponentes());
	}
	
	@Test
	public void inconexoCantidadComponentesTest() {
		unirInconexo();
		assertEquals(2, uf.cantidadComponentes());
	}
	
	@Test
	public void unionPrimerGrupoChicoDaTrueTest() {
		uf.union(1, 2);
		assertTrue(uf.union(0, 1));
	}
	@Test
	public void unionConPrimerGrupoIntercambianRaicesTest() {
		uf.union(1, 2);
		uf.union(0, 1);
		assertTrue(uf.find(0, 2));
		
	}
	
	private void unirInconexo() {
		uf.union(0, 1);
		uf.union(0, 2);
		uf.union(1, 2);
		uf.union(1, 3);
		uf.union(2, 4);
		uf.union(3, 4);
		uf.union(5, 6);
	}

	private void unirConexo() {
		for (int i = 0; i < 6; i++) {
			uf.union(i, i + 1);
		}
	}
}