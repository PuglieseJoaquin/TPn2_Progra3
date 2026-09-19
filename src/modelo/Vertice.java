package modelo;

import java.util.Objects;

public class Vertice {

    private final String nombre;

    public Vertice(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertice otro = (Vertice) obj;
		return Objects.equals(nombre, otro.nombre);
	}

    @Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

    @Override
    public String toString() {
        return nombre;
    }
}
