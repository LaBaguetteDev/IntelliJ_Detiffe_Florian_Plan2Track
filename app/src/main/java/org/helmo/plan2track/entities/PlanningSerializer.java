package org.helmo.plan2track.entities;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class PlanningSerializer {

    public static String writeToJson(Planning planning, Montage montage) {
        JsonArray planningJson = new JsonArray();
        addToJsonArray(new SimpleDateFormat("yyyy-MM-dd"), planning.getPlanning(), planningJson, montage);
        return writeFile(planningJson);
    }

    private static void addToJsonArray(DateFormat df, Map<Task, Date> pMap, JsonArray planning, Montage montage) {
        for (var entry : pMap.entrySet()) {
            JsonObject task = new JsonObject();
            JsonObject t = new JsonObject();

            var key = entry.getKey();

            var dateDebut = entry.getValue(); Calendar c = Calendar.getInstance(); c.setTime(dateDebut);

            c.add(Calendar.DATE, key.getDuration());
            var dateFin = c.getTime();

            var chief = key.getChief();
            t.put("Chantier", montage.getName());
            t.put("Description", key.getName());
            t.put("DateDebut", df.format(dateDebut) + "T00:00:00");
            t.put("DateFin", df.format(dateFin) + "T00:00:00");
            t.put("Statut", 0);
            t.put("DateDebutEffectif", "0001-01-01T00:00:00");
            t.put("DateFinEffectif", "0001-01-01T00:00:00");
            t.put("UserCode", chief.getCode());

            task.put("task", t);

            planning.add(task);
        }
    }

    private static String writeFile(JsonArray planning) {
        try {
            File file = new File("planning.json");
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
