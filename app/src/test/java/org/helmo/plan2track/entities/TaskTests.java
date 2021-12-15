package org.helmo.plan2track.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTests {



    @Test
    void returnName() {
        Task t = new Task("tache", 1);

        assertEquals("tache", t.getName());
    }

    @Test
    void returnDuration() {
        Task t = new Task("tache", 1);

        assertEquals(1, t.getDuration());

        assertEquals("1 jours", t.getDurationString());
    }

    @Test
    void returnEmptyDescription() {
        Task t = new Task("tache", 1);
        assertEquals("", t.getDescription());
    }

    @Test
    void returnEmptyChiefName() {
        Task t = new Task("tache", 1);
        assertEquals("", t.getChief().getName());
    }

    @Test
    void returnDurationWithDayString() {
        Task t = new Task("tache", 1);
        assertEquals("1 jours", t.getDurationString());
    }

    @Test
    void verifyTaskHasNoPriorTasks() {
        Task t = new Task("tache", 1);

        assertTrue(t.hasNoPriors());

        Task t2 = new Task("Autre tache", 1);
        t.setPriors(List.of(t2));

        assertFalse(t.hasNoPriors());
    }

    @Test
    void returnTrueOnVerifyPriorTaskExist() {
        Task t = new Task("tache", 1);
        Task t2 = new Task("Autre tache", 1);
        t.setPriors(List.of(t2));

        assertTrue(t.checkPriorTaskExist("Autre tache"));
        assertFalse(t.checkPriorTaskExist("tache"));
    }

    @Test
    void returnTrueOnTwoTaskHavingSameName() {
        Task t = new Task("tache", 1);
        Task t2 = new Task("tache", 1);

        assertTrue(t.hasSameName(t2));
        assertTrue(t.hasSameName("tache"));
    }

    @Test
    void assignTaskToAChief() {

        Task t = new Task("tache", 1);
        Chief c = new Chief("Chef", "C001");

        assertFalse(t.hasBeenAssigned());

        t.addChief(c);
        assertTrue(t.hasBeenAssigned());
    }

    @Test
    void deletePriorTaskWithHisName() {
        Task t = new Task("tache", 1);
        Task t2 = new Task("Autre tache", 1);
        t.setPriors(List.of(t2));
        assertTrue(t.checkPriorTaskExist("Autre tache"));

        t.deletePriorTaskByName("Autre tache");
        assertFalse(t.checkPriorTaskExist("Autre tache"));

    }
}
