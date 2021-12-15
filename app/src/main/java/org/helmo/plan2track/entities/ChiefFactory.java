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
        Chief hendrikx = new Chief("Nicolas Hendrikx", "H2G2");
        Chief jadot = new Chief("Jean Jadot", "J003");
        Chief ludewig = new Chief("Francois Ludewig", "F004");
        Chief dani = new Chief("Dani Bond", "D007");
        Chief greta = new Chief("Greta Thumberg", "G021");
        Chief gerard = new Chief("Gerard Muller", "F004");
        Chief simon = new Chief("Simon Lamy", "S010");

        this.teamLeaders.addAll(List.of(hendrikx, jadot, ludewig, dani, greta, gerard, simon));
    }

    /**
     * Permet de récupérer la liste des chefs d'équipes
     * @return la liste des chefs d'équipes connus
     */
    public List<Chief> getTeamLeadersList() {
        return this.teamLeaders;
    }
}
