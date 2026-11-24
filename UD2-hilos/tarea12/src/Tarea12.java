/** Contador compartido SIN proteger: es el que provoca el problema. */
class Contador {
    private int valor = 0;

    /** Suma 1. Como valor++ son tres pasos (leer, sumar, escribir), los hilos se pisan. */
    public void incrementar() {
        valor++;
    }

    public int getValor() {
        return valor;
    }
}

/** Hilo que incrementa el contador compartido 100.000 veces. */
class Hilo extends Thread {
    private final Contador contador;

    public Hilo(Contador contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            contador.incrementar();
        }
    }
}

/** Tarea 12 - Contador compartido: 5 hilos x 100.000 = 500.000 esperado. */
public class Tarea12 {
    public static void main(String[] args) throws InterruptedException {
        Contador contador = new Contador(); // un único contador para todos

        Hilo[] hilos = new Hilo[5];
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Hilo(contador);
            hilos[i].start();
        }
        for (Hilo h : hilos) {
            h.join();
        }

        System.out.println("Valor final: " + contador.getValor() + " (esperado: 500000)");
    }
}
