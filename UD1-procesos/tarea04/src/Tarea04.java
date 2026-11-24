import java.io.IOException;
import java.util.Scanner;

/**
 * Tarea 04: pide comandos en bucle, los ejecuta y muestra el código de salida.
 * Termina cuando el usuario escribe "salir" (código de salida del programa = 0).
 *
 * Ejemplos para probar: ls, ls -l, ls /carpeta_que_no_existe, gnome-text-editor, comandoinventado
 */
public class Tarea04 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Comando (o 'salir'): ");
            String linea = sc.nextLine().trim();

            if (linea.equalsIgnoreCase("salir")) {
                System.out.println("Fin del programa.");
                System.exit(0);
            }
            if (linea.isEmpty()) {
                continue; // si no escribe nada, volvemos a preguntar
            }

            // Separamos el comando y sus parámetros por los espacios
            String[] partes = linea.split("\\s+");

            try {
                ProcessBuilder pb = new ProcessBuilder(partes);
                pb.inheritIO();
                Process p = pb.start();
                int codigo = p.waitFor();
                System.out.println("Programa: " + partes[0] + " | Código de finalización: " + codigo);
            } catch (IOException e) {
                // El comando no existe: ni siquiera se llega a crear el proceso
                System.out.println("Programa: " + partes[0] + " | No se pudo lanzar: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Interrumpido.");
            }
        }
    }
}
