/**
 * Experimento del README: start() y join() DENTRO del mismo bucle.
 * Cada descarga se espera antes de arrancar la siguiente, así que
 * se hacen una detrás de otra y no se gana nada.
 */
public class ExperimentoSerie {

    public static void main(String[] args) throws InterruptedException {
        String[] archivos = { "cuarzos.png", "meditacion.mp4", "mantras.mp3", "horoscopo.pdf" };
        long inicio = System.currentTimeMillis();
        long acumuladoSerie = 0;

        for (String archivo : archivos) {
            Descarga d = new Descarga(archivo);
            d.start();
            d.join(); // <- aquí está el problema: esperamos antes de lanzar la siguiente
            acumuladoSerie += d.getTiempo();
        }

        long tiempoReal = System.currentTimeMillis() - inicio;
        System.out.println("Tiempo real: " + tiempoReal + " ms");
        System.out.println("Suma de las descargas: " + acumuladoSerie + " ms");
    }
}
