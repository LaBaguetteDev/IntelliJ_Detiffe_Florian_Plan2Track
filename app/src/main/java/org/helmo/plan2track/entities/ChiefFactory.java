package org.helmo.plan2track.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe contenant la liste des chefs d'équipes connus
 */
public class ChiefFactory {

    private final List<Chief> teamLeaders = new ArrayList<>();

    /**
     * Initialise la liste des chefs d'équipe
     */
    public ChiefFactory() {
        initChiefFactory();
    }

    private void initChiefFactory() {
        Chief hendrikx = new Chief("Nicolas Hendrikx");
        Chief jadot = new Chief("Jean Jadot");
        Chief ludewig = new Chief("Francois Ludewig");

        this.teamLeaders.add(hendrikx);
        this.teamLeaders.add(jadot);
        this.teamLeaders.add(ludewig);
    }

    /**
     * Permet de récupérer la liste des chefs d'équipes
     * @return la liste des chefs d'équipes connus
     */
    public List<Chief> getTeamLeadersList() {
        return this.teamLeaders;
    }
}
