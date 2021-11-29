package org.helmo.plan2track.pert;

import org.helmo.plan2track.entities.Task;

import java.util.*;

public class CriticalTasks {

    private final Map<Integer, List<Task>> stepOneResult = new HashMap<>();

    private final LinkedList<Map<Task, List<Task>>> graph = new LinkedList<>();

    public CriticalTasks(List<Task> tasks) {
        determineTaskLevels(tasks);
    }

    public Map<Integer, List<Task>> getResult() {
        return stepOneResult;
    }

    private void determineTaskLevels(List<Task> tasks) {
        List<Task> tasksCopy = new ArrayList<>(tasks);
        int l = -1;

        while (!tasksCopy.isEmpty()) {
            l++;
            List<Task> toRemove = new ArrayList<>();
            for (Task t : tasksCopy) {
                int sum = t.getPriors().size();

                if(sum == 0) {
                    this.stepOneResult.computeIfAbsent(l, level -> new ArrayList<>()).add(t);
                    toRemove.add(t);
                }
            }
            tasksCopy.removeAll(toRemove);
            removeSumZeroTask(tasksCopy, toRemove);
        }
    }

    private void removeSumZeroTask(List<Task> tasks, List<Task> tasksToRemove) {
        for (Task t : tasks) {
            for (Task tRemove : tasksToRemove)
            t.deletePriorTaskByName(tRemove.getName());
        }
    }

    private void makeGraph() {
        int index = 1;
        List<Task> pruningCandidates = new ArrayList<>();

        for (var entry : stepOneResult.entrySet()) {

            for (Task t : entry.getValue()) {

                if(t.getPriors().isEmpty()) {
                    // Ajouter au graph 1-->t-->index++
                    index++;
                } else {
                    Task highestRankedPredecessor = findHighestRankedPredecessor(t.getPriors());
                    // Ajouter au graph index++-->highestRankedPredecessor-->sommet

                    for (Task remaining : without(highestRankedPredecessor, t.getPriors())) {
                        Task fake = new Task("fake", 0);
                        // Ajouter origine-->remaining-->sommet
                        pruningCandidates.add(fake);
                    }
                }
            }
        }

        return;
    }

    private Task findHighestRankedPredecessor(List<Task> tasks) {
        return null;
    }

    private List<Task> without(Task t, List<Task> tasks) {
        return null;
    }
}
