/**
 * Nivel 3: "instala" el curso cuando están listas meditacion.mp4 y mantras.mp3.
 * Implementa Runnable (Descarga hereda de Thread, así que usamos la forma contraria).
 * Solo espera a esas dos descargas; las demás siguen a lo suyo.
 */
public class Instalador implements Runnable {

    private final Thread meditacion;
    private final Thread mantras;

    /** Recibe los dos hilos de descarga que tiene que esperar. */
    public Instalador(Thread meditacion, Thread mantras) {
        this.meditacion = meditacion;
        this.mantras = mantras;
    }

    /** Espera con join() a las dos descargas y después simula la instalación. */
    @Override
    public void run() {
        try {
            meditacion.join();
            mantras.join();
            System.out.println("[Instalador] Meditación y mantras listos: instalando...");
            Thread.sleep(1000);
            System.out.println("[Instalador] Instalación terminada");
        } catch (InterruptedException e) {
            System.out.println("[Instalador] interrumpido");
        }
    }
}
