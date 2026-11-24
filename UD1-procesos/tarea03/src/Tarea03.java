import java.io.File;
import java.io.IOException;

/**
 * Tarea 03: ejecuta "ls" (Linux) o "dir" (Windows) y muestra el directorio de
 * trabajo y la propiedad user.dir en tres situaciones:
 *   1. Por defecto.
 *   2. Después de cambiar la propiedad user.dir a la carpeta home.
 *   3. Después de cambiar el directorio de trabajo del proceso al directorio temporal.
 *
 * Importante: el proceso hijo no muestra su salida por sí solo.
 * Hay que usar inheritIO() para que el listado salga en nuestra consola.
 */
public class Tarea03 {

    static boolean windows = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("===== 1. Valores por defecto =====");
        mostrarDirectorios();
        listar(null);

        System.out.println("\n===== 2. Cambiamos la propiedad user.dir a la carpeta home =====");
        System.setProperty("user.dir", System.getProperty("user.home"));
        mostrarDirectorios();
        listar(null);

        System.out.println("\n===== 3. Cambiamos el directorio de trabajo del proceso al directorio temporal =====");
        File temporal = new File(windows ? "C:/temp" : "/tmp");
        mostrarDirectorios();
        listar(temporal);
    }

    /** Muestra el directorio de trabajo que ve Java y el valor de la propiedad user.dir. */
    static void mostrarDirectorios() {
        System.out.println("Directorio de trabajo (File): " + new File("").getAbsolutePath());
        System.out.println("Propiedad user.dir:           " + System.getProperty("user.dir"));
    }

    /**
     * Ejecuta pwd + ls (o cd + dir en Windows) en el directorio indicado.
     * Si directorio es null, el proceso usa el directorio de trabajo por defecto.
     */
    static void listar(File directorio) throws IOException, InterruptedException {
        String[] cual = windows ? new String[] { "cmd", "/c", "cd" } : new String[] { "pwd" };
        String[] lista = windows ? new String[] { "cmd", "/c", "dir" } : new String[] { "ls" };

        System.out.println("El proceso se ejecuta en:");
        ejecutar(cual, directorio);
        System.out.println("Listado:");
        ejecutar(lista, directorio);
    }

    /** Lanza un comando mostrando su salida en nuestra consola. */
    static void ejecutar(String[] comando, File directorio) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(comando);
        pb.inheritIO();              // sin esto, el listado no se vería
        if (directorio != null) {
            pb.directory(directorio); // cambia el directorio de trabajo del proceso
        }
        pb.start().waitFor();
    }
}
