package org.helmo.plan2track.entities;

import java.util.*;

/**
 * Classe permettant de savoir les tâches critiques d'un montage ainsi que sa date de fin au plus tôt
 *
 * Choix de collections :
 * Niveau des tâches :
 * Interface :
 * J'ai choisi une SortedMap car j'ai besoin d'associer un niveau à une liste de tâches. Une interface de type
 * clé - valeur m'a donc semblé évident. La clé correspond donc au niveau et la valeur correspond à la liste
 * de tâches de ce niveau.
 * J'avais aussi besoin que les tâches soient triées par niveau et je dois pouvoir accéder au dernier niveau
 * d'une tâche pour pouvoir calculer les tâches critiques et la date au plus tôt d'un montage. J'ai donc choisi
 * une SortedMap pour pouvoir avoir accès à la méthode lastKey() qui renvoie la dernière clé de la Map.
 *
 * Implémentation :
 * Les méthodes d'insertion et de recherche d'une TreeMap ont une complexité temporelle logarithmique
 * Etant donné que les élements sont triés en arbre. TreeMap est la seule implémentation possible d'une
 * SortedMap avec ConcurrentSkipListMap.
 */
public class CriticalTasks {

    private final SortedMap<Integer, List<Task>> taskLevels = new TreeMap<>();
    private final List<Task> initialTaskList = new ArrayList<>();
    private final List<Task> criticalTasks = new ArrayList<>();
    private int earliestEndDate;

    /**
     * Initialise les tâches critique et la date de fin au plus tôt
     * à partir du montage entré en paramètre
     * @param m Montage contenant la liste de tâche dont on veut obtenir les statistiques
     */
    public CriticalTasks(Montage m) {
        if(m != null) {
            List<Task> tasks = m.getTasks();
            createCopyOfTaskList(tasks);
            determineTaskLevels(tasks);
            earliestEndDate = getDateFinAuPlusTot();
        }
    }

    public List<Task> getCriticalTasks() {
        return criticalTasks;
    }

    public int getEarliestEndDate() {
        return earliestEndDate;
    }


    private void createCopyOfTaskList(List<Task> tasks) {
        for (var t : tasks) {
            initialTaskList.add(new Task(t.getName(), t.getDuration(), t.getDescription(), t.getPriors()));
        }
    }

    private void determineTaskLevels(List<Task> tasks) {
        List<Task> tasksCopy = new ArrayList<>(tasks);
        int l = -1;

        while (!tasksCopy.isEmpty()) {
            l++;
            List<Task> toRemove = new ArrayList<>();
            BrowsTasks(tasksCopy, l, toRemove);
            tasksCopy.removeAll(toRemove);
            removeSumZeroTask(tasksCopy, toRemove);
        }
        reAddPriorTaskToResult();
    }

    private void BrowsTasks(List<Task> tasksCopy, int l, List<Task> toRemove) {
        for (Task t : tasksCopy) {
            if(t.hasNoPriors()) {
                this.taskLevels.computeIfAbsent(l, level -> new ArrayList<>()).add(t);
                toRemove.add(t);
            }
        }
    }

    private void removeSumZeroTask(List<Task> tasks, List<Task> tasksToRemove) {
        for (Task t : tasks) {
            for (Task tRemove : tasksToRemove) {
                String tRemoveName = tRemove.getName();
                t.deletePriorTaskByName(tRemoveName);
            }
        }
    }

    private void reAddPriorTaskToResult() {
        for (var entry : taskLevels.entrySet()) {
            for (var taskInResult : entry.getValue()) {
                browseInitialTaskList(taskInResult);
            }
        }
    }
    private void browseInitialTaskList(Task taskInResult) {
        for (var t : initialTaskList) {
            if(t.hasSameName(taskInResult)) {
                taskInResult.setPriors(t.getPriors());
            }
        }
    }

    private int getDateFinAuPlusTot() {
        var lastKey = taskLevels.lastKey();
        var lastValue = taskLevels.get(lastKey);
        Task highestLevelTask = lastValue.get(0);
        criticalTasks.add(highestLevelTask);
        int duree = highestLevelTask.getDuration();
        duree += getHighestLevelTaskDuration(highestLevelTask.getPriors());
        Collections.reverse(criticalTasks);
        return duree;
    }

    private int getHighestLevelTaskDuration(List<Task> tasks) {
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

        return searchInPriorTasks(correctTask);
    }

    private int searchInPriorTasks(Task correctTask) {
        if(correctTask != null) {
            criticalTasks.add(correctTask);
            if(correctTask.hasNoPriors()) {
                return correctTask.getDuration();
            } else {
                return correctTask.getDuration() + getHighestLevelTaskDuration(correctTask.getPriors());
            }
        } else {
            return 0;
        }
    }

    private Task getHighestDurationTask(List<Task> tasks) {
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

    private int getTaskLevel(Task task) {
        int lvl = 0;

        for(var entry : taskLevels.entrySet()) {
            for (var t : entry.getValue()) {
                if (t.equals(task)) {
                    lvl = entry.getKey();
                    break;
                }
            }
        }
        return lvl;
    }
}
