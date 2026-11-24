import java.util.Random;

/**
 * Hilo que escribe 10 veces su nombre, iteración y prioridad.
 * Entre iteración e iteración se bloquea entre 1 y 10 segundos.
 */
class HiloPrioridad extends Thread {

    public HiloPrioridad(String nombre, int prioridad) {
        super(nombre);
        setPriority(prioridad);
    }

    @Override
    public void run() {
        Random azar = new Random();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Nombre del hilo: " + getName()
                    + ", Iteración: " + i
                    + ", Prioridad: " + getPriority());
            try {
                Thread.sleep(1000 + azar.nextInt(9001)); // entre 1 y 10 segundos
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

/** Tarea 11 - Prioridades: uno con prioridad mínima, dos medias y uno máxima. */
public class Tarea11 {
    public static void main(String[] args) throws InterruptedException {
        HiloPrioridad minimo = new HiloPrioridad("Hilo-MIN", Thread.MIN_PRIORITY);   // 1
        HiloPrioridad medio1 = new HiloPrioridad("Hilo-NORM-1", Thread.NORM_PRIORITY); // 5
        HiloPrioridad medio2 = new HiloPrioridad("Hilo-NORM-2", Thread.NORM_PRIORITY); // 5
        HiloPrioridad maximo = new HiloPrioridad("Hilo-MAX", Thread.MAX_PRIORITY);   // 10

        minimo.start();
        medio1.start();
        medio2.start();
        maximo.start();

        minimo.join();
        medio1.join();
        medio2.join();
        maximo.join();
        System.out.println("Han terminado todos.");
    }
}
