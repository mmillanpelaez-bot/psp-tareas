import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lanza el comando "factor" de Linux con el número que le pasan.
 * Según el nivel elegido, la salida se trata de una forma distinta:
 *   Nivel 1: se muestra tal cual en la consola.
 *   Nivel 2: se leen salida y error por separado y se etiquetan [OK] / [ERROR].
 *   Nivel 3: se guardan en factor_output.log y factor_error.log.
 *   Nivel 4: como el nivel 1, y además dice si el número es primo.
 */
public class Lanzador {

    private final int nivel;

    /** Guarda el nivel con el que se va a trabajar (1, 2, 3 o 4). */
    public Lanzador(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Ejecuta factor con el número según el nivel elegido.
     * Devuelve el código de salida del proceso (0 = bien, distinto de 0 = error).
     */
    public int lanzar(String numero) throws IOException, InterruptedException {
        switch (nivel) {
            case 2:
                return nivel2(numero);
            case 3:
                return nivel3(numero);
            case 4:
                return nivel4(numero);
            default:
                return nivel1(numero);
        }
    }

    /**
     * Nivel 1: la salida del proceso va directamente a la consola (inheritIO).
     * Devuelve el código de salida de factor.
     */
    private int nivel1(String numero) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("factor", numero);
        pb.inheritIO();
        Process p = pb.start();
        return p.waitFor();
    }

    /**
     * Nivel 2: lee la salida normal y la de error por separado.
     * Cada línea se muestra con [OK] o [ERROR] delante.
     * Devuelve el código de salida de factor.
     */
    private int nivel2(String numero) throws IOException, InterruptedException {
        Process p = new ProcessBuilder("factor", numero).start();

        BufferedReader salida = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = salida.readLine()) != null) {
            System.out.println("[OK] " + linea);
        }

        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((linea = error.readLine()) != null) {
            System.out.println("[ERROR] " + linea);
        }

        return p.waitFor();
    }

    /**
     * Nivel 3: redirige la salida a factor_output.log y el error a
     * factor_error.log. Se añade al final de cada fichero (appendTo), sin borrar
     * lo que ya había. En consola no se muestra nada de factor.
     * Devuelve el código de salida de factor.
     */
    private int nivel3(String numero) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("factor", numero);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(new File("factor_output.log")));
        pb.redirectError(ProcessBuilder.Redirect.appendTo(new File("factor_error.log")));
        Process p = pb.start();
        return p.waitFor();
    }

    /**
     * Nivel 4: muestra la salida de factor y dice si el número es primo.
     * Un número es primo si factor devuelve un solo factor (ej. "17: 17").
     * Si factor falla (código distinto de 0) no se dice nada sobre primos.
     * Devuelve el código de salida de factor.
     */
    private int nivel4(String numero) throws IOException, InterruptedException {
        Process p = new ProcessBuilder("factor", numero).start();

        BufferedReader salida = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        String resultado = null; // guardamos la línea de factor para analizarla
        while ((linea = salida.readLine()) != null) {
            System.out.println(linea);
            resultado = linea;
        }

        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((linea = error.readLine()) != null) {
            System.out.println(linea);
        }

        int codigo = p.waitFor();

        if (codigo == 0 && resultado != null) {
            // "360: 2 2 2 3 3 5" -> nos quedamos con lo que hay después de los ":"
            String factores = resultado.substring(resultado.indexOf(":") + 1).trim();
            // Si solo hay un factor (no hay espacios), el número es primo
            if (!factores.isEmpty() && !factores.contains(" ")) {
                System.out.println("¡" + numero + " es primo!");
            } else {
                System.out.println(numero + " no es primo");
            }
        }
        return codigo;
    }
}
