package vista;

public enum OpcionMapa {
    CAPITALES("/JSONs/capitales.json"),
    AMERICA("/JSONs/paisesAmerica.json"),
    MUNDO("/JSONs/paisesMundo.json");

    private final String path;

    OpcionMapa(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
