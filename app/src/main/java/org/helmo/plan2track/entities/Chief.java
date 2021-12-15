package org.helmo.plan2track.entities;

/**
 * Chef d'équipe d'une tâche
 */
public class Chief {
    private final String name;
    private final String code;

    /**
     * Initialise un chef avec son nom
     * @param name nom du chef d'équipe
     */
    public Chief(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Renvoie le nom du chef d'équipe
     * @return Le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie le code du chef d'équipe
     * @return Le code
     */
    public String getCode() {
        return code;
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
