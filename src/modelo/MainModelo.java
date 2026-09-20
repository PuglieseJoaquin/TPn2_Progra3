package modelo;

import java.util.List;

public class MainModelo {
	
	public static void main(String[] args) {
	        // 1) Probar equals/hashCode de Vertice
	        Vertice v1 = new Vertice("Buenos Aires");
	        Vertice v2 = new Vertice("Buenos Aires");
	        System.out.println("equals " + v1.equals(v2));               
	        System.out.println("hashCode " + (v1.hashCode() == v2.hashCode())); 

	        // 2) Armar un grafo chiquito
	        Vertice bsAs = new Vertice("Buenos Aires");
	        Vertice cordoba = new Vertice("Cordoba");
	        Vertice santaFe = new Vertice("Santa Fe");
	        Vertice jujuy = new Vertice("Jujuy");
	        Vertice Salta = new Vertice("Salta");

	        Grafo grafo = new Grafo();
	        grafo.agregarVertice(bsAs);
	        grafo.agregarVertice(cordoba);
	        grafo.agregarVertice(santaFe);
	        grafo.agregarVertice(Salta);
	        grafo.agregarVertice(jujuy);

	        grafo.agregarArista(bsAs, cordoba, 11);
	        grafo.agregarArista(bsAs, santaFe, 12);
	        grafo.agregarArista(Salta, jujuy,23);
	        

	        
	        System.out.println("Vértices: " + grafo.getVertices());

	       
	        List<Arista> aristasBsAs = grafo.getAristasDe(bsAs);
	        System.out.println("Aristas de Buenos Aires: " + aristasBsAs);

	        
	        Arista primeraArista = aristasBsAs.get(0);
	        System.out.println("Opuesto desde BsAs: " + primeraArista.getOpuesto(bsAs));

	        // 6) Probar que la lista devuelta es no modificable
	        aristasBsAs.add(new Arista(bsAs, jujuy, 2));
	        System.out.println("Aristas de BsAs después de modificar la copia (2): " + grafo.getAristasDe(bsAs).size());

	        //validaciones de agregarArista
	        try {
	            grafo.agregarArista(bsAs, bsAs, 1.0); 
	            System.out.println("debe lanzar excepción");
	        } catch (IllegalArgumentException e) {
	            System.out.println( e.getMessage());
	        }

	        try {
	            grafo.agregarArista(bsAs, cordoba, 0.9); 
	            System.out.println("debe lanzar excepción");
	        } catch (IllegalArgumentException e) {
	            System.out.println("arista duplicada rechazada " + e.getMessage());
	        }
	           
	        System.out.println("Todas las aristas: " + grafo.getTodasLasAristas());
	        
	        // 7) Probar Kruskal 
	        Kruskal kruskal = new Kruskal();

	        // El grafo actual no es conexo (Salta-Jujuy quedan aparte), entonces lanza excepcion
	        try {
	            kruskal.arbolGeneradorMinimo(grafo);
	            System.out.println("excepción");
	        } catch (IllegalArgumentException e) {
	            System.out.println("grafo no conexo rechazado: " + e.getMessage());
	        }

	        // Conectamos el grafo agregando aristas
	        grafo.agregarArista(cordoba, santaFe, 7);
	        grafo.agregarArista(cordoba, Salta, 15);
	        grafo.agregarArista(santaFe, Salta, 20);
	        grafo.agregarArista(cordoba, jujuy, 18);

	        List<Arista> agm = kruskal.arbolGeneradorMinimo(grafo);
	        System.out.println("Arbol generador minimo:");
	        double pesoTotal = 0;
	        for (Arista a : agm) {
	            System.out.println("  " + a);
	            pesoTotal += a.getPeso();
	        }
	        System.out.println("Cantidad de aristas (debe ser " + (grafo.cantidadVertices() - 1) + "): " + agm.size()); // Aristas = n - 1
	        System.out.println("Peso total: " + pesoTotal);
	
	        // 8) Probar Dividir Arbol
	        //DivisorDeArbol divisor = new DivisorDeArbol();
	}
}
