package org.helmo.plan2track.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChiefTest {

    @Test
    void returnChiefName() {
        Chief c = new Chief("Florian", "F666");

        assertEquals("Florian", c.getName());
        assertEquals("F666", c.getCode());
        assertTrue(c.exist());
    }
}