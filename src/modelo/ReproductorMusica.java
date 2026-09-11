package modelo;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class ReproductorMusica {
    private static Clip clip;

    public static void reproducirLoop(String rutaArchivo) {
        detener();
        clip = cargarYConfigurarClip(rutaArchivo, -20.0f);
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    public static void reproducirEfecto(String rutaArchivo) {
        Clip efectoClip = cargarYConfigurarClip(rutaArchivo, -10.0f);
        if (efectoClip != null) {
            efectoClip.start();
        }
    }

    public static void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    private static Clip cargarYConfigurarClip(String rutaArchivo, float volumen) {
        try {
            URL url = ReproductorMusica.class.getResource(rutaArchivo);
            if (url == null) {
                System.out.println("No se encontró el archivo de audio: " + rutaArchivo);
                return null;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            Clip nuevoClip = AudioSystem.getClip();
            nuevoClip.open(audioStream);
            
            FloatControl gainControl = (FloatControl) nuevoClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volumen);
            
            return nuevoClip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }
}