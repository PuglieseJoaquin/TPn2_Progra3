package modelo;

public class UnionFind {
    private final int[] padre;
    private final int[] tamaño;
    private int cantidadComponentes;

    public UnionFind(int n) {
        if (n < 0) throw new IllegalArgumentException("n negativo");
        padre = new int[n];
        tamaño = new int[n];
        for (int i = 0; i < n; i++) {
            padre[i] = i;
            tamaño[i] = 1;
        }
        cantidadComponentes = n;
    }
    
    // Une las componentes de i y j. Devuelve false si ya estaban unidas = hay ciclo
    public boolean union(int i, int j) {
        int ri = root(i);
        int rj = root(j);
        if (ri == rj) return false;

        // el arbol más chico cuelga del más grande
        if (tamaño[ri] < tamaño[rj]) {
            int raizTemporal = ri; 
            ri = rj; 
            rj = raizTemporal;
        }
        padre[rj] = ri;
        tamaño[ri] += tamaño[rj];
        cantidadComponentes--;
        return true;
    }

    // Encuentra la raíz
    public int root(int i) {
        validar(i);
        while (padre[i] != i) {
            padre[i] = padre[padre[i]]; // cada nodo apunta a su padre
            i = padre[i];
        }
        return i;
    }

    public boolean find(int i, int j) {
        return root(i) == root(j);
    }

    
    public int cantidadComponentes() {
        return cantidadComponentes;
    }

    private void validar(int i) {
        if (i < 0 || i >= padre.length)
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + i);
    }
}