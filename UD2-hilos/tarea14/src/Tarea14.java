class Cocinero extends Thread {

    public Cocinero() {
        super("Cocinero");
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("[Cocinero] El guiso está listo");
        } catch (InterruptedException e) {
            System.out.println("[Cocinero] ¡Me han cortado la luz!");
        }
    }
}

public class Tarea14 {
    public static void main(String[] args) throws InterruptedException {
        Cocinero cocinero = new Cocinero();

        System.out.println("(1) " + cocinero.getState());
        cocinero.start();
        Thread.sleep(200);
        System.out.println("(2) " + cocinero.getState());
        cocinero.interrupt();
        cocinero.join();
        System.out.println("(3) " + cocinero.getState());
    }
}
