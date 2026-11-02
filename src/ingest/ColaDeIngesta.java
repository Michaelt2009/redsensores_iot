package ingest;

import model.LecturaSensor;

/**
 * Cola circular FIFO (First In, First Out) sobre arreglo con reciclaje de posiciones.
 */
public class ColaDeIngesta {

    private final LecturaSensor[] buffer;
    private final int capacidad;
    private int frente;
    private int fin;
    private int cantidad;
    private long lecturasDescartadas;

    public ColaDeIngesta(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a cero");
        }
        this.capacidad = capacidad;
        this.buffer = new LecturaSensor[capacidad];
        this.frente = 0;
        this.fin = 0;
        this.cantidad = 0;
        this.lecturasDescartadas = 0;
    }

    public boolean encolar(LecturaSensor lectura) {
        if (lectura == null) {
            return false;
        }

        if (estaLlena()) {
            this.lecturasDescartadas++;
            return false;
        }

        this.buffer[this.fin] = lectura;
        this.fin = (this.fin + 1) % this.capacidad;
        this.cantidad++;
        return true;
    }

    public LecturaSensor desencolar() {
        if (estaVacia()) {
            return null;
        }

        LecturaSensor lectura = this.buffer[this.frente];
        this.buffer[this.frente] = null;
        this.frente = (this.frente + 1) % this.capacidad;
        this.cantidad--;
        return lectura;
    }

    public LecturaSensor primero() {
        if (estaVacia()) {
            return null;
        }
        return this.buffer[this.frente];
    }

    public boolean estaVacia() {
        return this.cantidad == 0;
    }

    public boolean estaLlena() {
        return this.cantidad == this.capacidad;
    }

    public int tamano() {
        return this.cantidad;
    }

    public int getCapacidad() {
        return this.capacidad;
    }

    public long getLecturasDescartadas() {
        return this.lecturasDescartadas;
    }
}