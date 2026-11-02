package control;

/**
 * Pila LIFO (Last In, First Out) para la gestión y reversión de acciones.
 */
public class PilaDeAcciones<T> {

    private static class NodoPila<T> {
        private final T accion;
        private final NodoPila<T> siguiente;

        public NodoPila(T accion, NodoPila<T> siguiente) {
            this.accion = accion;
            this.siguiente = siguiente;
        }
    }

    private NodoPila<T> tope;
    private int cantidad;

    public PilaDeAcciones() {
        this.tope = null;
        this.cantidad = 0;
    }

    public void apilar(T accion) {
        if (accion == null) {
            throw new IllegalArgumentException("No se puede apilar un elemento nulo");
        }
        this.tope = new NodoPila<>(accion, this.tope);
        this.cantidad++;
    }

    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        T dato = this.tope.accion;
        this.tope = this.tope.siguiente;
        this.cantidad--;
        return dato;
    }

    public T cima() {
        if (estaVacia()) {
            return null;
        }
        return this.tope.accion;
    }

    public boolean estaVacia() {
        return this.tope == null;
    }

    public int tamano() {
        return this.cantidad;
    }

    public void limpiar() {
        this.tope = null;
        this.cantidad = 0;
    }
}