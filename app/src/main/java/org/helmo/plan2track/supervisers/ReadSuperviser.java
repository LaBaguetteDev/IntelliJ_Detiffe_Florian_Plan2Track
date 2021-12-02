package org.helmo.plan2track.supervisers;

import org.helmo.plan2track.entities.CriticalTasks;
import org.helmo.plan2track.entities.Montage;
import org.helmo.plan2track.entities.MontageEditedEventHandler;
import org.helmo.plan2track.entities.Task;
import org.helmo.plan2track.view.EditTab;

import java.util.List;

/**
 * Classe permettant de créer un montage
 */
public class ReadSuperviser implements MontageEditedEventHandler {
    private final Montage montage;
    private ReadView rView;
    private EditTab eView;
    private CriticalTasks ct;

    /**
     * Initialise un ReadSuperviser avec un montage
     */
    public ReadSuperviser(Montage montage) {
        this.montage = montage;
    }

    public void setReadView(ReadView rView) {
        this.rView = rView;
    }

    public void setEditView(EditTab eView) {
        this.eView = eView;
    }

    public boolean checkMontageExist() {
        return this.montage.montageExist();
    }

    public String getCriticalTasksString() {
        String rs = "";
        if(!montage.getTasks().isEmpty()) {
            ct = new CriticalTasks(montage);
            List<Task> criticalTasks = ct.getCriticalTasks();
            for (int i = 0; i < criticalTasks.size(); i++) {
                if(criticalTasks.size() > i+1) {
                    rs += criticalTasks.get(i).getName() + ", requise pour " + criticalTasks.get(i+1).getName() + "\n";
                } else {
                    rs += criticalTasks.get(i).getName() + "\n";
                }
            }

            rs += "Date de fin au plus tôt = " + ct.getEarliestEndDate() + " jours après la date de début";

            return rs;
        } else {
            return "Aucune tâche critique";
        }
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
