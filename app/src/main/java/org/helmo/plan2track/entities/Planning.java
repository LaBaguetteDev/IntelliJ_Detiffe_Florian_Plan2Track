package org.helmo.plan2track.entities;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Classe permettant de calculer le planning d'un montage
 *
 * Choic de collection :
 * Interface :
 * J'ai choisi une Map car j'ai besoin d'associer une tâche à une date de début.
 * Une interface de type clé - valeur m'a donc semblé évident pour cela. La Map contient
 * donc comme clé la tâche et comme valeur sa date de début.
 *
 * Implémentation :
 * J'ai choisi une HashMap, car à choisir entre une HashMap et une TreeMap,
 * je n'ai pas besoin que les tâches soient triées par la TreeMap. D'autant plus que
 * la TreeMap a la plupart du temps une CTT moins performantes qu'une HashMap.
 * Les méthodes principales get, put et remove ont la plupart du temps une CTT constante
 * dans une HashMap, tant dis que dans une TreeMap, ces méthodes ont une CTT logarithmique,
 * ce qui peut potentiellement être moins performant.
 */
public class Planning {

    private final Date startDate;
    private final Map<Task, Date> planning = new HashMap<>();

    /**
     * Initialise un planning avec un montage et une date de début
     * @param montage Montage dont on veut le planning
     * @param startDate date de début du planning
     */
    public Planning(Montage montage, Date startDate) {
        this.startDate = startDate;
        generatePlanning(montage.getTasks());
    }

    /**
     * Permet d'obtenir le planning obtenu sous forme de map,
     * avec une tâche correspondant à une date de débit
     * @return Le planning du montage
     */
    public Map<Task, Date> getPlanning() {
        return planning;
    }


    private void generatePlanning(List<Task> taskList) {
        for (var t : taskList) {
            calculateTaskDate(t);
        }
    }

    private void calculateTaskDate(Task t) {
        if(t.hasNoPriors()) {
            planning.put(t, startDate);
        } else {
            for (var tAnt : t.getPriors()) {
                if (planning.containsKey(tAnt)) {
                    Date tAntDate = planning.get(tAnt);
                    int tAntDuration = tAnt.getDuration();
                    var taskStart = new Date(tAntDate.getTime() + TimeUnit.DAYS.toMillis(tAntDuration));
                    planning.put(t, taskStart);
                }
            }
        }
    }
}
