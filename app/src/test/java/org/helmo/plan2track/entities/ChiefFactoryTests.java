package org.helmo.plan2track.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChiefFactoryTests {

    @Test
    void returnDefaultTeamLeaders() {
        Chief hendrikx = new Chief("Nicolas Hendrikx", "H2G2");
        Chief jadot = new Chief("Jean Jadot", "J003");
        Chief ludewig = new Chief("Francois Ludewig", "F004");
        Chief dani = new Chief("Dani Bond", "D007");
        Chief greta = new Chief("Greta Thumberg", "G021");
        Chief gerard = new Chief("Gerard Muller", "F004");
        Chief simon = new Chief("Simon Lamy", "S010");
        List<Chief> expected = List.of(hendrikx, jadot, ludewig, dani, greta, gerard, simon);

        ChiefFactory cf = new ChiefFactory();
        List<Chief> result = cf.getTeamLeadersList();

        assertEquals(result.size(), expected.size());
    }
}
