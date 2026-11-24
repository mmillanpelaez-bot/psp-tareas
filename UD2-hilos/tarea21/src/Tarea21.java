import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tarea: cuenta una consonante en el texto. Es un Runnable (una tarea),
 * no un hilo: el pool es quien la ejecuta en uno de sus hilos.
 */
class TareaConsonante implements Runnable {
    private final char consonante;
    private final String texto;
    private final AtomicInteger total; // compartido por todas las tareas
    private int cuenta = 0;

    public TareaConsonante(char consonante, String texto, AtomicInteger total) {
        this.consonante = consonante;
        this.texto = texto;
        this.total = total;
    }

    @Override
    public void run() {
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == consonante) {
                cuenta++;
                total.incrementAndGet(); // suma 1 sin synchronized
            }
        }
        System.out.println(Thread.currentThread().getName() + " contó '" + consonante + "'");
    }

    public char getConsonante() {
        return consonante;
    }

    public int getCuenta() {
        return cuenta;
    }
}

/** Tarea 21 - Contador de consonantes (tarea 17) con un pool de hilos. */
public class Tarea21 {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        char[] consonantes = new char[5];
        for (int i = 0; i < 5; i++) {
            consonantes[i] = pedirConsonante(sc, i + 1);
        }
        System.out.print("Escribe un texto: ");
        String texto = sc.nextLine().toLowerCase().repeat(2000);

        AtomicInteger total = new AtomicInteger(0);
        TareaConsonante[] tareas = new TareaConsonante[5];

        // Un pool de 3 hilos para 5 tareas: los hilos se reutilizan
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            tareas[i] = new TareaConsonante(consonantes[i], texto, total);
            pool.execute(tareas[i]);
        }

        pool.shutdown(); // "no admito más tareas": sin esto el programa no termina
        pool.awaitTermination(1, TimeUnit.MINUTES); // espera a que acaben (como un join)

        for (TareaConsonante t : tareas) {
            System.out.println("Consonante '" + t.getConsonante() + "': " + t.getCuenta());
        }
        System.out.println("Total: " + total.get());
    }

    /** Pide una consonante hasta que el usuario escribe una letra que no sea vocal. */
    static char pedirConsonante(Scanner sc, int numero) {
        while (true) {
            System.out.print("Consonante " + numero + ": ");
            String entrada = sc.nextLine().trim().toLowerCase();
            if (entrada.length() == 1) {
                char c = entrada.charAt(0);
                boolean esLetra = c >= 'a' && c <= 'z';
                boolean esVocal = "aeiou".indexOf(c) >= 0;
                if (esLetra && !esVocal) {
                    return c;
                }
            }
            System.out.println("Tiene que ser UNA consonante (una letra que no sea vocal).");
        }
    }
}
