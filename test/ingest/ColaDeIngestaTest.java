package ingest;

import model.LecturaSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColaDeIngestaTest {

    private ColaDeIngesta cola;

    @BeforeEach
    public void setUp() {
        cola = new ColaDeIngesta(3);
    }

    @Test
    public void testReciclajeCircularSinDescartes() {
        LecturaSensor l1 = new LecturaSensor("S1", "2026-11-02 10:00", 20.0, 50.0, 10.0);
        LecturaSensor l2 = new LecturaSensor("S2", "2026-11-02 10:01", 21.0, 51.0, 11.0);
        LecturaSensor l3 = new LecturaSensor("S3", "2026-11-02 10:02", 22.0, 52.0, 12.0);

        assertTrue(cola.encolar(l1));
        assertTrue(cola.encolar(l2));
        assertTrue(cola.encolar(l3));
        assertTrue(cola.estaLlena());

        // Desencolar para reciclar espacio
        assertEquals(l1, cola.desencolar());
        assertFalse(cola.estaLlena());

        // Encolar nuevo dato reutilizando posición liberada
        LecturaSensor l4 = new LecturaSensor("S4", "2026-11-02 10:03", 23.0, 53.0, 13.0);
        assertTrue(cola.encolar(l4));
        assertEquals(0, cola.getLecturasDescartadas());

        assertEquals(l2, cola.desencolar());
        assertEquals(l3, cola.desencolar());
        assertEquals(l4, cola.desencolar());
        assertTrue(cola.estaVacia());
    }
}