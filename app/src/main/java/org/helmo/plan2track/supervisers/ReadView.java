package org.helmo.plan2track.supervisers;

import org.helmo.plan2track.entities.Task;

import java.util.List;

/**
 * Interface permettant de mettre à jour les données du
 * montage lorsque celles-ci ont été modifiées
 */
public interface ReadView {

    /**
     * Permet de mettre à jour le nom du montage
     * @param montageName Nouveau nom du montage
     */
    void setTitle(String montageName);

    /**
     * Permet de mettre à jour les tâches du montage
     * @param tasks Nouvelles tâches du montage
     */
    void setTaskList(List<Task> tasks);

    /**
     * Permet d'indiquer à la vue qu'elle peut
     * activer les onglets d'édition et de visualisation
     * du montage
     */
    void setEnable();
}
