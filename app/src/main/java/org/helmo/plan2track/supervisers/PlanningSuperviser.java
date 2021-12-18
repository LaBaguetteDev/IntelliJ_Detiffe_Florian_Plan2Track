package org.helmo.plan2track.supervisers;

import org.helmo.plan2track.entities.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Classe permettant de gérer le planning d'un montage
 */
public class PlanningSuperviser {

    private final Montage montage;

    /**
     * Initialise un PlanningSuperviser avec un montage
     * @param montage Montage de l'utilisateur
     */
    public PlanningSuperviser(Montage montage) {
        this.montage = montage;
    }

    /**
     * Permet de vérifier que toutes les tâches d'un montage
     * ont un chef d'équipe
     * @return Vrai si toutes les tâches ont un chef d'équipe,
     *          Faux sinon
     */
    public boolean checkAllTasksHaveChief() {
        for (var t : montage.getTasks()) {
            if(!t.hasBeenAssigned()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Permet d'obtenir le label contenant les tâches critique et la date au
     * plus tôt d'un montage
     * @return Le label contenant les statistiques
     */
    public String getCriticalAndEarliestDateString() {
        StringBuilder rs = new StringBuilder();
        if(!this.montage.isEmpty()) {
            var ct = new CriticalTasks(montage);
            getCriticalTasksStrings(rs, ct);
            rs.append("\nDate de fin au plus tôt : ").append(ct.getEarliestEndDate()).append(" jours après la date de début");
            return rs.toString();
        } else {
            return "Aucune tâche critique";
        }
    }
    private void getCriticalTasksStrings(StringBuilder rs, CriticalTasks ct) {
        List<Task> criticalTasks = ct.getCriticalTasks();
        rs.append("Tâches critiques : \n");
        for (int i = 0; i < criticalTasks.size(); i++) {
            var t = criticalTasks.get(i);
            if(criticalTasks.size() > i+1) {
                var tNext = criticalTasks.get(i + 1);
                rs.append(t.getName()).append(", requise pour ").append(tNext.getName()).append("\n");
            } else {
                rs.append(t.getName()).append("\n");
            }
        }
    }

    /**
     * Permet d'obtenir le label du planning du montage, associant chaque tâche
     * à une date de début
     * @param startingDate Date de début du planning
     * @return Le label du planning du montage
     */
    public String getPlanningString(Date startingDate) {
        StringBuilder rs = new StringBuilder();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Planning p = new Planning(montage, startingDate);
        var pMap = p.getPlanning();
        for (var entry : pMap.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            var chief = key.getChief();
            rs.append(key.getName()).append(" - ").append(df.format(value)).append(" - ").append(chief.getName()).append("\n");
        }

        return rs.toString();
    }

    /**
     * Permet d'écrire sur un fichier JSON le planning du montage
     * @param startingDate Date de début du planning
     */
    public String writeToJson(Date startingDate) {
        return PlanningSerializer.writeToJson(new Planning(montage, startingDate), montage);
    }
}
