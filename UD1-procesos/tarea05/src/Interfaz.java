import java.io.IOException;
import java.util.Scanner;

/**
 * Interfaz de consola: pide el nivel una vez y después pide números en bucle
 * hasta que el usuario escribe "salir". Cada número se lo pasa a Lanzador.
 */
public class Interfaz {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Pedimos el nivel hasta que sea 1, 2, 3 o 4
        int nivel = 0;
        while (nivel < 1 || nivel > 4) {
            System.out.println("¿Qué nivel quieres usar? (1, 2, 3 o 4):");
            System.out.print("> ");
            String texto = sc.nextLine().trim();
            if (texto.matches("[1-4]")) {
                nivel = Integer.parseInt(texto);
            }
        }

        Lanzador lanzador = new Lanzador(nivel);

        while (true) {
            System.out.println();
            System.out.println("Introduce un número (o 'salir' para terminar):");
            System.out.print("> ");
            String entrada = sc.nextLine().trim();

            if (entrada.equalsIgnoreCase("salir")) {
                System.out.println("Saliendo del programa");
                break;
            }

            try {
                int codigo = lanzador.lanzar(entrada);
                System.out.println("Operación completada. Código de salida: " + codigo);
            } catch (IOException e) {
                // Pasa, por ejemplo, si "factor" no existe en este sistema
                System.out.println("No se pudo ejecutar factor: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("El proceso fue interrumpido.");
            }
        }
    }
}
