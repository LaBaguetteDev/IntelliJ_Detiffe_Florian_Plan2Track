package org.helmo.plan2track.pert;

import org.helmo.plan2track.entities.CriticalTasks;
import org.helmo.plan2track.entities.Task;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CriticalTasksTest {

    @Test
    void determineTaskLevels() {

        // A        B       C       D       E       F       G       H       I       J
        //          E       E       A       A      D,E      B       G    J,C,H,F    A

        Task a, b, c, d, e, f, g, h, i, j;
        a = new Task("a", 2);
        e = new Task("e", 2, a);
        b = new Task("b", 1, e);
        c = new Task("c", 3, e);
        d = new Task("d", 4, a);
        f = new Task("f", 1, d, e);
        g = new Task("g", 4, b);
        h = new Task("h", 1, g);
        j = new Task("j", 1, a);
        i = new Task("i", 2, j, c, h, f);

        //Niveau    0       1       2       3       4       5
        //Taches    A     D,E,J   B,C,F     G       H       I
        Map<Integer, List<Task>> expectedLevels = new HashMap<Integer, List<Task>>();

        List<Task> levelZero = List.of(a);
        List<Task> levelOne = List.of(d, e, j);
        List<Task> levelTwo = List.of(b, c, f);
        List<Task> levelThree = List.of(g);
        List<Task> levelFour = List.of(h);
        List<Task> levelFive = List.of(i);

        expectedLevels.put(0, levelZero);
        expectedLevels.put(1, levelOne);
        expectedLevels.put(2, levelTwo);
        expectedLevels.put(3, levelThree);
        expectedLevels.put(4, levelFour);
        expectedLevels.put(5, levelFive);


        List<Task> initialList = List.of(a, b, c, d, e, f, g, h, i, j);
        CriticalTasks ct = new CriticalTasks(initialList);
        Map<Integer, List<Task>> actualLevels = ct.getResult();


        /*for (var entry : actualLevels.entrySet()) {
            System.out.println("Niveau " + entry.getKey());
            for (var t : entry.getValue()) {
                System.out.println(t.getName());
            }
        }*/

        System.out.println("Date de fin au plus tot : " + ct.getDateFinAuPlusTot());

        assertTrue(expectedLevels.equals(actualLevels));


    }

    @Test
    void determinelevelTest2() {
        Task a, b, c, d, e, f, g;
        a = new Task("a", 6);
        b = new Task("b", 5);
        c = new Task("c", 4, a);
        d = new Task("d", 6, b);
        e = new Task("e", 5, c);
        f = new Task("f", 6, a, d);
        g = new Task("g", 4, e, f);

        List<Task> initialList = List.of(a, b, c, d, e, f, g);
        CriticalTasks ct = new CriticalTasks(initialList);
        Map<Integer, List<Task>> actualLevels = ct.getResult();


        for (var entry : actualLevels.entrySet()) {
            System.out.println("Niveau " + entry.getKey());
            for (var t : entry.getValue()) {
                System.out.println(t.getName());
            }
        }

        System.out.println("Date de fin au plus tot : " + ct.getDateFinAuPlusTot());
    }

    @Test
    void determinelevelTest3() {
        Task a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p;
        d = new Task("d", 4);
        g = new Task("g", 7);
        c = new Task("c", 2, g);
        i = new Task("i", 5);
        p = new Task("p", 6, g);
        n = new Task("n", 1, i, p);
        a = new Task("a", 3, i, p);
        e = new Task("e", 8, i, p);
        o = new Task("o", 2, d, e);
        h = new Task("h", 5, i, p);
        m = new Task("m", 7, d, e);
        k = new Task("k", 3, m, n, o);
        j = new Task("j", 6, a, g);
        b = new Task("b", 5, a, c, h);
        f = new Task("f", 1, a);
        l = new Task("l", 4, a, f, k);

        List<Task> initialList = List.of(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
        CriticalTasks ct = new CriticalTasks(initialList);
        Map<Integer, List<Task>> actualLevels = ct.getResult();

        for (var entry : actualLevels.entrySet()) {
            System.out.println("Niveau " + entry.getKey());
            for (var t : entry.getValue()) {
                System.out.println(t.getName());
            }
        }

        System.out.println("Date de fin au plus tot : " + ct.getDateFinAuPlusTot());
    }
}