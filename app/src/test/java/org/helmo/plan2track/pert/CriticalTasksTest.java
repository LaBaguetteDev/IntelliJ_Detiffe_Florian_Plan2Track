package org.helmo.plan2track.pert;

import org.checkerframework.checker.units.qual.A;
import org.helmo.plan2track.entities.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CriticalTasksTest {

    // A        B       C       D       E       F       G       H       I       J
    //          E       E       A       A      D,E      B       G    J,C,H,F    A

    Task a, b, c, d, e, f, g, h, i, j;

    @BeforeEach
    void setup() {
        a = new Task("a", 1);
        e = new Task("e", 1, a);
        b = new Task("b", 1, e);
        c = new Task("c", 1, e);
        d = new Task("d", 1, a);
        f = new Task("f", 1, d, e);
        g = new Task("g", 1, b);
        h = new Task("h", 1, g);
        j = new Task("j", 1, a);
        i = new Task("i", 1, j, c, h, f);
    }

    @Test
    void determineTaskLevels() {
        //Niveau    0       1       2       3       4       5
        //Taches    A     D,E,J   B,C,F     G       H       I
        Map<Integer, List<Task>> expectedLevels = new HashMap<Integer, List<Task>>();

        List<Task> levelZero = List.of(a);
        List<Task> levelOne = List.of(d, e, j);
        List<Task> levelTwo= List.of(b, c, f);
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

        /*for (int level : expectedLevels.keySet()) {
            for (Task t : expectedLevels.get(level)) {
                System.out.println(level + " " + t.getName());
            }
        }
        System.out.println();
        for (int level : actualLevels.keySet()) {
            for (Task t : actualLevels.get(level)) {
                System.out.println(level + " " + t.getName());
            }
        }*/

        assertTrue(expectedLevels.equals(actualLevels));
    }

}