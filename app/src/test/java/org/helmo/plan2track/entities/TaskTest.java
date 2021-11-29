package org.helmo.plan2track.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void initTaskWithManyPriorTasks() {
        Task tAnt0 = new Task("tache anterieur0", 1);
        Task tAnt1 = new Task("tache anterieur1", 1);
        Task t = new Task("tache", 1, tAnt0, tAnt1);

        Assertions.assertTrue(t.checkPriorTaskExist("tache anterieur0"));
        Assertions.assertTrue(t.checkPriorTaskExist("tache anterieur1"));
    }

    @Test
    void returnStringWithNoDescriptionTaskInfo() {
        Task t = new Task("tache", 1);
        String expected = "tache : / 1j Requis : - \n";

        assertEquals(expected, t.printTask());
    }

    @Test
    void returnStringWithDescriptionTaskInfo() {
        Task tAnt0 = new Task("t0", 1);
        Task tAnt1 = new Task("t1", 1);
        List<Task> tAnt = new ArrayList<>();
        tAnt.add(tAnt0); tAnt.add(tAnt1);
        Task t = new Task("tache", 1, "description", tAnt);

        String expected = "tache : description 1j Requis : - t0 - t1 - \n";

        assertEquals(expected, t.printTask());
    }

    @Test
    void initTaskWithDescriptionAndPriorTaskList() {
        Task tAnt0 = new Task("tache anterieur0", 1);
        Task tAnt1 = new Task("tache anterieur1", 1);
        List<Task> tAnt = new ArrayList<>();
        tAnt.add(tAnt0); tAnt.add(tAnt1);
        Task t = new Task("tache", 1, "description", tAnt);

        Assertions.assertTrue(t.checkPriorTaskExist("tache anterieur0"));
        Assertions.assertTrue(t.checkPriorTaskExist("tache anterieur1"));
    }

    @Test
    void affectAChief() {
        Task t = new Task("tache", 1);
        Chief c = new Chief("Florian");
        t.addChief(c);

        assertEquals(t.getChief(), c);
    }

    // Plan de test suppression tache antérieur

    // Cas de réussite :
    @Test
    void checkPriorTaskExistSucess() {
        Task tAnt = new Task("tache anterieur", 1); // Création de la tache antérieur
        Task t = new Task("tache", 1, tAnt); // Ajoute de la tache antérieur à une nouvelle tache

        // Vérifie que la tache antérieur est présente dans la nouvelle tache :
        Assertions.assertTrue(t.checkPriorTaskExist("tache anterieur"));

        t.deletePriorTaskByName("tache anterieur"); // Supprime la tache antérieur de la nouvelle tache

        // Vérifie que la tache antérieur n'est plus présente dans la nouvelle tache
        Assertions.assertFalse(t.checkPriorTaskExist("tache anterieur"));
    }

    // Cas d'échec :
    @Test
    void checkPriorTaskExistFail() {
        //Task tAnt = new Task("tache anterieur", 1); // Création de la tache antérieur
        Task t = new Task("tache", 1); // N'ajoute pas de tache antérieur à la nouvelle tache

        // Vérifie que la tache antérieur n'est présente dans la nouvelle tache :
        Assertions.assertFalse(t.checkPriorTaskExist("tache anterieur"));

        t.deletePriorTaskByName("tache anterieur"); // Supprime la tache antérieur de la nouvelle tache --> Rien ne se passe

        // Vérifie que la tache antérieur n'est plus présente dans la nouvelle tache
        Assertions.assertFalse(t.checkPriorTaskExist("tache anterieur"));
    }
}