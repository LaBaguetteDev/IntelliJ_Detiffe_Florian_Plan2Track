package org.helmo.plan2track.supervisers;

import org.helmo.plan2track.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe permettant de modifier le montage et les tâches
 */
public class EditSuperviser {

    private final MontageEditedEventHandler rsv;
    private Montage montage;
    private final List<Chief> teamLeaders = new ChiefFactory().getTeamLeadersList();

    public void createMontage(String name) {
        this.montage.resetMontage();
        this.montage.setName(name);
        notifyHandlers();
    }

    public boolean montageExist() {
        return this.montage.montageExist();
    }

    /**
     * Initisalise un editSuperviser avec un ReadSuperviser
     * @param rsv ReadSuperviser
     */
    public EditSuperviser(Montage montage, MontageEditedEventHandler rsv) {
        this.montage = montage;
        this.rsv = rsv;
    }

    private void notifyHandlers() {
        rsv.onMontageEdited();
    }

    public List<Task> getMontageTasks() {
        return montage.getTasks();
    }

    /***
     * Modifie le nom de la tâche
     */
    public void editName(String newName) {
        this.montage.setName(newName);
        notifyHandlers();
    }

    public void addTask(String taskName, String taskDescription, int taskDuration, List<String> antTasks) {

        List<Task> antTaskObjects = new ArrayList<>();
        for (var s : antTasks) {
            addPriorTask(antTaskObjects, s);
        }

        Task taskToAdd = new Task(taskName, taskDuration, taskDescription, antTaskObjects);

        this.montage.addTasks(taskToAdd);
        notifyHandlers();
    }

    private boolean verifyBlank(String s) {
        return s.isBlank();
    }

    private void addPriorTask(List<Task> priorsTasks, String priorTaskName) {
        if(!priorTaskName.isBlank()) {
            Task priorTask = this.montage.searchTaskByName(priorTaskName);
            if(priorTask != null) {
                priorsTasks.add(priorTask);
            }
            else {
                priorsTasks.add(new Task(priorTaskName, 1));
                this.montage.addTasks(new Task(priorTaskName, 1));
            }
        }
    }

    public int getAssociateNumber(int taskIndex) {
        Task t = this.montage.getTasks().get(taskIndex);
        int i = 0;
        for (Task task : this.montage.getTasks()) {
            if(task.checkPriorTaskExist(t.getName())) {
                i++;
            }
        }

        return i;
    }

    /***
     * Supprime une tâche du montage
     */
    public void deleteTask(int taskIndex) {
        String taskNameToDelete = this.montage.getTasks().get(taskIndex).getName();
        for (Task task : this.montage.getTasks()) {
            task.deletePriorTaskByName(taskNameToDelete);
        }
        this.montage.deleteTask(taskNameToDelete);
        notifyHandlers();
    }


    public List<String> getChiefNames() {
        List<String> chiefsNames = new ArrayList<>();
        for (var tl : this.teamLeaders) {
            chiefsNames.add(tl.getName());
        }
        return chiefsNames;
    }

    public void assignChief(int taskIndex, int chiefChoice) {
        String taskNameToAssign = this.montage.getTasks().get(taskIndex).getName();
        this.montage.searchTaskByName(taskNameToAssign).addChief(teamLeaders.get(chiefChoice));
        notifyHandlers();
    }

    private void findCriticalTasks() {
        if(this.montage.getTasks().isEmpty()) {
            System.out.println("Aucune tâche critique");
        } else {

        }
    }
}
