package org.helmo.plan2track.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MontageTests {

    /**
     * Plan de test de suppression d'une tâche
     */

    /**
     * Cas de réussite basique
     */
    @Test
    void deletingTaskSuccessing() {
        Montage m = new Montage("montage");
        Task t1 = new Task("tache1", 1);

        m.addTasks(t1); // Quand j'ajoute ma tâche au montage
        assertTrue(m.getTasks().contains(t1)); // Elle est présente dans la liste des tâches du montage

        m.deleteTask(t1.getName()); // Quand je supprime ma tâche du montage
        assertFalse(m.getTasks().contains(t1)); // Elle n'est plus présente
    }

    /**
     * Cas de réussite avec tâche antérieure à supprimer
     */
    @Test
    void deletingTaskSuccessingWithAntTask() {
        Montage m = new Montage("montage");
        Task t1 = new Task("tache1", 1);
        Task t2 = new Task("tache1", 1);

        t1.setPriors(List.of(t2)); // Quand j'indique que t2 est au tâche antérieure à t1

        m.addTasks(t1, t2);
        assertTrue(t1.getPriors().contains(t2)); // La liste des tâches antérieures à t1 contient t2

        m.deleteTask(t2.getName()); // Quand je supprime t2 du montage
        assertFalse(t1.getPriors().contains(t2)); // t2 n'est plus présente dans les tâches antérieures de t1
    }

    /**
     * Cas de réussite avec plusieurs tâches antérieures à supprimer
     */
    @Test
    void deletingTaskSuccessingWithManyAntTask() {
        Montage m = new Montage("montage");
        Task A = new Task("A", 6);
        Task B = new Task("B", 5);
        Task C = new Task("C", 4);
        Task D = new Task("D", 6);
        Task E = new Task("E", 5);
        Task F = new Task("F", 6);
        Task G = new Task("G", 4);
        C.setPriors(List.of(A));
        D.setPriors(List.of(B));
        E.setPriors(List.of(C));
        F.setPriors(List.of(A, D));
        G.setPriors(List.of(E, F));
        m.addTasks(A, B, C, E, F, G);

        m.deleteTask(A.getName());

        assertFalse(C.getPriors().contains(A));
        assertFalse(F.getPriors().contains(A));
    }

    /**
     * Cas d'échec avec plusieurs tâches du même nom à supprimer
     */
    @Test
    void deleteTaskSuccessWithManySameNameTask() {
        Montage m = new Montage("montage");
        Task A = new Task("A", 6);
        Task A2 = new Task("A", 5);
        Task A3 = new Task("A", 4);
        Task D = new Task("D", 6);
        Task E = new Task("E", 5);
        Task F = new Task("F", 6);
        Task G = new Task("G", 4);
        A2.setPriors(List.of(A));
        D.setPriors(List.of(A2));
        E.setPriors(List.of(A3));
        F.setPriors(List.of(A, D));
        G.setPriors(List.of(E, F));
        m.addTasks(A, A2, A3, E, F, G);

        m.deleteTask(A.getName()); // Supprime la première instance trouvée mais pas les autres

        assertFalse(m.getTasks().contains(A));
        assertTrue(m.getTasks().contains(A2)); // Echec -> Devrait retourner Faux
        assertTrue(m.getTasks().contains(A3)); // Echec -> Devrait retourner Faux

        m.deleteTask(A2.getName()); // Il faut supprimer de nouveau la tâche
        m.deleteTask(A2.getName()); // Fonctionne si on récupère le même nom mais d'une autre tâche
        assertFalse(m.getTasks().contains(A2));
        assertFalse(m.getTasks().contains(A3));
    }

    /**
     * Cas normal avec suppression de tâche inexistante dans le montage
     */
    @Test
    void deleteTaskNotInMontage() {
        Montage m = new Montage("montage");
        Task A = new Task("A", 6);
        Task B = new Task("B", 5);
        Task C = new Task("C", 4);
        Task D = new Task("D", 6);
        Task E = new Task("E", 5);
        Task F = new Task("F", 6);
        Task G = new Task("G", 4); // N'est pas dans le montage
        C.setPriors(List.of(A));
        D.setPriors(List.of(B));
        E.setPriors(List.of(C));
        F.setPriors(List.of(A, D));
        G.setPriors(List.of(E, F));
        m.addTasks(A, B, C, E, F);

        m.deleteTask(G.getName()); // Suppression de la tâche non présente

        assertFalse(m.getTasks().contains(G));
    }

}
