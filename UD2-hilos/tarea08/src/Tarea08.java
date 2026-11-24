import java.util.Random;

/**
 * Hilo que escribe diez veces el número de iteración y su nombre.
 * Después de escribir se bloquea (sleep) entre 500 y 2000 ms.
 */
class HiloDurmiente extends Thread {

    public HiloDurmiente(String nombre) {
        super(nombre);
    }

    @Override
    public void run() {
        Random azar = new Random();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Iteración " + i + " - " + getName());
            try {
                Thread.sleep(500 + azar.nextInt(1501)); // entre 500 y 2000 ms
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

/**
 * Tarea 08 - Cuatro hilos que duermen.
 * Uso normal:  java Tarea08          (start() de todos y después join() de todos)
 * Experimento: java Tarea08 serie    (start() y join() dentro del mismo bucle)
 */
public class Tarea08 {
    public static void main(String[] args) throws InterruptedException {
        boolean serie = args.length > 0 && args[0].equals("serie");
        long inicio = System.currentTimeMillis();

        HiloDurmiente[] hilos = new HiloDurmiente[4];
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new HiloDurmiente("Hilo-" + (i + 1));
        }

        if (serie) {
            // MAL: esperamos a cada hilo antes de arrancar el siguiente
            for (HiloDurmiente h : hilos) {
                h.start();
                h.join();
            }
        } else {
            // BIEN: primero arrancamos todos...
            for (HiloDurmiente h : hilos) {
                h.start();
            }
            // ...y después esperamos a cada uno
            for (HiloDurmiente h : hilos) {
                h.join();
            }
        }

        long segundos = (System.currentTimeMillis() - inicio) / 1000;
        System.out.println("Han terminado todos");
        System.out.println("Tiempo total: " + segundos + " segundos");
    }
}
