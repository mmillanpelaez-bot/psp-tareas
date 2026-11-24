import java.util.concurrent.atomic.AtomicInteger;

/** Contador con synchronized (la versión de la tarea 13). */
class ContadorSync {
    private int valor = 0;

    public synchronized void incrementar() {
        valor++;
    }

    public synchronized int getValor() {
        return valor;
    }
}

/** Contador con AtomicInteger: SIN synchronized. */
class ContadorAtomico {
    private final AtomicInteger valor = new AtomicInteger(0);

    /** incrementAndGet() suma 1 de forma atómica: nadie puede interrumpirlo a medias. */
    public void incrementar() {
        valor.incrementAndGet();
    }

    public int getValor() {
        return valor.get();
    }
}

class HiloSync extends Thread {
    private final ContadorSync contador;

    public HiloSync(ContadorSync contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            contador.incrementar();
        }
    }
}

class HiloAtomico extends Thread {
    private final ContadorAtomico contador;

    public HiloAtomico(ContadorAtomico contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            contador.incrementar();
        }
    }
}

/** Tarea 18 - Variables atómicas: compara el tiempo de synchronized y de AtomicInteger. */
public class Tarea18 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Ejecución | synchronized (ms) | AtomicInteger (ms) | valores");
        for (int vez = 1; vez <= 5; vez++) {
            // Versión con synchronized
            ContadorSync cs = new ContadorSync();
            HiloSync[] hs = new HiloSync[5];
            long inicio = System.nanoTime();
            for (int i = 0; i < 5; i++) {
                hs[i] = new HiloSync(cs);
                hs[i].start();
            }
            for (HiloSync h : hs) {
                h.join();
            }
            double msSync = (System.nanoTime() - inicio) / 1_000_000.0;

            // Versión con AtomicInteger
            ContadorAtomico ca = new ContadorAtomico();
            HiloAtomico[] ha = new HiloAtomico[5];
            inicio = System.nanoTime();
            for (int i = 0; i < 5; i++) {
                ha[i] = new HiloAtomico(ca);
                ha[i].start();
            }
            for (HiloAtomico h : ha) {
                h.join();
            }
            double msAtomic = (System.nanoTime() - inicio) / 1_000_000.0;

            System.out.printf("%9d | %17.2f | %18.2f | %d y %d%n",
                    vez, msSync, msAtomic, cs.getValor(), ca.getValor());
        }
    }
}
