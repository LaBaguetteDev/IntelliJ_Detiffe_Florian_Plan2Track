package org.helmo.plan2track.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Une tache contient un nom, une éventuelle description, une durée en jours
 * des éventuelles tâches antérieurs et des éventuels chefs d'équipes
 */
public class Task {

    private final String name;
    private String description = "";
    private final int duration;
    private List<Task> priors = new ArrayList<>();
    private Chief chief = new Chief("");

    /**
     * Initialise une tâche avec un nom et une durée
     * @param name nom de la tâche
     * @param duration durée en jour de la tâche
     */
    public Task(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    /**
     * Initialise une tâche avec un nom, une durée et une ou plusieurs tâches antérieurs
     * @param name nom de la tâche
     * @param duration durée de la tâche en jours
     * @param priors tâches antérieures
     */
    public Task(String name, int duration, Task... priors) {
        this.name = name;
        this.duration = duration;
        Collections.addAll(this.priors, priors);
    }

    public Task(String name, String description, int duration, Task... priors) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        Collections.addAll(this.priors, priors);
    }

    /**
     * Initialise une tâche avec un nom, une durée, une description et une liste de tâches antérieurs
     * @param name nom de la tâche
     * @param duration durée de la tâche en jours
     * @param description description de la tâche
     * @param priors Liste de tâches antérieures
     */
    public Task(String name, int duration, String description, List<Task> priors) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.priors.addAll(priors);
    }

    /**
     * @return le nom de la tâche
     */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public String getDurationString() {
        return duration + " jours";
    }

    /**
     * @return le chef d'équipe de la tâche
     */
    public Chief getChief() {
        return chief;
    }

    public List<Task> getPriors() {
        return priors;
    }

    public void setPriors(List<Task> priors) {
        this.priors = List.copyOf(priors);
    }

    /**
     * Vérifie que la tâche possède la tâche antérieur à partir de son nom
     * @param name Nom de la tâche à vérifier
     * @return Vrai si la tâche est présente
     *          Faux sinon
     */
    public boolean checkPriorTaskExist(String name) {
        for (Task priorTask : priors) {
            if(name.equals(priorTask.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Supprime la tâche antérieure de la liste des tâches antérieure de la tâche
     * @param name Nom de la tâche à supprimer des tâches antérieures
     */
    public void deletePriorTaskByName(String name) {
        priors.removeIf(priorTask -> priorTask.getName().equals(name));
    }

    /**
     * Ajoute un chef d'équipe à la tâche
     * @param chief Chef d'équipe à ajouter
     */
    public void addChief(Chief chief) {
        this.chief = chief;
    }
}
