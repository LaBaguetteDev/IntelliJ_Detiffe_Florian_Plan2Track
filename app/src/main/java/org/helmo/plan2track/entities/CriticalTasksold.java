package org.helmo.plan2track.entities;

import java.util.*;

public class CriticalTasksold {

    private final SortedMap<Integer, List<Task>> stepOneResult = new TreeMap<>();
    List<Task> initialTaskList = new ArrayList<>();
    List<Task> criticalTasks = new ArrayList<>();

    public CriticalTasksold(List<Task> tasks) {
        createCopyOfTaskList(tasks);
        determineTaskLevels(tasks);
    }

    private void createCopyOfTaskList(List<Task> tasks) {
        for (var t : tasks) {
            initialTaskList.add(new Task(t.getName(), t.getDuration(), t.getDescription(), t.getPriors()));
        }
    }

    public Map<Integer, List<Task>> getResult() {
        return stepOneResult;
    }

    public List<Task> getCriticalTasks() {
        return criticalTasks;
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

        reAddPriorTaskToResult();
    }

    private void removeSumZeroTask(List<Task> tasks, List<Task> tasksToRemove) {
        for (Task t : tasks) {
            for (Task tRemove : tasksToRemove)
                t.deletePriorTaskByName(tRemove.getName());
        }
    }

    private void reAddPriorTaskToResult() {
        for (var entry : stepOneResult.entrySet()) {
            for (var taskInResult : entry.getValue()) {
                for (var t : initialTaskList) {
                    if(t.getName().equals(taskInResult.getName())) {
                        taskInResult.setPriors(t.getPriors());
                    }
                }
            }
        }
    }

    public int getDateFinAuPlusTot() {
        Task highestLevelTask = stepOneResult.get(stepOneResult.lastKey()).get(0);
        criticalTasks.add(highestLevelTask);
        int duree = highestLevelTask.getDuration();
        duree += getHighestLevelTaskDuration(stepOneResult.get(stepOneResult.lastKey()).get(0).getPriors());

        Collections.reverse(criticalTasks);
        return duree;
    }

    public int getHighestLevelTaskDuration(List<Task> tasks) {
        int max = Integer.MIN_VALUE;
        Task correctTask = null;
        for (var t : tasks) {
            int temp = getTaskLevel(t);
            if(temp > max) {
                max = temp;
                correctTask = t;
            } else if(temp == max) {
                max = Integer.MAX_VALUE;
                correctTask = getHighestDurationTask(tasks);
            }
        }

        criticalTasks.add(correctTask);
        if(correctTask.getPriors().isEmpty()) {
            return correctTask.getDuration();
        } else {
            return correctTask.getDuration() + getHighestLevelTaskDuration(correctTask.getPriors());
        }

    }

    public Task getHighestDurationTask(List<Task> tasks) {
        int duree = 0;
        Task correctTask = null;
        for (var t : tasks) {
            if(duree < t.getDuration()) {
                duree = t.getDuration();
                correctTask = t;
            }
        }

        return correctTask;
    }

    public int getTaskLevel(Task task) {
        int lvl = 0;

        for(var entry : stepOneResult.entrySet()) {
            for (var t : entry.getValue()) {
                if(t.equals(task)) {
                    lvl = entry.getKey();
                }
            }
        }

        return lvl;
    }



}
