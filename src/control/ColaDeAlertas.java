package control;

import model.Alerta;

/**
 * Cola de prioridad implementada mediante un Max-Heap sobre arreglo dinámico.
 */
public class ColaDeAlertas {

    private Alerta[] monticulo;
    private int cantidad;
    private static final int CAPACIDAD_INICIAL = 16;

    public ColaDeAlertas() {
        this(CAPACIDAD_INICIAL);
    }

    public ColaDeAlertas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a cero");
        }
        this.monticulo = new Alerta[capacidad];
        this.cantidad = 0;
    }

    public void insertar(Alerta alerta) {
        if (alerta == null) {
            throw new IllegalArgumentException("No se puede insertar una alerta nula");
        }

        if (this.cantidad == this.monticulo.length) {
            redimensionar();
        }

        this.monticulo[this.cantidad] = alerta;
        flotar(this.cantidad);
        this.cantidad++;
    }

    public void flotar(int indice) {
        int actual = indice;
        while (actual > 0) {
            int padre = (actual - 1) / 2;
            if (this.monticulo[actual].compareTo(this.monticulo[padre]) > 0) {
                intercambiar(actual, padre);
                actual = padre;
            } else {
                break;
            }
        }
    }

    public Alerta atender() {
        if (estaVacia()) {
            return null;
        }

        Alerta maxima = this.monticulo[0];
        this.monticulo[0] = this.monticulo[this.cantidad - 1];
        this.monticulo[this.cantidad - 1] = null;
        this.cantidad--;

        if (this.cantidad > 0) {
            hundir(0);
        }

        return maxima;
    }

    private void hundir(int indice) {
        int actual = indice;
        while (true) {
            int izquierdo = 2 * actual + 1;
            int derecho = 2 * actual + 2;
            int mayor = actual;

            if (izquierdo < this.cantidad && this.monticulo[izquierdo].compareTo(this.monticulo[mayor]) > 0) {
                mayor = izquierdo;
            }

            if (derecho < this.cantidad && this.monticulo[derecho].compareTo(this.monticulo[mayor]) > 0) {
                mayor = derecho;
            }

            if (mayor != actual) {
                intercambiar(actual, mayor);
                actual = mayor;
            } else {
                break;
            }
        }
    }

    public Alerta consultarMaxima() {
        if (estaVacia()) {
            return null;
        }
        return this.monticulo[0];
    }

    private void intercambiar(int i, int j) {
        Alerta temp = this.monticulo[i];
        this.monticulo[i] = this.monticulo[j];
        this.monticulo[j] = temp;
    }

    private void redimensionar() {
        Alerta[] nuevo = new Alerta[this.monticulo.length * 2];
        System.arraycopy(this.monticulo, 0, nuevo, 0, this.monticulo.length);
        this.monticulo = nuevo;
    }

    public boolean estaVacia() {
        return this.cantidad == 0;
    }

    public int tamano() {
        return this.cantidad;
    }
}