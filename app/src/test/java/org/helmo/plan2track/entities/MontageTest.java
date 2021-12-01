package org.helmo.plan2track.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MontageTest {

    /*@Test
    void returnName() {
        Montage m = new Montage("montage");

        String mName = m.getName();
        assertEquals("montage", mName);
    }

    @Test
    void returnTasks() {
        Montage m = new Montage("montage");

        Task t = new Task("tache", 1);
        Task t2 = new Task("tache2", 1);
        Task t3 = new Task("tache3", 1);
        m.addTasks(t,t2,t3);

        assertTrue(m.checkTaskExist("tache"));
        assertTrue(m.checkTaskExist("tache2"));
        assertTrue(m.checkTaskExist("tache3"));

        assertFalse(m.checkTaskExist("tache4"));

        List<Task> returnedTasks = m.getTasks();
        assertTrue(returnedTasks.contains(t));
        assertTrue(returnedTasks.contains(t2));
        assertTrue(returnedTasks.contains(t3));
    }

    @Test
    void setNewName() {
        Montage m = new Montage("montage");

        m.setName("nouveau montage");

        String mName = m.getName();
        assertEquals("nouveau montage", mName);
    }

    @Test
    void verifyPrintNoNameMontage() {
        Montage m = new Montage("null");

        String expected = "Aucun montage a été créé";
        assertEquals(expected, m.printMontage());
    }

    @Test
    void verifyPrintNoTaskMontage() {
        Montage m = new Montage("montage");

        String expected = "\tmontage\n\t(Aucune tâche)\n";
        assertEquals(expected, m.printMontage());

        Task t = new Task("tache", 1);
        Task t2 = new Task("tache2", 1);
        Task t3 = new Task("tache3", 1);
        m.addTasks(t,t2,t3);
    }

    // Plan de test suppression tache

    // Cas de réussite :
    @Test
    void deleteTaskSuccess() {
        Task t = new Task("tache", 1);
        Montage m = new Montage("montage");

        m.addTasks(t); // Ajoute la tache
        assertTrue(m.checkTaskExist("tache")); // Vérifie que la tache existe
        assertEquals(t, m.searchTaskByName("tache")); // Trouve la tache


        m.deleteTask("tache"); // Supprime la tache
        assertFalse(m.checkTaskExist("tache")); // Vérifie que la tache n'existe plus
    }

    // Cas d'échec :
    @Test
    void deleteTaskFail() {
        //Task t = new Task("tache", 1);
        Montage m = new Montage("montage");

        //m.addTasks(t); // N'ajoute pas la tache
        assertFalse(m.checkTaskExist("tache")); // Vérifie que la tache n'existe pas
        assertNull(m.searchTaskByName("tache")); // Ne trouve pas la tache --> renvoie null

        m.deleteTask("tache"); // Supprime la tache --> rien ne se passe
        assertFalse(m.checkTaskExist("tache")); // Vérifie que la tache n'existe pas
    }*/
}