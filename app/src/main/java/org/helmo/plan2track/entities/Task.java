package org.helmo.plan2track.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Une tache contient un nom, une éventuelle description, une durée en jours
 * des éventuelles tâches antérieurs et des éventuels chefs d'équipes
 */
public class Task {

    private final String name;
    private String description = "";
    private final int duration;
    private final List<Task> priors = new ArrayList<>();
    private Chief chief = new Chief("", "");

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

    /**
     * @return La description de la tâche
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return La durée de la tâche
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return La durée de la tâche avec la mention "jours"
     */
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

    /**
     * Permet d'assigner des tâches antérieurs à la tâche
     * @param priors Tâches antérieures à assigner
     */
    public void setPriors(List<Task> priors) {
        this.priors.addAll(priors);
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

    /**
     * Vérifie que la tâche a été assignée à un chef d'équipe
     * @return Vrai si la tâche a été assignée
     *          Faux sinon
     */
    public boolean hasBeenAssigned() {
        return chief.exist();
    }

    /**
     * Vérifie que la tâche possède des tâches antérieurs
     * @return Vrai si la tâche ne possède pas de tâche antérieures
     *          Faux si la tâche possède des tâches antérieures
     */
    public boolean hasNoPriors() {
        return this.priors.isEmpty();
    }

    /**
     * Vérifie qu'une tâche possède le même nom qu'une autre tâche
     * @param t Tâche que l'on veut vérifier
     * @return Vrai si la tâche possède le même nom
     *          Faux sinon
     */
    public boolean hasSameName(Task t) {
        return this.name.equals(t.getName());
    }

    /**
     * Vérifie qu'une tâche possède le nom entré en paramètre
     * @param tName Nom de tâche à vérifier
     * @return Vrai si la tâche possède le nom
     *          Faux sinon
     */
    public boolean hasSameName(String tName) {
        return this.name.equals(tName);
    }
}
