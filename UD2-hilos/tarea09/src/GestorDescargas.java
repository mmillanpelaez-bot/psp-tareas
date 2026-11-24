/**
 * Gestor de descargas: crea una descarga por archivo, las lanza todas a la
 * vez (cada una en su hilo), espera a que terminen y muestra un informe.
 *
 * Los archivos se pueden pasar por la línea de comandos. Si no se pasa
 * ninguno se usan los 4 del curso.
 */
public class GestorDescargas {

    public static void main(String[] args) throws InterruptedException {
        long inicio = System.currentTimeMillis();

        // Nivel 2: archivos por línea de comandos (o los 4 por defecto)
        String[] archivos = args;
        if (archivos.length == 0) {
            archivos = new String[] { "cuarzos.png", "meditacion.mp4", "mantras.mp3", "horoscopo.pdf" };
        }

        // Creamos las descargas
        Descarga[] descargas = new Descarga[archivos.length];
        for (int i = 0; i < archivos.length; i++) {
            descargas[i] = new Descarga(archivos[i]);
        }

        // Primero las arrancamos TODAS...
        for (Descarga d : descargas) {
            d.start();
        }

        // Nivel 2: el monitor (Runnable) arranca cuando ya hay descargas vivas
        Thread monitor = new Thread(new Monitor(descargas), "Monitor");
        monitor.start();

        // Nivel 3: el instalador solo se crea si están las dos descargas que necesita
        Descarga meditacion = buscar(descargas, "meditacion.mp4");
        Descarga mantras = buscar(descargas, "mantras.mp3");
        Thread instalador = null;
        if (meditacion != null && mantras != null) {
            instalador = new Thread(new Instalador(meditacion, mantras), "Instalador");
            instalador.start();
        }

        // Nivel 3: espera con límite. Como mucho 3 s por meditacion.mp4 y NO la cancelamos
        if (meditacion != null) {
            meditacion.join(3000);
            if (meditacion.isAlive()) {
                System.out.println("[Main] meditacion.mp4 sigue en segundo plano");
            }
        }

        // ...y después esperamos a todas
        for (Descarga d : descargas) {
            d.join();
        }
        long tiempoReal = System.currentTimeMillis() - inicio;

        // Esperamos también al monitor y al instalador para que no escriban después del informe
        monitor.join();
        if (instalador != null) {
            instalador.join();
        }

        // Lo que habría tardado una detrás de otra: la suma de los tiempos
        long acumuladoSerie = 0;
        for (Descarga d : descargas) {
            acumuladoSerie += d.getTiempo();
        }

        System.out.println("Todas las descargas han terminado.");
        System.out.println("Tiempo real: " + tiempoReal + " ms");
        System.out.println("Si se hubieran descargado una detrás de otra: " + acumuladoSerie + " ms");
    }

    /** Busca una descarga por el nombre de su archivo. Devuelve null si no está. */
    private static Descarga buscar(Descarga[] descargas, String archivo) {
        for (Descarga d : descargas) {
            if (d.getArchivo().equals(archivo)) {
                return d;
            }
        }
        return null;
    }
}
