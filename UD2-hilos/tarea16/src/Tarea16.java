/** La caja con el capital compartido por los dos hilos. */
class Caja {
    public static double capital = 1000.0;

    /**
     * Suma (o resta si es negativa) una cantidad al capital.
     * synchronized: mientras un hilo hace los tres pasos (leer, esperar, escribir)
     * el otro no puede entrar. Si se quita synchronized, los dos leen el mismo
     * saldo y uno pisa el cambio del otro, y el resultado sale mal.
     */
    public static synchronized void mover(double cantidad) throws InterruptedException {
        double saldo = Caja.capital;   // 1. guarda el saldo
        Thread.sleep(1);               // 2. espera 1 ms
        Caja.capital = saldo + cantidad; // 3. escribe el nuevo saldo
    }
}

/** 5.000 ventas de 10 €. */
class Ingresos extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5000; i++) {
                Caja.mover(10);
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}

/** 3.000 pagos de 10 €. */
class Extracciones extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 3000; i++) {
                Caja.mover(-10);
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}

/** Tarea 16 - Flujo de caja. Esperado: 1000 + 50.000 - 30.000 = 21.000 €. */
public class Tarea16 {
    public static void main(String[] args) throws InterruptedException {
        Ingresos ingresos = new Ingresos();
        Extracciones extracciones = new Extracciones();

        ingresos.start();
        extracciones.start();
        ingresos.join();
        extracciones.join();

        System.out.println("Saldo final: " + Caja.capital + " € (esperado: 21000.0)");
    }
}
