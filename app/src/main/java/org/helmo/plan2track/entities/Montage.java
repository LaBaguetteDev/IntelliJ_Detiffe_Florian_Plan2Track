package org.helmo.plan2track.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Un montage contient un nom et une liste de tache
 *
 * Choix de collection :
 * Interface :
 * J'ai choisi une List car je dois pouvoir ajouter facilement des tâches et les garder dans un certain
 * ordre pour que je puisse accéder à une tâche via son index, ce qui m'est utile pour calculer les tâches critiques,
 * la date de fin au plus tôt du montage, ainsi que pour obtenir le planning. De plus, je n'ai pas besoin d'avoir une
 * collection triée.
 *
 * Implémentation :
 * J'ai choisi une ArrayList car les méthodes sont généralement plus performantes qu'une LinkedList
 * par exemple avec une ctt de O(1) dans la plupart des cas. Elle implémente toutes les méthodes de l'interface List
 * et sa capacité correspond à la taille du tableau de stockage des éléments, ce qui veut dire qu'elle est automatiement
 * ajustée lors de l'ajout ou de la suppression d'un élément afin d'éviter d'allouer trop de mémoire inutile.
 */
public class Montage {

    private String name;
    private final List<Task> tasks = new ArrayList<>();
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
     * Recherche une tâche en fonction de son nom
     * @param name nom de la tâche à rechercher
     * @return la tâche qui correspond au nom entré en paramètre
     *          Si la tâche n'a pas été trouvée, la méthode renvoie null
     */
    public Task searchTaskByName(String name) {
        for (Task task : tasks) {
            String currentTaskName = task.getName();
            if(name.equals(currentTaskName)) {
                return task;
            }
        }
        return null;
    }

    /**
     * Supprime une tâche dans la liste des tâches
     * CTT de cette méthode :
     * La méthode contient une première boucle :
     *      Pour chaque tâche du montage, la méthode removeIf de l'arrayList est appelée,
     *      cette méthode a une ctt linéaire car elle va parcourir tous ses éléments jusqu'à
     *      ce que la condition soit respectée.
     * --> La première boucle a une ctt de O(n²) car on a une ctt linéaire au sein d'une boucle
     *
     * La méthode contient un appel à searchTaskByName qui va parcourir toutes les tâches du montage
     * jusqu'à tomber sur la tâche qui contient le nom entré en paramètre. Cette méthode appelée
     * ne contient qu'une seule boucle qui parcours toutes les tâches du montage.
     * --> La deuxième boucle a une ctt de O(n)
     *
     * Etant donné que la ctt correspond au pire des cas, cette méthode possède donc une complexité
     * maximale quadratique, c'est à dire en O(n²)
     * @param name nom de la tâche à supprimer
     */
    public void deleteTask(String name) {
        for (Task task : tasks) {
            task.deletePriorTaskByName(name);
        }
        Task taskToDelete = searchTaskByName(name);
        this.tasks.remove(taskToDelete);
    }

    /**
     * Permet de remettre à zéro un montage
     * en supprimant sa liste de tâche mais en indiquant que
     * le montage est existant
     */
    public void resetMontage() {
        this.tasks.clear();
        this.exist = true;
    }

    /**
     * Permet de vérifier que le montage est existant
     * @return Vrai si il existe, faux sinon
     */
    public boolean montageExist() {
        return !this.exist;
    }

    /**
     * Vérifie que le montage possède des tâches
     * @return Vrai si il est vide
     *          Faux sinon
     */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }
}
