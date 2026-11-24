import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * El parking: el Semaphore controla cuántos coches caben (acquire() ocupa una
 * plaza y espera si no queda ninguna; release() la libera).
 * El contador "libres" solo sirve para mostrar el número en pantalla; se toca
 * dentro de synchronized para que los mensajes salgan siempre en orden.
 */
class Parking {
    private final Semaphore plazas = new Semaphore(3); // 3 plazas
    private int libres = 3;

    /** Un coche entra. Si el parking está lleno, espera hasta que salga otro. */
    public void entrar(String coche) throws InterruptedException {
        // tryAcquire() intenta coger plaza sin esperar: devuelve false si no hay
        if (!plazas.tryAcquire()) {
            System.out.println(coche + " espera: parking lleno");
            plazas.acquire(); // ahora sí esperamos hasta que se libere una
        }
        synchronized (this) {
            libres--;
            System.out.println(coche + " ENTRA. Plazas libres: " + libres);
        }
    }

    /** Un coche sale y libera su plaza. */
    public void salir(String coche) {
        synchronized (this) {
            libres++;
            System.out.println(coche + " SALE.  Plazas libres: " + libres);
        }
        plazas.release();
    }
}

/** Un coche: entra, está aparcado un tiempo aleatorio y sale. */
class Coche extends Thread {
    private final Parking parking;

    public Coche(String nombre, Parking parking) {
        super(nombre);
        this.parking = parking;
    }

    @Override
    public void run() {
        try {
            parking.entrar(getName());
            Thread.sleep(1000 + new Random().nextInt(2001)); // aparcado entre 1 y 3 s
            parking.salir(getName());
        } catch (InterruptedException e) {
            return;
        }
    }
}

/** Tarea 19 - Parking con Semaphore: 3 plazas y 8 coches. */
public class Tarea19 {
    public static void main(String[] args) throws InterruptedException {
        Parking parking = new Parking();

        Coche[] coches = new Coche[8];
        for (int i = 0; i < coches.length; i++) {
            coches[i] = new Coche("Coche-" + (i + 1), parking);
            coches[i].start();
        }
        for (Coche c : coches) {
            c.join();
        }
        System.out.println("Todos los coches han pasado por el parking.");
    }
}
