import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/** Un pedido de pizza: es una tarea (Runnable) que ejecuta un cocinero del pool. */
class Pedido implements Runnable {
    private final int numero;

    public Pedido(int numero) {
        this.numero = numero;
    }

    @Override
    public void run() {
        // El nombre del hilo es el del cocinero que lo está haciendo (pool-1-thread-N)
        String cocinero = Thread.currentThread().getName();
        System.out.println(cocinero + " empieza el pedido " + numero);
        try {
            Thread.sleep(1000 + new Random().nextInt(2001)); // entre 1 y 3 s
        } catch (InterruptedException e) {
            return;
        }
        System.out.println(cocinero + " termina el pedido " + numero);
    }
}

/** Tarea 22 - Pizzería: 3 cocineros (hilos del pool) y 10 pedidos (tareas). */
public class Tarea22 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService cocineros = Executors.newFixedThreadPool(3); // nunca más de 3 a la vez

        for (int i = 1; i <= 10; i++) {
            cocineros.execute(new Pedido(i)); // los que no caben esperan en la cola del pool
        }

        cocineros.shutdown();
        cocineros.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("Todos los pedidos están listos.");
    }
}
