package principal;

import javax.swing.UIManager;

import vista.InterfazGestorPantalla;
import vista.GestorPantallas;

public class Principal {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            System.out.println("No se pudo cargar el LookAndFeel: " + e);
        }

        InterfazGestorPantalla gestorInterfaz = new GestorPantallas();
        gestorInterfaz.crearPantallaInicio();
    }
}