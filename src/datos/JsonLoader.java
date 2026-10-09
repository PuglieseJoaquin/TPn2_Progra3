package datos;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import modelo.CiudadSeleccionada;

public class JsonLoader {

    public static List<CiudadSeleccionada> cargarCiudades(String ruta) {
        Gson gson = new Gson();
        try (Reader reader = new InputStreamReader(
                JsonLoader.class.getResourceAsStream(ruta))) {
            CapitalesWrapper wrapper = gson.fromJson(reader, CapitalesWrapper.class);
            return wrapper.getVertices();
        } catch (Exception e) {
            throw new RuntimeException("Error al leer JSON: " + e.getMessage(), e);
        }
    }

    private static class CapitalesWrapper {
        private List<CiudadSeleccionada> vertices;

        public List<CiudadSeleccionada> getVertices() {
            return vertices;
        }
    }
}
