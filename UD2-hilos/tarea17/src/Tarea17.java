import java.util.Scanner;

/** Contador total compartido por todos los hilos. */
class ContadorTotal {
    private int total = 0;

    /**
     * Suma 1 al total. synchronized evita que dos hilos se pisen.
     * Si se quita, el total sale menor que la suma de lo que cuenta cada hilo.
     */
    public synchronized void incrementar() {
        total++;
    }

    public synchronized int getTotal() {
        return total;
    }
}

/** Hilo que cuenta cuántas veces aparece UNA consonante en el texto. */
class HiloConsonante extends Thread {
    private final char consonante;
    private final String texto;
    private final ContadorTotal contadorTotal;
    private int cuenta = 0; // lo que ha contado este hilo

    public HiloConsonante(char consonante, String texto, ContadorTotal contadorTotal) {
        super("Hilo-" + consonante);
        this.consonante = consonante;
        this.texto = texto;
        this.contadorTotal = contadorTotal;
    }

    @Override
    public void run() {
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == consonante) {
                cuenta++;                      // su cuenta propia
                contadorTotal.incrementar();   // el total compartido
            }
        }
    }

    public char getConsonante() {
        return consonante;
    }

    public int getCuenta() {
        return cuenta;
    }
}

/** Tarea 17 - Contador de caracteres: un hilo por consonante y un total compartido. */
public class Tarea17 {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        // Pedimos 5 consonantes (en minúscula para comparar bien)
        char[] consonantes = new char[5];
        for (int i = 0; i < 5; i++) {
            consonantes[i] = pedirConsonante(sc, i + 1);
        }

        System.out.print("Escribe un texto: ");
        String texto = sc.nextLine().toLowerCase();
        texto = texto.repeat(2000); // lo repetimos para que se note el problema

        ContadorTotal total = new ContadorTotal();
        HiloConsonante[] hilos = new HiloConsonante[5];
        for (int i = 0; i < 5; i++) {
            hilos[i] = new HiloConsonante(consonantes[i], texto, total);
            hilos[i].start();
        }
        for (HiloConsonante h : hilos) {
            h.join();
        }

        // Mostramos lo de cada hilo y el total
        int suma = 0;
        for (HiloConsonante h : hilos) {
            System.out.println("Consonante '" + h.getConsonante() + "': " + h.getCuenta());
            suma += h.getCuenta();
        }
        System.out.println("Total compartido: " + total.getTotal());
        System.out.println("Suma de lo que contó cada hilo: " + suma);
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
