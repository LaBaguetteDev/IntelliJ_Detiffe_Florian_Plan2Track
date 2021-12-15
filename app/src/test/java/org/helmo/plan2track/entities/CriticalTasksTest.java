package org.helmo.plan2track.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CriticalTasksTest {

    @Test
    void ReturnEarliestDateAndCriticalTasksOnSimpleMontage() {
        Montage m = new Montage("Test d'acceptation 1");

        Task ds = new Task("Démonter scène", 1);
        Task rs = new Task("Rapatriement scène", 1, "Aller chercher au hangar", List.of(ds));
        Task ms = new Task("Montage scène", 1, "Campus Guillemins", List.of(rs));

        m.addTasks(ds, rs, ms);

        CriticalTasks ct = new CriticalTasks(m);

        assertEquals(3, ct.getEarliestEndDate());
        List<Task> result = ct.getCriticalTasks();
        List<Task> expected = List.of(ds, rs, ms);
        assertTrue(expected.size() == result.size() && result.containsAll(expected));
    }

    @Test
    void ReturnEarliestDateAndCriticalTasksOnComplexMontage() {
        Montage m = new Montage("Test d'acceptation 2");

        Task a = new Task("A", 6);
        Task b = new Task("B", 5);
        Task c = new Task("C", 4, "", List.of(a));
        Task d = new Task("D", 6, "", List.of(b));
        Task e = new Task("E", 5, "", List.of(c));
        Task f = new Task("F", 6, "", List.of(a, d));
        Task g = new Task("G", 4, "", List.of(e, f));

        m.addTasks(a, b, c, d, e, f, g);

        CriticalTasks ct = new CriticalTasks(m);

        assertEquals(21, ct.getEarliestEndDate());
        List<Task> result = ct.getCriticalTasks();
        List<Task> expected = List.of(b, d, f, g);
        assertTrue(expected.size() == result.size() && result.containsAll(expected));
    }
}
