package org.helmo.plan2track.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.helmo.plan2track.entities.Task;
import org.helmo.plan2track.supervisers.PlanningSuperviser;
import org.helmo.plan2track.supervisers.ReadSuperviser;
import org.helmo.plan2track.supervisers.ReadView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe permettant d'obtenir la vue de visualisation du montage
 */
public class MontageTab implements ReadView {

    private final Tab mt = new Tab("Voir montage");
    private final ReadSuperviser rsv;
    private final PlanningSuperviser psv;

    private final VBox vBox = new VBox(); {
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(5);
    }
    private final HBox firstLineHBox = new HBox(); {
        firstLineHBox.setPadding(new Insets(10));
        firstLineHBox.setSpacing(5);
        firstLineHBox.setAlignment(Pos.BOTTOM_CENTER);
    }
    private final VBox secondLineHbox = new VBox(); {
        secondLineHbox.setPadding(new Insets(10));
        secondLineHbox.setSpacing(5);
        secondLineHbox.setAlignment(Pos.BOTTOM_CENTER);
    }
    private final TableView<Task> tv = new TableView<>();
    private final TableColumn<Task, String> tcName = new TableColumn<>("Tâche"); {
        tcName.prefWidthProperty().bind(tv.widthProperty().multiply(0.2));
        tcName.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getName()));
    }
    private final TableColumn<Task, String> tcDescription = new TableColumn<>("Description"); {
        tcDescription.prefWidthProperty().bind(tv.widthProperty().multiply(0.2));
        tcDescription.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDescription()));
    }
    private final TableColumn<Task, String> tcDuree = new TableColumn<>("Durée"); {
        tcDuree.prefWidthProperty().bind(tv.widthProperty().multiply(0.1));
        tcDuree.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDurationString()));
    }
    private final TableColumn<Task, String> tcAnt = new TableColumn<>("Tâches antérieures"); {
        tcAnt.prefWidthProperty().bind(tv.widthProperty().multiply(0.3));
        tcAnt.setCellValueFactory((TableColumn.CellDataFeatures<Task, String> p) -> {
            List<Task> antTasks = p.getValue().getPriors();
            String val = antTasks.stream()
                    .map(Task::getName)
                    .collect(Collectors.joining(", "));
            return new ReadOnlyStringWrapper(val);
        });
    }
    private final TableColumn<Task, String> tcChief = new TableColumn<>("Chef d'équipe"); {
        tcChief.prefWidthProperty().bind(tv.widthProperty().multiply(0.2));
        tcChief.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getChief().getName()));
    }
    private final Label montageNameLb = new Label(); {
        montageNameLb.setFont(new Font("Segoe UI", 20));
    }
    private final Button calculateCTBtn = new Button("Calculer la date au plus tôt et les tâches critiques"); {
        setCriticalTasksBtnOnAction();
    }
    private final Button calculatePBtn = new Button("Calculer le planning"); {
        setPlanningBtnOnAction();
    }
    private final Label planningLabel = new Label();
    private String dateInput;
    private final Button publishToJson = new Button("Publier le planning"); {
        publishBtnOnAction();
        publishToJson.setDisable(true);
    }

    /**
     * Initialise l'onglet de visualisation du montage
     * @param rsv Superviser de visualisation du montage
     * @param psv Superviser de gestion du planning du montage
     */
    public MontageTab(ReadSuperviser rsv, PlanningSuperviser psv) {
        this.rsv = rsv;
        this.psv = psv;
        setContent();
    }
    private void setCriticalTasksBtnOnAction() {
        calculateCTBtn.setOnAction(e -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Tâches critiques et fin au plus tôt");
            a.setHeaderText("");
            a.setContentText(psv.getCriticalAndEarliestDateString());
            a.show();
        });
    }
    private void setPlanningBtnOnAction() {
        calculatePBtn.setOnAction(e -> {
            if(psv.checkAllTasksHaveChief() && rsv.checkMontageHasTasks()) {
                settingPlanning();
            } else {
                errorAlert();
            }
        });
    }
    private void settingPlanning() {
        TextInputDialog tid = new TextInputDialog("");
        tid.setHeaderText("Veuillez entrer la date de début du planning");
        tid.setContentText("Format de la date à entrer : dd/mm/yyyy");
        Optional<String> result = tid.showAndWait();
        dateInput = result.get(); //TODO GERER LE BOUTON CANCEL
        try {
            planningLabel.setText(psv.getPlanningString(new SimpleDateFormat("dd/MM/yyyy").parse(dateInput)));
            publishToJson.setDisable(false);
        } catch (ParseException ex) {
            WrongDateFormatAlert();
        }
    }
    private void errorAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erreur de planning");
        a.setHeaderText("");
        a.setContentText("Vous n'avez pas encore de tâches ou toutes vos tâches n'ont pas été assignées à un chef d'équipe");
        a.showAndWait();
    }
    private void WrongDateFormatAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Format de date inccorect");
        a.setHeaderText("");
        a.setContentText("La date encodée ne correspond pas au format indiqué");
        a.showAndWait();
    }
    private void publishBtnOnAction() {
        publishToJson.setOnAction(e -> {
            try {
                String path = psv.writeToJson(new SimpleDateFormat("dd/MM/yyyy").parse(dateInput));
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Fichier JSON créé");
                a.setHeaderText("");
                a.setContentText("Le fichier " + montageNameLb.getText() + ".json a bien été créé à l'emplacement " + path);
                a.show();
            } catch (ParseException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur de création de fichier");
                a.setHeaderText("");
                a.setContentText("Le fichier json n'a pas pu être créé, erreur : " + ex.getMessage());
            }
        });
    }

    /**
     * Permet d'obtenir l'onglet de visualisation du montage
     * @return L'onglet de visualisation du montage
     */
    public Tab getMontageViewTab() {
        tv.getColumns().addAll(tcName, tcDescription, tcDuree, tcAnt, tcChief);
        firstLineHBox.getChildren().addAll(calculateCTBtn, calculatePBtn, publishToJson);
        secondLineHbox.getChildren().addAll(planningLabel);

        vBox.getChildren().addAll(montageNameLb, tv, firstLineHBox, secondLineHbox);

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
        planningLabel.setText("");
        publishToJson.setDisable(true);
    }

    private void setContent() {
        rsv.setReadView(this);
    }

    @Override
    public void setEnable() {
        mt.setDisable(false);
    }

}
