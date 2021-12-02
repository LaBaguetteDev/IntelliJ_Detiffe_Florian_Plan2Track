package org.helmo.plan2track.supervisers;

import org.helmo.plan2track.entities.Task;

import java.util.List;

public interface ReadView {

    void setTitle(String montageName);

    void setTaskList(List<Task> tasks);

    void setEnable();

    void setCriticalTasksString(String label);

    void setDateAuPlusTotString(String label);
}
