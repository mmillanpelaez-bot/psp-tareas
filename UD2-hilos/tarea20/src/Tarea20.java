import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/** Productor: pone productos en la cola. */
class Productor extends Thread {
    private final BlockingQueue<String> cola;

    public Productor(BlockingQueue<String> cola) {
        super("Productor");
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                String producto = "Producto-" + i;
                cola.put(producto); // si la cola está llena, espera aquí
                System.out.println("Puesto: " + producto);
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}

/** Consumidor: saca productos de la cola. */
class Consumidor extends Thread {
    private final BlockingQueue<String> cola;

    public Consumidor(BlockingQueue<String> cola) {
        super("Consumidor");
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                String producto = cola.take(); // si la cola está vacía, espera aquí
                System.out.println("Sacado: " + producto);
                Thread.sleep(800); // consume más despacio que lo que se produce
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}

/**
 * Tarea 20 - Productor-consumidor con una cola.
 * Ya no hay clase Almacen, ni synchronized, ni wait(), ni notifyAll():
 * la BlockingQueue (con capacidad 1, como el almacén) lo hace todo.
 */
public class Tarea20 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> cola = new ArrayBlockingQueue<>(1);

        Productor productor = new Productor(cola);
        Consumidor consumidor = new Consumidor(cola);
        productor.start();
        consumidor.start();

        productor.join();
        consumidor.join();
        System.out.println("Fin.");
    }
}
