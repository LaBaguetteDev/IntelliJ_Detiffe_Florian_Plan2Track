package org.helmo.plan2track.entities;

/**
 * Interface permettant de notifier que le montage a été modifié
 */
public interface MontageEditedEventHandler {

    /**
     * Méthode appelée lorsque le montage a été modifié
     */
    void onMontageEdited();
}
