package org.helmo.plan2track.entities;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanningTests {

    @Test
    void ReturnPlanningOnSimpleMontage() {
        Montage m = new Montage("Test d'acceptation 1");
        Task ds = new Task("Démonter scène", 1);
        Task rs = new Task("Rapatriement scène", 1, "Aller chercher au hangar", List.of(ds));
        Task ms = new Task("Montage scène", 1, "Campus Guillemins", List.of(rs));
        m.addTasks(ds, rs, ms);

        Planning p = new Planning(m, new Date(2021, Calendar.JANUARY, 1));
        Map<Task, Date> result = p.getPlanning();

        Map<Task, Date> expected = new HashMap<>();
        expected.put(ds, new Date(2021, Calendar.JANUARY, 1));
        expected.put(rs, new Date(2021, Calendar.JANUARY, 2));
        expected.put(ms, new Date(2021, Calendar.JANUARY, 3));

        assertEquals(expected, result);
    }

    @Test
    void ReturnPlanningOnComplexMontage() {
        Montage m = new Montage("Test d'acceptation 2");
        Task a = new Task("A", 6);
        Task b = new Task("B", 5);
        Task c = new Task("C", 4, "", List.of(a));
        Task d = new Task("D", 6, "", List.of(b));
        Task e = new Task("E", 5, "", List.of(c));
        Task f = new Task("F", 6, "", List.of(a, d));
        Task g = new Task("G", 4, "", List.of(e, f));
        m.addTasks(a, b, c, d, e, f, g);

        Planning p = new Planning(m, new Date(2021, Calendar.JANUARY, 1));
        Map<Task, Date> result = p.getPlanning();

        Map<Task, Date> expected = new HashMap<>();
        expected.put(a, new Date(2021, Calendar.JANUARY, 1));
        expected.put(b, new Date(2021, Calendar.JANUARY, 1));
        expected.put(c, new Date(2021, Calendar.JANUARY, 7));
        expected.put(d, new Date(2021, Calendar.JANUARY, 6));
        expected.put(e, new Date(2021, Calendar.JANUARY, 11));
        expected.put(f, new Date(2021, Calendar.JANUARY, 12));
        expected.put(g, new Date(2021, Calendar.JANUARY, 18));

        assertEquals(expected, result);
    }
}
