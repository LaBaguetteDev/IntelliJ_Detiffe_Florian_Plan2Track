package org.helmo.plan2track.supervisers;

import org.helmo.plan2track.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de modifier le montage et les tâches
 */
public class EditSuperviser {

    private final MontageEditedEventHandler rsv;
    private final Montage montage;
    private final List<Chief> teamLeaders = new ChiefFactory().getTeamLeadersList();

    /**
     * Initisalise un editSuperviser avec un ReadSuperviser
     * @param rsv ReadSuperviser
     */
    public EditSuperviser(Montage montage, MontageEditedEventHandler rsv) {
        this.montage = montage;
        this.rsv = rsv;
    }

    /**
     * Méthode permettant de créer un montage
     * @param name Nom du montage
     */
    public void createMontage(String name) {
        this.montage.resetMontage();
        this.montage.setName(name);
        notifyHandlers();
    }

    private void notifyHandlers() {
        rsv.onMontageEdited();
    }

    /**
     * Vérifie si un montage existe déjà ou non
     * @return
     */
    public boolean montageExist() {
        return this.montage.montageExist();
    }

    /***
     * Modifie le nom de la tâche
     */
    public void editName(String newName) {
        this.montage.setName(newName);
        notifyHandlers();
    }

    /**
     * Permet d'ajouter une tâche au montage
     * @param taskName Nom de la tâche
     * @param taskDescription Description de la tâche
     * @param taskDuration Durée de la tâche
     * @param antTasks Tâches antérieures de la tâche
     */
    public void addTask(String taskName, String taskDescription, int taskDuration, List<String> antTasks) {
        List<Task> antTaskObjects = new ArrayList<>();
        for (var s : antTasks) {
            addPriorTask(antTaskObjects, s);
        }

        Task taskToAdd = new Task(taskName, taskDuration, taskDescription, antTaskObjects);

        this.montage.addTasks(taskToAdd);
        notifyHandlers();
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

    /**
     * Permet de connaître le nombre de tâches associées à une tâche
     * @param taskIndex Index de la tâche dont on veut savoir le nombre de tâches associées
     * @return Le nombre de tâche associées à une tâche
     */
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
        this.montage.deleteTask(taskNameToDelete);
        notifyHandlers();
    }


    /**
     * Permet d'obtenir le nom de tous les chefs d'équipe
     * @return La liste des chefs d'équipe
     */
    public List<String> getChiefNames() {
        List<String> chiefsNames = new ArrayList<>();
        for (var tl : this.teamLeaders) {
            chiefsNames.add(tl.getName());
        }
        return chiefsNames;
    }

    /**
     * Permet d'associer une tâche à un chef d'équipe
     * @param taskIndex Index de la tâche à associer
     * @param chiefChoice Index du chef d'équipe à assigner
     */
    public void assignChief(int taskIndex, int chiefChoice) {
        String taskNameToAssign = this.montage.getTasks().get(taskIndex).getName();
        this.montage.searchTaskByName(taskNameToAssign).addChief(teamLeaders.get(chiefChoice));
        notifyHandlers();
    }
}
