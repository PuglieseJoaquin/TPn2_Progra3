package vista;

import javax.swing.JFrame;

public class GestorPantallas implements GestorInterfaz {

    private PantallaInicio pantallaInicio;
    
    @Override
    public void crearPantallaInicio() {
        pantallaInicio = new PantallaInicio(this);
        mostrarPantalla(pantallaInicio);
    }
    
    public JFrame mostrarPantalla(JFrame pantalla) {
        pantalla.setVisible(true);
        ocultarPantallas(pantalla);
        return pantalla;
    }   
    
    public void ocultarPantallas(JFrame pantallaActual) {
        JFrame[] todasLasPantallas = {pantallaInicio};
            
        for (JFrame pantalla : todasLasPantallas) {
            if(pantalla != pantallaActual && pantalla != null) {
                pantalla.setVisible(false);
            }
        }
    }
}