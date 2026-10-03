package presenter;

import datos.JsonLoader;
import modelo.CapitalesArgentinas;
import vista.GestorPantallas;
import vista.PantallaCargaDesdeJSON;

import java.io.IOException;
import java.util.List;

public class CargaDesdeJSONPresentador {
	private GestorPantallas gestorPantallas;
    private PantallaCargaDesdeJSON vista;
    private List<CapitalesArgentinas> capitales;

    public CargaDesdeJSONPresentador(PantallaCargaDesdeJSON vista, GestorPantallas gestorPantallas) {
    	this.gestorPantallas=gestorPantallas;
        this.vista = vista;
        inicializar();
    }

    private void inicializar() {
        try {
            capitales = JsonLoader.cargarCapitales("/capitales.json");
            vista.cargarCapitales(capitales);
        } catch (Exception e) {
            vista.mostrarError("Error al leer JSON: " + e.getMessage());
        }
    }

    // 👉 Este método se llama cuando el usuario aprieta "Agregar conexión"
    public void manejarAgregarConexion(String origen, String destino, int peso) {
    		CapitalesArgentinas capOrigen = buscarCapital(origen);
    		CapitalesArgentinas capDestino = buscarCapital(destino);

        if (capOrigen != null && capDestino != null) {
            // Acá recién se crean los vértices y la arista
            vista.dibujarConexion(capOrigen, capDestino, peso);
        } else {
            vista.mostrarError("No se encontraron las capitales seleccionadas.");
        }
    }

    private CapitalesArgentinas buscarCapital(String nombre) {
        for (CapitalesArgentinas c : capitales) {
            if (c.getNombre().equals(nombre)) {
                return c;
            }
        }
        return null;
    }
    public void manejarClickVolverAlMenu() {
		gestorPantallas.crearPantallaInicio();
	}
}

