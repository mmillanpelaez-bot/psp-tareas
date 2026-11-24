import java.util.Random;

/**
 * Profesor hecho heredando de Thread.
 * Su "nivel de cabreo" sube en 1 por cada trabajo copiado de una IA que descubre.
 */
class ProfesorThread extends Thread {
    private final int limite; // máximo de trabajos que va a revisar

    // El nombre del profesor es el nombre del hilo (super(nombre))
    public ProfesorThread(String nombre, int limite) {
        super(nombre);
        this.limite = limite;
    }

    @Override
    public void run() {
        Random azar = new Random();
        for (int nivel = 1; nivel <= limite; nivel++) {
            if (nivel == limite) {
                System.out.println("[" + getName() + "] Cabreo nivel: " + nivel + "... ¡He llegado a mi límite!");
            } else {
                System.out.println("[" + getName() + "] Cabreo nivel: " + nivel);
            }
            try {
                Thread.sleep(100 + azar.nextInt(200)); // pausa para que se mezclen las quejas
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

/**
 * Profesor hecho implementando Runnable.
 * Hace lo mismo, pero hay que meterlo en un Thread para ejecutarlo.
 */
class ProfesorRunnable implements Runnable {
    private final int limite;

    public ProfesorRunnable(int limite) {
        this.limite = limite;
    }

    @Override
    public void run() {
        // Un Runnable no tiene getName(): se lo preguntamos al hilo que lo está ejecutando
        String nombre = Thread.currentThread().getName();
        Random azar = new Random();
        for (int nivel = 1; nivel <= limite; nivel++) {
            if (nivel == limite) {
                System.out.println("[" + nombre + "] Cabreo nivel: " + nivel + "... ¡He llegado a mi límite!");
            } else {
                System.out.println("[" + nombre + "] Cabreo nivel: " + nivel);
            }
            try {
                Thread.sleep(100 + azar.nextInt(200));
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

/** Tarea 06 - Profesores cabreados: dos con Thread y dos con Runnable, sin esperar a que terminen. */
public class Tarea06 {
    public static void main(String[] args) {
        // Dos profesores heredando de Thread
        Thread diego = new ProfesorThread("Diego", 4);
        Thread manuel = new ProfesorThread("Manuel", 5);

        // Dos profesores con Runnable: el nombre se le da al Thread que lo envuelve
        Thread damian = new Thread(new ProfesorRunnable(3), "Damian");
        Thread araujo = new Thread(new ProfesorRunnable(5), "Araujo");

        diego.start();
        manuel.start();
        damian.start();
        araujo.start();

        // No hacemos join: el main termina sin esperar a nadie
        System.out.println("Programa principal terminado.");
    }
}
