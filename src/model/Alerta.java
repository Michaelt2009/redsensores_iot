package model;

/**
 * Representa una alerta emitida en la plataforma de monitoreo.
 * Implementa orden natural para colas de prioridad:
 * Mayor severidad tiene prioridad; a igual severidad, desempate FIFO (menor timestamp).
 */
public class Alerta implements Comparable<Alerta> {

    private final String idSensor;
    private final String tipo;
    private final int severidad;
    private final long timestamp;

    public Alerta(String idSensor, String tipo, int severidad, long timestamp) {
        this.idSensor = idSensor;
        this.tipo = tipo;
        this.severidad = severidad;
        this.timestamp = timestamp;
    }

    public String getIdSensor() { return idSensor; }
    public String getTipo() { return tipo; }
    public int getSeveridad() { return severidad; }
    public long getTimestamp() { return timestamp; }

    @Override
    public int compareTo(Alerta otra) {
        if (this.severidad != otra.severidad) {
            return Integer.compare(this.severidad, otra.severidad);
        }
        // Desempate FIFO: la alerta con menor timestamp llegó antes
        return Long.compare(otra.timestamp, this.timestamp);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Severidad: %d, Time: %d)", idSensor, tipo, severidad, timestamp);
    }
}