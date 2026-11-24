/**
 * Hilo que escribe 8 veces su nombre y la iteración.
 * Si recibe otro hilo en el constructor, primero espera (join) a que ese termine.
 * Así se consigue un orden: el que no espera a nadie va primero.
 */
class HiloOrden extends Thread {
    private final Thread esperarA; // null si no tiene que esperar a nadie

    public HiloOrden(String nombre, Thread esperarA) {
        super(nombre);
        this.esperarA = esperarA;
    }

    @Override
    public void run() {
        try {
            if (esperarA != null) {
                esperarA.join(); // no empieza hasta que el otro acabe
            }
            for (int i = 1; i <= 8; i++) {
                System.out.println(getName() + " - iteración " + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}

/** Tarea 15 - Orden secuencial inverso: primero el 3, luego el 2 y por último el 1. */
public class Tarea15 {
    public static void main(String[] args) throws InterruptedException {
        // El 3 no espera a nadie, el 2 espera al 3 y el 1 espera al 2
        HiloOrden hilo3 = new HiloOrden("Hilo-3", null);
        HiloOrden hilo2 = new HiloOrden("Hilo-2", hilo3);
        HiloOrden hilo1 = new HiloOrden("Hilo-1", hilo2);

        // Se arrancan los tres a la vez (da igual el orden de start)
        hilo1.start();
        hilo2.start();
        hilo3.start();

        // El main espera a los tres
        hilo1.join();
        hilo2.join();
        hilo3.join();
        System.out.println("Han terminado los tres hilos.");
    }
}
