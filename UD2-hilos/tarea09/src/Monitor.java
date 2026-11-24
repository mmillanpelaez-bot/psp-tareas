/**
 * Nivel 2: muestra cada 500 ms cuántas descargas siguen en curso.
 * Implementa Runnable (Descarga hereda de Thread, así que usamos la forma contraria).
 * Termina solo cuando no queda ninguna descarga viva.
 */
public class Monitor implements Runnable {

    private final Descarga[] descargas;

    /** Recibe las descargas que tiene que vigilar. */
    public Monitor(Descarga[] descargas) {
        this.descargas = descargas;
    }

    /**
     * Cuenta las descargas vivas con isAlive(). Si hay alguna lo dice y espera
     * 500 ms; si no queda ninguna avisa y termina.
     */
    @Override
    public void run() {
        try {
            while (true) {
                int vivas = 0;
                for (Descarga d : descargas) {
                    if (d.isAlive()) {
                        vivas++;
                    }
                }
                if (vivas == 0) {
                    System.out.println("[Monitor] No queda ninguna descarga en curso");
                    break;
                }
                System.out.println("[Monitor] Descargas en curso: " + vivas);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("[Monitor] interrumpido");
        }
    }
}
