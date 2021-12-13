package org.helmo.plan2track.supervisers;

import org.helmo.plan2track.entities.*;


/**
 * Classe permettant de créer un montage
 */
public class ReadSuperviser implements MontageEditedEventHandler {
    private final Montage montage;
    private ReadView rView;
    private ReadView eView;

    /**
     * Initialise un ReadSuperviser avec un montage
     */
    public ReadSuperviser(Montage montage) {
        this.montage = montage;
    }

    /**
     * Permet de récupérer la vue qui visualise le montage en cours
     * @param rView Interface de la vue du montage
     */
    public void setReadView(ReadView rView) {
        this.rView = rView;
    }

    /**
     * Permet de récupérer la vue qui édite le montage en cours
     * @param eView Interface d'édition du montage
     */
    public void setEditView(ReadView eView) {
        this.eView = eView;
    }

    /**
     * Permet de vérifier si un montage est déjà en cours de création
     * @return Vrai si un montage a déjà été créé
     *          Faux sinon
     */
    public boolean checkMontageExist() {
        return this.montage.montageExist();
    }

    /**
     * Permet de vérifier qu'un montage possède des tâches
     * @return Vrai si le montage possède des tâches
     *          Faux sinon
     */
    public boolean checkMontageHasTasks() {
        return !montage.isEmpty();
    }

    /**
     * Vérifier qu'une tâche existe déjà
     * @param taskName Nom de la tâche à vérifier
     * @return Vrai si la tâche existe déjà
     *          Faux sinon
     */
    public boolean checkTaskAlreadyExist(String taskName) {
        for (var t : this.montage.getTasks()) {
            if(t.hasSameName(taskName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de modifier la données des vues lorsque le montage a été édite
     */
    @Override
    public void onMontageEdited() {
        rView.setTitle(montage.getName());
        eView.setTitle(montage.getName());
        rView.setTaskList(montage.getTasks());
        eView.setTaskList(montage.getTasks());
        rView.setEnable();
        eView.setEnable();
    }
}
