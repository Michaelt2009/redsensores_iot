package experiment;

import control.ColaDeAlertas;
import ingest.ColaDeIngesta;
import model.Alerta;
import model.LecturaSensor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BancoDeFlujo {

    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("       BANCO DE FLUJO - EXPERIMENTOS SEMANA 06               ");
        System.out.println("=============================================================\n");

        probarColaCircularSinDescartes();
        System.out.println();
        ejecutarComparativaListaVsHeap();
    }

    private static void probarColaCircularSinDescartes() {
        System.out.println("--- EXPERIMENTO 1: Flujo continuo en Cola Circular ---");
        int capacidadBuffer = 10;
        int totalLecturasAProcesar = 50;

        ColaDeIngesta cola = new ColaDeIngesta(capacidadBuffer);
        int lecturasEncoladas = 0;
        int lecturasProcesadas = 0;

        for (int i = 1; i <= totalLecturasAProcesar; i++) {
            // Se utiliza el constructor original compatible de LecturaSensor
            LecturaSensor lectura = new LecturaSensor(
                    "SENS-" + (i % 5),
                    "2026-11-02 10:00",
                    20.0 + (i % 10),
                    50.0,
                    15.0
            );

            if (cola.estaLlena()) {
                cola.desencolar();
                lecturasProcesadas++;
            }

            if (cola.encolar(lectura)) {
                lecturasEncoladas++;
            }
        }

        while (!cola.estaVacia()) {
            cola.desencolar();
            lecturasProcesadas++;
        }

        System.out.printf("Total generadas: %d | Encoladas: %d | Procesadas: %d%n",
                totalLecturasAProcesar, lecturasEncoladas, lecturasProcesadas);
        System.out.printf("Descartes registrados: %d%n", cola.getLecturasDescartadas());
        System.out.println("Resultado: " + (cola.getLecturasDescartadas() == 0
                ? "EXITOSO (Espacio reciclado correctamente sin pérdidas)"
                : "FALLO (Hubo descartes indebidos)"));
    }

    private static void ejecutarComparativaListaVsHeap() {
        System.out.println("--- EXPERIMENTO 2: Comparativa Lista Ordenada vs Montículo ---");
        int[] tamanosN = {1000, 5000, 10000, 25000, 50000};
        Random random = new Random(42);

        System.out.printf("%-10s | %-20s | %-20s%n", "N Alertas", "Lista Ordenada (ms)", "Montículo Heap (ms)");
        System.out.println("------------------------------------------------------------------");

        for (int n : tamanosN) {
            Alerta[] alertasPrueba = new Alerta[n];
            long baseTime = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                int severidad = 1 + random.nextInt(5);
                alertasPrueba[i] = new Alerta("SENS-" + i, "ALERTA", severidad, baseTime + i);
            }

            // Medición Lista Ordenada
            long inicioLista = System.nanoTime();
            ArrayList<Alerta> listaOrdenada = new ArrayList<>(n);
            for (Alerta a : alertasPrueba) {
                insertarEnListaOrdenada(listaOrdenada, a);
            }
            while (!listaOrdenada.isEmpty()) {
                listaOrdenada.remove(listaOrdenada.size() - 1);
            }
            long tiempoListaMs = (System.nanoTime() - inicioLista) / 1_000_000;

            // Medición Montículo Heap
            long inicioHeap = System.nanoTime();
            ColaDeAlertas heap = new ColaDeAlertas(n);
            for (Alerta a : alertasPrueba) {
                heap.insertar(a);
            }
            while (!heap.estaVacia()) {
                heap.atender();
            }
            long tiempoHeapMs = (System.nanoTime() - inicioHeap) / 1_000_000;

            System.out.printf("%-10d | %-20d | %-20d%n", n, tiempoListaMs, tiempoHeapMs);
        }
    }

    private static void insertarEnListaOrdenada(ArrayList<Alerta> lista, Alerta nueva) {
        int index = Collections.binarySearch(lista, nueva);
        if (index < 0) {
            index = -(index + 1);
        }
        lista.add(index, nueva);
    }
}