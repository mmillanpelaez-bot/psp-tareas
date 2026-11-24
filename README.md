# PSP · Tareas de clase

Módulo Programación de Servicios y Procesos · CFGS DAM 2 · Curso 2026-2027

Aquí están las tareas pequeñas de clase. Las tareas 01, 05 y 09 piden entrega
propia (con README, capturas y tabla de pruebas), así que están en **repositorios aparte**:

- `psp-tarea01-informe-sistema`
- `psp-tarea05-detector-primos`
- `psp-tarea09-gestor-descargas`

## Estructura

```
psp-tareas/
├── UD1-procesos/   tarea02, tarea03, tarea04
└── UD2-hilos/      tarea06 ... tarea22
```

Cada tarea tiene su carpeta con un `.java` (sin paquetes, para que sea sencillo).

## Cómo compilar y ejecutar una tarea

```bash
cd UD2-hilos/tarea13
javac Tarea13.java
java Tarea13
```

> Las carpetas `tarea12` y `tarea13` tienen clases con el mismo nombre
> (`Contador`, `Hilo`). No pasa nada porque se compilan cada una en su carpeta,
> pero **no las compiles juntas** en el mismo directorio.

## Índice

| Tarea | Tema | Archivo |
|-------|------|---------|
| 02 | Abrir un archivo con un editor | `UD1-procesos/tarea02/src/Tarea02.java` |
| 03 | `user.dir` y directorio de trabajo | `UD1-procesos/tarea03/src/Tarea03.java` |
| 04 | Ejecutar comandos en bucle | `UD1-procesos/tarea04/src/Tarea04.java` |
| 06 | Profesores cabreados (Thread y Runnable) | `UD2-hilos/tarea06/src/Tarea06.java` |
| 07 | Fibonacci en un hilo | `UD2-hilos/tarea07/src/Tarea07.java` |
| 08 | Cuatro hilos que duermen | `UD2-hilos/tarea08/src/Tarea08.java` |
| 10 | Tres sumas en paralelo | `UD2-hilos/tarea10/src/Tarea10.java` |
| 11 | Prioridades | `UD2-hilos/tarea11/src/Tarea11.java` |
| 12 | Contador compartido (con fallo) | `UD2-hilos/tarea12/src/Tarea12.java` |
| 13 | Contador compartido sincronizado | `UD2-hilos/tarea13/src/Tarea13.java` |
| 14 | Estados de un hilo | `UD2-hilos/tarea14/src/Tarea14.java` |
| 15 | Orden secuencial inverso | `UD2-hilos/tarea15/src/Tarea15.java` |
| 16 | Flujo de caja | `UD2-hilos/tarea16/src/Tarea16.java` |
| 17 | Contador de caracteres | `UD2-hilos/tarea17/src/Tarea17.java` |
| 18 | Variables atómicas | `UD2-hilos/tarea18/src/Tarea18.java` |
| 19 | Parking con Semaphore | `UD2-hilos/tarea19/src/Tarea19.java` |
| 20 | Productor-consumidor con cola | `UD2-hilos/tarea20/src/Tarea20.java` |
| 21 | Consonantes con pool de hilos | `UD2-hilos/tarea21/src/Tarea21.java` |
| 22 | Pizzería con pool de hilos | `UD2-hilos/tarea22/src/Tarea22.java` |

> Nota: en los enunciados, los ficheros "Tarea 07 - Fibonacci.txt" y
> "Tarea 08 Cuatro hilos.txt" tienen el contenido cambiado (el de 07 es el de
> los cuatro hilos y al revés). Aquí se sigue el **título que lleva dentro cada enunciado**:
> 07 = Fibonacci, 08 = cuatro hilos.
> Además, el enunciado de la 21 dice "rehaz la tarea 20", pero el título y los apuntes dicen que
> es la **17** (consonantes) con un pool, y eso es lo que está hecho.

---

## Respuestas y observaciones

### Tarea 03 · `user.dir` y directorio de trabajo

| Paso | Propiedad `user.dir` | Directorio donde se ejecuta `ls` |
|------|----------------------|----------------------------------|
| 1. Por defecto | La carpeta desde donde lanzas el programa | La misma |
| 2. `System.setProperty("user.dir", home)` | La carpeta home | **La misma que antes** |
| 3. `pb.directory(new File("/tmp"))` | La carpeta home (no cambia) | `/tmp` |

Conclusión: cambiar la propiedad `user.dir` solo cambia el **texto** de la propiedad.
No mueve de verdad el directorio de trabajo, ni siquiera el del proceso que
lanzamos. Para que el proceso hijo trabaje en otra carpeta hay que decírselo
con `ProcessBuilder.directory(...)`.

Además, el hijo no escribe en nuestra consola por su cuenta: hay que usar
`inheritIO()` (o leer su salida) o no se ve el listado.

### Tarea 04 · Códigos de salida

- Comando correcto (`ls`, `echo hola`): código **0**.
- Comando que existe pero con parámetros malos (`ls /noexiste`): código **2** en `ls`.
  Cada programa elige sus códigos.
- `false`: código **1**.
- Comando que **no existe** (`comandoinventado`): no hay código, porque el proceso ni
  siquiera llega a crearse. Java lanza `IOException` y la capturamos.

### Tarea 08 · Cuatro hilos que duermen

Resultados que me salieron:

| Forma | Tiempo total |
|-------|--------------|
| `start()` de todos y después `join()` de todos | 11 s |
| `start()` y `join()` en el mismo bucle | 47 s |

