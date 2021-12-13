package org.helmo.plan2track.entities;

/**
 * Chef d'équipe d'une tâche
 */
public class Chief {
    private final String name;

    /**
     * Initialise un chef avec son nom
     * @param name nom du chef d'équipe
     */
    public Chief(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Vérifie que le chef d'équipe existe (qu'il a un nom)
     * @return Vrai si le chef d'équipe existe
     *          Faux sinon
     */
    public boolean exist() {
        return !name.isBlank();
    }
}
