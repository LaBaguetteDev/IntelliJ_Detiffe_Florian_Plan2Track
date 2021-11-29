package org.helmo.plan2track.supervisers;

import org.helmo.plan2track.entities.Montage;
import org.helmo.plan2track.entities.MontageEditedEventHandler;
import org.helmo.plan2track.entities.Task;
import org.helmo.plan2track.view.EditView;

/**
 * Classe permettant de créer un montage
 */
public class ReadSuperviser implements MontageEditedEventHandler {
    private Montage montage;
    private ReadView rView;
    private EditView eView;

    /**
     * Initialise un ReadSuperviser avec un montage
     */
    public ReadSuperviser(Montage montage) {
        this.montage = montage;
    }

    public void setReadView(ReadView rView) {
        this.rView = rView;
    }

    public void setEditView(EditView eView) {
        this.eView = eView;
    }

    public boolean checkMontageExist() {
        return this.montage.montageExist();
    }

    public boolean checkTaskExistInMontage(Task t) {
        return montage.getTasks().contains(t);
    }

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
