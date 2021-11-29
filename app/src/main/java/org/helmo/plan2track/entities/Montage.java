package org.helmo.plan2track.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Un montage contient un nom et une liste de tache
 */
public class Montage {

    private String name;
    private final List<Task> tasks = new ArrayList<Task>();
    private boolean exist = false;

    /**
     * Initialise un montage avec un nom
     * @param name nom du montage
     */
    public Montage(String name) {
        this.name = name;
    }

    /**
     * @return le nom du montage
     */
    public String getName() {
        return name;
    }

    /**
     * @return la liste des taches du montage
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Modifie le nom du montage
     * @param name Nom à modifier
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ajoute des tâches au montage
     * @param tasks tâches à ajouter
     */
    public void addTasks(Task... tasks) {
        Collections.addAll(this.tasks, tasks);
    }

    /**
     * Affiche le montage ainsi que ses tâches
     */
    public String printMontage() {
        String montageFormat;
        if(!this.name.equals("null")) {
            montageFormat = "\t" + this.name + "\n";
            if(tasks.isEmpty()) {
                montageFormat += "\t" + "(Aucune tâche)" + "\n";
            } else {
                montageFormat += printTasks();
            }
        } else {
            montageFormat = "Aucun montage a été créé";
        }

        return montageFormat;
    }

    private String printTasks() {
        StringBuilder montageFB = new StringBuilder();
        for (Task task : this.tasks) {
            montageFB.append(task.printTask());
        }
        return montageFB.toString();
    }

    /**
     * Recherche une tâche en fonction de son nom
     * @param name nom de la tâche à rechercher
     * @return la tâche qui correspond au nom entré en paramètre
     *          Si la tâche n'a pas été trouvée, la méthode renvoie null
     */
    public Task searchTaskByName(String name) {
        for (Task task : this.tasks) {
            String currentTaskName = task.getName();
            if(name.equals(currentTaskName)) {
                return task;
            }
        }
        return null;
    }

    /**
     * Vérifie que le nom de la tâche entré en
     * paramètre est présent dans la liste des tâches
     * @param name nom de la tâche à rechercher
     * @return Vrai si la tâche est présente
     *          Faux sinon
     */
    public boolean checkTaskExist(String name) {
        Task t = searchTaskByName(name);
        return t != null;
    }

    /**
     * Supprime une tâche dans la liste des tâches
     * @param name nom de la tâche à supprimer
     */
    public void deleteTask(String name) {
        Task taskToDelete = searchTaskByName(name);
        this.tasks.remove(taskToDelete);
    }

    public void resetMontage() {
        this.tasks.clear();
        this.exist = true;
    }

    public boolean montageExist() {
        return !this.exist;
    }

}