- **¿Se parece a la suma o al más lento?** Al del hilo **más lento**. Los cuatro
  duermen a la vez, así que sus esperas se solapan.
- **`start()` y `join()` en el mismo bucle:** cada hilo se espera antes de
  arrancar el siguiente, así que se ejecutan uno tras otro y el tiempo se
  parece a la suma (47 s). Se prueba con `java Tarea08 serie`.

### Tarea 11 · Prioridades

Todos los hilos hacen sus 10 iteraciones, sea cual sea su prioridad. En mis
pruebas **la prioridad no cambia nada claro**. Razones:

- Los hilos pasan casi todo el tiempo dormidos (`sleep` de 1 a 10 s), así que
  casi nunca compiten por la CPU. La prioridad solo es una *sugerencia* para
  el planificador cuando hay varios hilos listos para ejecutarse a la vez.
- En Linux, la JVM normal ignora las prioridades de los hilos, y en otros sistemas el
  efecto depende del sistema operativo.
- El tiempo aleatorio de `sleep` pesa mucho más que la prioridad.

Conclusión: no se puede confiar en las prioridades para controlar el orden. Para
eso hay que usar `join()`, `synchronized`, etc.

### Tarea 12 · Contador compartido (con fallo)

Esperado: 500.000. Con varios núcleos el resultado sale **casi siempre menor y
distinto en cada ejecución**, porque `valor++` son tres pasos (leer, sumar,
escribir) y dos hilos pueden leer el mismo valor a la vez: uno pisa el cambio
del otro (condición de carrera).

> Ojo: en un ordenador con **un solo núcleo** el fallo es muy raro y puede salir
> 500.000 aunque el código esté mal. Ejecútalo varias veces en tu equipo. Si no
> falla, sube las vueltas a un número más grande (por ejemplo 20.000.000) para verlo.

### Tarea 13 · Contador sincronizado

Con `synchronized` en `incrementar()` solo entra un hilo a la vez en el método, así
que el resultado es siempre **500.000** (comprobado en 5 ejecuciones).

### Tarea 14 · Estados de un hilo

Antes de abrir esto, **apunta tú tus predicciones** y ejecuta el programa. Después compara:

<details>
<summary>Ver la solución</summary>

```
(1) NEW
(2) TIMED_WAITING
[Cocinero] ¡Me han cortado la luz!
(3) TERMINATED
```

- (1) El hilo está creado pero todavía no se ha hecho `start()`: **NEW**.
- (2) Han pasado 200 ms y el cocinero está dentro de `sleep(3000)`: **TIMED_WAITING**
  (espera con tiempo límite).
- `interrupt()` despierta el `sleep` con una `InterruptedException`, y por eso sale
  el mensaje de "cortado la luz" y no el del guiso (que habría salido a los 3 s).
- (3) El `join()` espera a que acabe, así que el mensaje del cocinero sale
  **antes** que (3), y el estado es **TERMINATED**.

</details>

### Tarea 16 · Flujo de caja

Esperado: 1000 + 5000·10 − 3000·10 = **21.000 €**.

| Versión | Saldo final |
|---------|-------------|
| Con `synchronized` | 21000.0 (correcto) |
| Sin `synchronized` | 51000.0 (mal: se pierden las extracciones) |

Sin proteger, los dos hilos leen el saldo, esperan 1 ms (con el `sleep` el otro
tiene tiempo de sobra de entrar) y cada uno escribe su resultado pisando el del otro.
Con `synchronized` los tres pasos se hacen juntos, sin que el otro hilo se cuele en medio.
Tarda unos 10 s porque el `sleep(1)` se hace dentro del candado, uno tras otro.

### Tarea 17 · Contador de caracteres

La suma de lo que cuenta cada hilo coincide con el "Total compartido" gracias a
`synchronized` en `incrementar()`. Si se quita, el total sale menor que la suma
(mismo problema que en la tarea 12).

### Tarea 18 · Variables atómicas

El programa mide las dos versiones (5 ejecuciones cada una). En mi ordenador
(con un solo núcleo) salió algo así:

| | synchronized | AtomicInteger |
|---|---|---|
| Tiempo típico | ~15 ms | ~5 ms |

`AtomicInteger` suele ser más rápido porque no bloquea a los hilos: usa una
instrucción especial del procesador (comparar y cambiar). `synchronized` puede
dejar hilos esperando por el candado. Tus tiempos serán distintos según tu
ordenador; ejecútalo y apunta los tuyos.

### Tarea 20 · Productor-consumidor con cola

Sin `synchronized`, `wait()` ni `notifyAll()`: la `BlockingQueue` hace la espera por
dentro. `put()` espera si la cola está llena y `take()` espera si está vacía. La
cola tiene capacidad 1, como el almacén del ejemplo.

### Tarea 21 y 22 · Pools de hilos

- `Executors.newFixedThreadPool(n)` crea n hilos que se reutilizan.
- `pool.execute(tarea)` encarga una tarea (un `Runnable`). Si todos los hilos están
  ocupados, la tarea espera su turno.
- `pool.shutdown()` es imprescindible: sin él el programa no termina porque los
  hilos del pool se quedan esperando más trabajo.
- `pool.awaitTermination(...)` hace de `join()`: espera a que acaben las tareas.
- En la 22 se ve `pool-1-thread-1`, `2` y `3` repitiéndose: los mismos 3 cocineros
  hacen los 10 pedidos.
