package org.helmo.plan2track.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.helmo.plan2track.entities.Task;
import org.helmo.plan2track.supervisers.ReadSuperviser;
import org.helmo.plan2track.supervisers.ReadView;

import java.util.List;
import java.util.stream.Collectors;

public class MontageTab implements ReadView {

    private final Tab mt = new Tab("Voir montage");

    private final ReadSuperviser rsv;

    VBox vBox = new VBox(); {
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(5);
    }

    TableView<Task> tv = new TableView<>();
    TableColumn<Task, String> tcName = new TableColumn<>("Tâche"); {
        tcName.prefWidthProperty().bind(tv.widthProperty().multiply(0.2));
        tcName.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getName()));
    }
    TableColumn<Task, String> tcDescription = new TableColumn<>("Description"); {
        tcDescription.prefWidthProperty().bind(tv.widthProperty().multiply(0.2));
        tcDescription.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDescription()));
    }
    TableColumn<Task, String> tcDuree = new TableColumn<>("Durée"); {
        tcDuree.prefWidthProperty().bind(tv.widthProperty().multiply(0.1));
        tcDuree.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDurationString()));
    }

    TableColumn<Task, String> tcAnt = new TableColumn<>("Tâches antérieures"); {
        tcAnt.prefWidthProperty().bind(tv.widthProperty().multiply(0.3));
        tcAnt.setCellValueFactory((TableColumn.CellDataFeatures<Task, String> p) -> {
            List<Task> antTasks = p.getValue().getPriors();
            String val = antTasks.stream()
                    .map(Task::getName)
                    .collect(Collectors.joining(", "));
            return new ReadOnlyStringWrapper(val);
        });
    }

    TableColumn<Task, String> tcChief = new TableColumn<>("Chef d'équipe"); {
        tcChief.prefWidthProperty().bind(tv.widthProperty().multiply(0.2));
        tcChief.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getChief().getName()));
    }


    Label montageNameLb = new Label(); {
        montageNameLb.setFont(new Font("Segoe UI", 20));
    }

    Button calculateCriticalTasksBtn = new Button("Calculer la date au plus tôt et les tâches critiques"); {
        setBtnOnAction();
    }

    Label criticalTasklabel = new Label("Test");

    private void setBtnOnAction() {
        calculateCriticalTasksBtn.setOnAction(e -> {
            criticalTasklabel.setText(rsv.getCriticalTasksString());
        });
    }

    public MontageTab(ReadSuperviser rsv) {
        this.rsv = rsv;
        setContent();
    }

    public Tab getMontageViewTab() {
        tv.getColumns().addAll(tcName, tcDescription, tcDuree, tcAnt, tcChief);
        vBox.getChildren().addAll(montageNameLb, tv, calculateCriticalTasksBtn, criticalTasklabel);

        mt.setContent(vBox);
        mt.setClosable(false);
        if(rsv.checkMontageExist()) {
            mt.setDisable(true);
        }

        return mt;
    }

    @Override
    public void setTitle(String montageName) {
        montageNameLb.setText(montageName);
    }

    @Override
    public void setTaskList(List<Task> tasks) {
        tv.setItems(FXCollections.observableList(tasks));
        tv.refresh();
    }

    private void setContent() {
        rsv.setReadView(this);
    }

    @Override
    public void setEnable() {
        mt.setDisable(false);
    }

    @Override
    public void setCriticalTasksString(String label) {

    }

    @Override
    public void setDateAuPlusTotString(String label) {

    }
}
