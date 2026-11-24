/** Contador compartido PROTEGIDO con synchronized. */
class Contador {
    private int valor = 0;

    /**
     * synchronized: solo un hilo a la vez puede ejecutar este método sobre el
     * mismo objeto. Así valor++ nunca se mezcla con el de otro hilo.
     */
    public synchronized void incrementar() {
        valor++;
    }

    public synchronized int getValor() {
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

/** Tarea 13 - Contador compartido sincronizado: siempre debe dar 500.000. */
public class Tarea13 {
    public static void main(String[] args) throws InterruptedException {
        Contador contador = new Contador();

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
