package modelo;

public class Arista implements Comparable<Arista> {

    private Vertice origen;
    private Vertice destino;
    private double peso;

    public Arista(Vertice origen, Vertice destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }
    
    public Vertice getOrigen() {
        return origen;
    }
    
    public Vertice getDestino() {
        return destino;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public Vertice obtenerOpuesto(Vertice v) {
    	if (getOrigen().equals(v)) return getDestino();
    	if (getDestino().equals(v)) return getOrigen();
    	throw new IllegalArgumentException("El vertice no pertenece a esta arista.");
    }
    
    @Override
    public int compareTo(Arista otra) {
        return Double.compare(this.peso, otra.peso);
    }   
    
    @Override
    public String toString() {
        return origen + " -- " + destino + " (" + peso + ")";
    }  
}
