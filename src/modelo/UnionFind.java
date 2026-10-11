package modelo;

public class UnionFind {
    private int[] padre;
    private int[] tamaño;
    private int cantidadComponentes;

    public UnionFind(int cantidadVertices) {
        validarCantidadEsNumeroPositivo(cantidadVertices);
        
        padre = new int[cantidadVertices];
        tamaño = new int[cantidadVertices];
        
        for (int i = 0; i < cantidadVertices; i++) {
            padre[i] = i;
            tamaño[i] = 1;
        }       
        cantidadComponentes = cantidadVertices;
    }

	private void validarCantidadEsNumeroPositivo(int cantidadVertices) {
		if (cantidadVertices < 0)
			throw new IllegalArgumentException("La cantidad de vertices es negativo");
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
    
    public int getCantidadComponentes() {
        return cantidadComponentes;
    }
}
