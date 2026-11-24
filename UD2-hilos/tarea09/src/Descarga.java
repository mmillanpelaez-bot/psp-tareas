import java.util.Random;

/**
 * Simula la descarga de un archivo en su propio hilo (hereda de Thread).
 * La descarga se hace en 10 bloques del 10 %; cada bloque tarda un tiempo
 * aleatorio (entre 100 y 500 ms) que se elige al crear el objeto.
 */
public class Descarga extends Thread {

    // Multiplica el tiempo de cada bloque (con 1 no cambia nada)
    private static final int AJUSTE_BLOQUE = 1;

    private final String archivo;
    private final int msPorBloque;
    private long tiempo; // lo que ha tardado en total, en ms

    /**
     * Crea la descarga de un archivo. El hilo se llama "Descarga-archivo" y
     * el tiempo de cada bloque se elige al azar entre 100 y 500 ms.
     */
    public Descarga(String archivo) {
        super("Descarga-" + archivo);
        this.archivo = archivo;
        this.msPorBloque = (100 + new Random().nextInt(401)) * AJUSTE_BLOQUE;
    }

    /**
     * Hace 10 iteraciones: duerme el tiempo de un bloque e imprime el
     * progreso. Al terminar imprime cuánto ha tardado y lo guarda en tiempo.
     */
    @Override
    public void run() {
        long inicio = System.currentTimeMillis();
        try {
            for (int i = 1; i <= 10; i++) {
                Thread.sleep(msPorBloque);
                System.out.println("[" + archivo + "] " + (i * 10) + "%");
            }
        } catch (InterruptedException e) {
            System.out.println("[" + archivo + "] interrumpida");
            return;
        }
        tiempo = System.currentTimeMillis() - inicio;
        System.out.println("[" + archivo + "] completada en " + tiempo + " ms");
    }

    /** Devuelve lo que ha tardado la descarga en ms (0 si aún no ha terminado). */
    public long getTiempo() {
        return tiempo;
    }

    /** Devuelve el nombre del archivo que se descarga. */
    public String getArchivo() {
        return archivo;
    }
}
