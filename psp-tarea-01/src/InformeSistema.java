import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeMap;

public class InformeSistema {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        // Constante para pasar de bytes a MiB
        final long MIB = 1024 * 1024;

        // 1. PROCESADORES
        System.out.println("PROCESADORES");
        System.out.println("==================================================");
        System.out.println("Disponibles JVM: " + runtime.availableProcessors());
        System.out.println("(son hilos lógicos: con SMT no coinciden con los núcleos físicos)");

        // 2. MEMORIA ANTES DE RESERVAR
        System.out.println("\nMEMORIA · ANTES");
        System.out.println("==================================================");

        long totalAntes = runtime.totalMemory() / MIB;
        long libreAntes = runtime.freeMemory() / MIB;
        long maxima = runtime.maxMemory() / MIB;
        long enUsoAntes = totalAntes - libreAntes;
        long porcentajeAntes = (totalAntes > 0) ? (enUsoAntes * 100) / totalAntes : 0;

        System.out.println("Total reservada: " + totalAntes + " MiB");
        System.out.println("Libre: " + libreAntes + " MiB");
        System.out.println("En uso: " + enUsoAntes + " MiB (" + porcentajeAntes + " % de la total)");
        System.out.println("Máxima (-Xmx): " + maxima + " MiB");

        // 3. RESERVA DE MEMORIA (64 MiB)
        // Guardamos el array en una variable para evitar que el Garbage Collector lo limpie antes de medir
        long[] reservado = new long[8 * 1024 * 1024];

        // 4. MEMORIA DESPUÉS DE RESERVAR
        System.out.println("\nMEMORIA · DESPUÉS DE RESERVAR 64 MIB");
        System.out.println("==================================================");

        long totalDespues = runtime.totalMemory() / MIB;
        long libreDespues = runtime.freeMemory() / MIB;
        long enUsoDespues = totalDespues - libreDespues;
        long porcentajeDespues = (totalDespues > 0) ? (enUsoDespues * 100) / totalDespues : 0;
        long incremento = enUsoDespues - enUsoAntes;

        System.out.println("Total reservada: " + totalDespues + " MiB");
        System.out.println("Libre: " + libreDespues + " MiB");
        System.out.println("En uso: " + enUsoDespues + " MiB (" + porcentajeDespues + "% de la total)");
        System.out.println("Máxima (-Xmx): " + maxima + " MiB");
        System.out.println("==================================================");
        System.out.println("Incremento en uso: " + incremento + " MiB");
        // Imprimimos la posición 0 para asegurarnos de que la variable sigue activa
        System.out.println("(el array sigue en memoria: reservado [0] = " + reservado[0] + ")");

        // 5. DATOS DEL SISTEMA / MULTIPLATAFORMA
        System.out.println("\nSISTEMA");
        System.out.println("==================================================");

        String osName = System.getProperty("os.name");
        String fileSeparator = System.getProperty("file.separator");
        String userHome = System.getProperty("user.home");

        System.out.println("os.name: " + osName);
        System.out.println("file.separator: \"" + fileSeparator + "\"");

        // Construimos la ruta de informe.txt usando las propiedades sin hardcodear separadores
        String rutaInforme = userHome + fileSeparator + "psp" + fileSeparator + "informe.txt";
        System.out.println("Ruta construida con las propiedades:\n" + rutaInforme);

        // 6. PROPIEDADES DEL SISTEMA
        // Prefijos por defecto si no nos pasan ninguno por la línea de comandos
        String[] prefijos = (args.length > 0) ? args : new String[]{"os.", "user.", "java.version"};

        System.out.print("\nPROPIEDADES QUE EMPIEZAN POR ");
        for (int i = 0; i < prefijos.length; i++) {
            System.out.print(prefijos[i] + (i < prefijos.length - 1 ? ", " : ""));
        }
        System.out.println("\n==================================================");

        Properties propiedades = System.getProperties();
        // Usamos TreeMap para almacenar y ordenar las claves alfabéticamente
        TreeMap<String, String> propiedadesOrdenadas = new TreeMap<>();

        for (String clave : propiedades.stringPropertyNames()) {
            for (String prefijo : prefijos) {
                if (clave.startsWith(prefijo)) {
                    propiedadesOrdenadas.put(clave, propiedades.getProperty(clave));
                    break;
                }
            }
        }

        // Mostramos las propiedades filtradas
        for (Map.Entry<String, String> entrada : propiedadesOrdenadas.entrySet()) {
            System.out.println(entrada.getKey() + " = " + entrada.getValue());
        }

        // 7. ESPERA FINAL
        System.out.println("\nPROCESO EN ESPERA");
        System.out.println("==================================================");
        System.out.println("Buscame desde otra terminal con:\nps -ef | grep InformeSistema");
        System.out.println("Pulsa INTRO para terminar...");

        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.out.println("Fin del programa.");
        sc.close();
    }
}