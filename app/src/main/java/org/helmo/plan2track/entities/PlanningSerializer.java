package org.helmo.plan2track.entities;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Classe permettant d'écrire les données d'un planning dans un fichier
 * JSON à destination de l'application Tracker
 */
public class PlanningSerializer {

    /**
     * Ecrit les données du planning dans un fichier JSON
     * @param planning  Planning du montage à sauvegarder
     * @param montage   Montage à sauvegarder
     * @return  Le chemin du fichier
     */
    public static String writeToJson(Planning planning, Montage montage) {
        JsonArray planningJson = new JsonArray();
        addToJsonArray(planning.getPlanning(), planningJson, montage);
        return writeFile(planningJson);
    }

    private static void addToJsonArray(Map<Task, Date> pMap, JsonArray planning, Montage montage) {
        var df = new SimpleDateFormat("yyyy-MM-dd");
        for (var entry : pMap.entrySet()) {
            JsonObject task = new JsonObject();
            JsonObject t = new JsonObject();
            var key = entry.getKey();
            var dateDebut = entry.getValue();
            Date dateFin = getDateFin(key, dateDebut);
            var chief = key.getChief();

            initJsonObject(montage, df, t, key, dateDebut, dateFin, chief);
            task.put("task", t);
            planning.add(task);
        }
    }

    private static Date getDateFin(Task key, Date dateDebut) {
        Calendar c = Calendar.getInstance(); //TODO Demeter ?
        c.setTime(dateDebut);
        c.add(Calendar.DATE, key.getDuration());
        return c.getTime();
    }

    private static void initJsonObject(Montage montage, SimpleDateFormat df, JsonObject t, Task key, Date dateDebut, Date dateFin, Chief chief) { //TODO LongParameterList ?
        t.put("Chantier", montage.getName());
        t.put("Description", key.getName());
        t.put("DateDebut", df.format(dateDebut) + "T00:00:00");
        t.put("DateFin", df.format(dateFin) + "T00:00:00");
        t.put("Statut", 0);
        t.put("DateDebutEffectif", "0001-01-01T00:00:00");
        t.put("DateFinEffectif", "0001-01-01T00:00:00");
        t.put("UserCode", chief.getCode());
    }

    private static String writeFile(JsonArray planning) {
        try {
            File file = new File("C:\\temp\\Plan2Track\\" + "planning.json");
            file.getParentFile().mkdirs(); //TODO Demeter ?
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(planning.toJson());
            fileWriter.flush();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
