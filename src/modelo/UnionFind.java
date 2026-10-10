package modelo;

public class UnionFind {
    private int[] padre;
    private int[] tamaño;
    private int cantidadComponentes;

    public UnionFind(int n) {
        if (n < 0) throw new IllegalArgumentException("n es negativo");
        padre = new int[n];
        tamaño = new int[n];
        
        for (int i = 0; i < n; i++) {
            padre[i] = i;
            tamaño[i] = 1;
        }
        cantidadComponentes = n;
    }   
    
    public boolean union(int i, int j) {
        int raizI = raiz(i);
        int raizJ = raiz(j);
        if (raizI == raizJ) return false;
        
        if (tamaño[raizI] < tamaño[raizJ]) {
            int raizTemporal = raizI; 
            raizI = raizJ; 
            raizJ = raizTemporal;
        	}
        
        padre[raizJ] = raizI;
        tamaño[raizI] += tamaño[raizJ];
        cantidadComponentes--;
        
        return true;
    }
    
    public int raiz(int i) {
        validar(i);
        while (padre[i] != i) {
            padre[i] = padre[padre[i]]; 
            i = padre[i];
        }
        return i;
    }
    
    private void validar(int i) {
        if (i < 0 || i >= padre.length)
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + i);
    }
    
    public boolean find(int i, int j) {
        return raiz(i) == raiz(j);
    }
    
    public int cantidadComponentes() {
        return cantidadComponentes;
    }
}
