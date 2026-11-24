/**
 * Hilo que suma los números del 1 al 1923 que cumplan una condición.
 * Una sola clase para los tres casos: el tipo de suma llega por el constructor.
 */
class HiloSuma extends Thread {

    public static final int PARES = 1;
    public static final int IMPARES = 2;
    public static final int TERMINAN_EN_3_O_4 = 3;

    private final int tipo;
    private long resultado; // aquí guarda su suma

    public HiloSuma(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public void run() {
        for (int n = 1; n <= 1923; n++) {
            if (cumple(n)) {
                resultado += n;
            }
        }
    }

    /** Dice si el número n entra en la suma de este hilo. */
    private boolean cumple(int n) {
        if (tipo == PARES) {
            return n % 2 == 0;
        } else if (tipo == IMPARES) {
            return n % 2 != 0;
        } else {
            int ultima = n % 10; // última cifra
            return ultima == 3 || ultima == 4;
        }
    }

    public long getResultado() {
        return resultado;
    }
}

/** Tarea 10 - Tres sumas en paralelo. */
public class Tarea10 {
    public static void main(String[] args) throws InterruptedException {
        HiloSuma pares = new HiloSuma(HiloSuma.PARES);
        HiloSuma impares = new HiloSuma(HiloSuma.IMPARES);
        HiloSuma tresCuatro = new HiloSuma(HiloSuma.TERMINAN_EN_3_O_4);

        pares.start();
        impares.start();
        tresCuatro.start();

        // Esperamos a los tres antes de leer los resultados
        pares.join();
        impares.join();
        tresCuatro.join();

        System.out.println("Suma de pares... " + pares.getResultado());
        System.out.println("Suma de impares... " + impares.getResultado());
        System.out.println("Suma de terminados en 3 o en 4... " + tresCuatro.getResultado());
    }
}
