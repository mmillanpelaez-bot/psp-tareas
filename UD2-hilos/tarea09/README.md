# Tarea 09 · Descargas Cuánticas: el gestor de Don Magufo

Módulo PSP · CFGS DAM 2 · Curso 2026-2027

## Niveles hechos

- [x] Nivel 1 (obligatorio)
- [x] Nivel 2 · archivos por línea de comandos + `Monitor` (Runnable)
- [x] Nivel 3 · `Instalador` (Runnable) + espera con límite de 3 s

Solo se usan `Thread`, `Runnable`, `sleep`, `join` e `isAlive`.

## Clases

| Clase | Qué hace |
|-------|----------|
| `Descarga` (hereda de `Thread`) | Simula la descarga de un archivo en 10 bloques y guarda cuánto ha tardado |
| `GestorDescargas` | Tiene el `main`: crea y lanza las descargas, espera y da el informe |
| `Monitor` (implementa `Runnable`) | Cada 500 ms dice cuántas descargas siguen vivas y termina solo |
| `Instalador` (implementa `Runnable`) | Espera a `meditacion.mp4` y `mantras.mp3` y "instala" |
| `ExperimentoSerie` | Solo para la pregunta 2: `start()` y `join()` en el mismo bucle |

## Cómo se ejecuta

```bash
cd src
javac *.java

java GestorDescargas                      # los 4 archivos por defecto
java GestorDescargas uno.pdf dos.mp3      # archivos por la línea de comandos (nivel 2)
java ExperimentoSerie                     # el experimento de la pregunta 2
```

> Con otros nombres de archivo el `Instalador` no se crea, porque necesita
> `meditacion.mp4` y `mantras.mp3`.

## Salida de ejemplo

```
[Monitor] Descargas en curso: 4
[mantras.mp3] 10%
[meditacion.mp4] 10%
[horoscopo.pdf] 10%
[cuarzos.png] 10%
...
[Main] meditacion.mp4 sigue en segundo plano
...
[mantras.mp3] completada en 3377 ms
[meditacion.mp4] completada en 4084 ms
[Instalador] Meditación y mantras listos: instalando...
[horoscopo.pdf] completada en 4114 ms
[cuarzos.png] completada en 4883 ms
[Monitor] No queda ninguna descarga en curso
[Instalador] Instalación terminada
Todas las descargas han terminado.
Tiempo real: 4893 ms
Si se hubieran descargado una detrás de otra: 16458 ms
```

---

## Tabla de ejecuciones (nivel 1)

> Los números salen distintos en cada ejecución porque los tiempos son aleatorios. 
> Estos son los que me salieron a mí, solo como referencia:

| Ejecución | Descarga más lenta | Tiempo real (ms) | Suma (ms) |
|-----------|--------------------|------------------|-----------|
| 1 | mantras.mp3, 3196 ms| 3199 ms| 10098 ms|
| 2 | horoscopo.pdf, 4635 ms| 4637 ms| 10080 ms|
| 3 | cuarzos.png, 4999 ms| 5000 ms| 15318 ms|

### ¿Por qué el tiempo real es mucho menor que la suma?

Porque las cuatro descargas se ejecutan a la vez, cada una en su hilo, y sus
esperas (`sleep`) se solapan. El tiempo total es aproximadamente el de la
descarga **más lenta** (en la tabla, el tiempo real es casi igual al de la más lenta).
La suma es lo que tardaría si las hiciéramos una detrás de otra: cada una tendría
que esperar a que acabara la anterior.

### ¿Qué pasa con `start()` y `join()` en el mismo bucle?

Se hacen una detrás de otra: el `join()` bloquea al `main` hasta que acaba la
descarga, y hasta entonces no se arranca la siguiente. Se pierde todo lo que se
ganaba con los hilos.

Lo probé con `ExperimentoSerie` y salió:

```
Tiempo real: 9910 ms
Suma de las descargas: 9899 ms
```

Es casi igual a la suma (la pequeña diferencia es el tiempo de crear y arrancar
los hilos).

La forma correcta es el bucle de `start()` primero y el bucle de `join()` después.

---

## Declaración de uso de IA

    - Usé Claude y Gemini como guía