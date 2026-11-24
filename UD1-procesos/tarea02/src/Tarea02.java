import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
* Tarea 02 -
* Crea un programa en Java que permita al usuario, desde la línea de comandos, abrir un archivo existente o crear uno nuevo utilizando un editor de texto sencillo disponible en el sistema (por ejemplo: gnome-text-editor, gedit, notepad, etc.).
* El usuario deberá introducir por teclado el nombre del archivo o su ruta completa (incluyendo la extensión).
* Si el archivo no existe, el editor deberá permitir su creación.
*/

public class Tarea02 {

    static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Introduce el nombre o ruta del archivo: ");
        String rutaArchivo = teclado.nextLine().trim();

        if (rutaArchivo.isEmpty()) {
            System.out.println("No has introducido ningun nombre de archivo.");
            return;
        }

        String sistemaOperativo = System.getProperty("os.name").toLowerCase();
        String editor;

        if (sistemaOperativo.contains("win")) {
            editor = "notepad";
        } else if (sistemaOperativo.contains("mac")) {
            editor = "TextEditor";
        } else {
            editor = "gnome-text-editor";
        }

        try {
            System.out.println("Abriendo " + editor + " con el archivo: " + rutaArchivo);

            ProcessBuilder pb = new ProcessBuilder(editor, rutaArchivo);

            Process proceso = pb.start();

            int codigoSalida = proceso.waitFor();
            System.out.println("El editor se cerró con el código de salida " + codigoSalida);

        } catch (IOException e) {
            System.err.println("Error al ejecutar el editor: " + e.getMessage());
            System.err.println("Comprueba que el programa " + editor + " está instalado.");
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            teclado.close();
        }
    }
}
