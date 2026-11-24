/**
 * Hilo que calcula e imprime los primeros N términos de Fibonacci.
 * La sucesión empieza por 1 y 1: 1, 1, 2, 3, 5, 8, 13...
 */
class HiloFibonacci extends Thread {
    private final int n;

    // N llega por el constructor
    public HiloFibonacci(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        long anterior = 0;
        long actual = 1;
        for (int i = 1; i <= n; i++) {
            System.out.print(actual + (i < n ? ", " : ""));
            long siguiente = anterior + actual; // cada término es la suma de los dos anteriores
            anterior = actual;
            actual = siguiente;
        }
        System.out.println();
    }
}

/** Tarea 07 - Fibonacci. Uso: java Tarea07 [N]  (por defecto N = 10) */
public class Tarea07 {
    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        HiloFibonacci hilo = new HiloFibonacci(n);
        hilo.start();
        hilo.join(); // el main espera a que el hilo termine el cálculo
        System.out.println("Cálculo terminado.");
    }
}
