package vista;

public enum OpcionMapa {
    CAPITALES("/capitales.json"),
    AMERICA("/paisesAmerica.json"),
    MUNDO("/paisesMundo.json");

    private final String path;

    OpcionMapa(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
