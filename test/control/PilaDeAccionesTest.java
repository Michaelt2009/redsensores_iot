package control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PilaDeAccionesTest {

    private PilaDeAcciones<String> pila;

    @BeforeEach
    public void setUp() {
        pila = new PilaDeAcciones<>();
    }

    @Test
    public void testPilaVaciaRetornaNull() {
        assertTrue(pila.estaVacia());
        assertNull(pila.cima());
        assertNull(pila.desapilar());
        assertEquals(0, pila.tamano());
    }

    @Test
    public void testOrdenLIFO() {
        pila.apilar("ACCION_1");
        pila.apilar("ACCION_2");
        pila.apilar("ACCION_3");

        assertEquals(3, pila.tamano());
        assertEquals("ACCION_3", pila.cima());

        assertEquals("ACCION_3", pila.desapilar());
        assertEquals("ACCION_2", pila.desapilar());
        assertEquals("ACCION_1", pila.desapilar());
        assertTrue(pila.estaVacia());
    }
}