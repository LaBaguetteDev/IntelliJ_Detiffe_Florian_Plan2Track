package org.helmo.plan2track.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.helmo.plan2track.entities.Task;
import org.helmo.plan2track.supervisers.EditSuperviser;
import org.helmo.plan2track.supervisers.ReadSuperviser;
import org.helmo.plan2track.supervisers.ReadView;

import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Classe permettant d'obtenir la vue d'édition d'un montage
 */
public class EditTab implements ReadView {

    private final EditSuperviser esv;
    private final ReadSuperviser rsv;

    private final GridPane gp = new GridPane();

    // Modifier le nom du montage
    private final Label montageNameTitle = new Label("Modifier le nom du montage"); {
        montageNameTitle.setFont(H1_FONT);
    }
    private final Label montageNameLabel = new Label("Nom du montage : ");
    private final TextField montageNameField = new TextField();
    private final Button montageNameBtn = new Button("Modifier"); {
        renamingMontageName();
    }

    // Ajouter une nouvelle tâche
    private final Label addTaskTitle = new Label("Ajouter une nouvelle tâche"); {
        addTaskTitle.setFont(H1_FONT);
    }
    private final Label taskNameLabel = new Label("Nom : ");
    private final TextField taskNameField = new TextField();
    private final Label taskDescLabel = new Label("Descriptif :");
    private final TextField taskDescField = new TextField();
    private final Label taskLengthLabel = new Label("Durée : ");
    private final TextField taskLengthField = new TextField(); {
        makeNumberOnly(taskLengthField);
    }

    // Ajouter tâche antérieure
    private final Label addAntTaskTitle = new Label("Ajouter une ou plusieurs tâche(s) antérieure(s)"); {
        addAntTaskTitle.setFont(H2_FONT);
    }
    private final TextField antTaskTextField = new TextField();
    private final Label antTaskInstrLabel = new Label("Format d'ajout : Tache 1 - Tache 2 - Tache 3");
    private final Button addTaskBtn = new Button("Ajouter la tâche"); {
        addTaskBtn.setOnAction(e -> addingTask());
    }

    // Supprimer une tâche
    private final Label deleteTaskTitle = new Label("Supprimer une tâche"); {
        deleteTaskTitle.setFont(H1_FONT);
    }
    private final ChoiceBox delTasksBox = new ChoiceBox(); {
        delTasksBox.setDisable(true);
    }
    private final Button deleteTaskBtn = new Button("Supprimer"); {
        deleteTaskBtn.setDisable(true);
        setDeleteTaskBtnAction();
    }

    // Assigner une tâche
    private final Label assignTaskTitle = new Label("Assigner une tâche"); {
        assignTaskTitle.setFont(H1_FONT);
    }
    private final ChoiceBox assignTasksBox = new ChoiceBox(); {
        assignTasksBox.setDisable(true);
    }
    private final ChoiceBox assignChefBox = new ChoiceBox(); {
        assignChefBox.setDisable(true);
    }
    private final Button assignTaskBtn = new Button("Assigner"); {
        assignTaskBtn.setDisable(true);
        assignTaskBtnSetAction();
    }

    private final Tab et = new Tab("Modifier montage");
    private static final Font H1_FONT = new Font("Segoe UI", 20);
    private static final Font H2_FONT = new Font("Segoe UI", 16);


    /**
     * Initialise l'onglet permettant d'éditer un montage
     * @param esv Superviser d'édition de montage
     * @param rsv Superviser de visualisation de montage
     */
    public EditTab(EditSuperviser esv, ReadSuperviser rsv) {
        this.esv = esv;
        this.rsv = rsv;
        setContent();
    }

    /**
     * Permet d'obtenir l'onglet contenant les Nodes de création de montage
     * @return Onlget de modification de montage
     */
    public Tab getEditViewTab() {
        // Positionnement
        initGridPane();
        initMontageNameEdit();
        initAddTask();
        initAddAntTask();
        initDelTask();
        initAssignTask();

        et.setContent(gp);
        et.setClosable(false);

        if (rsv.checkMontageExist()) {
            et.setDisable(true);
        }

        return et;
    }

    private void renamingMontageName() {
        montageNameBtn.setOnAction(e -> {
            if(!montageNameField.getText().isBlank()) {
                esv.editName(montageNameField.getText());
            } else {
                emptyMontageNameAlert();
            }
        });
    }
    private void emptyMontageNameAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Nom du montage vide");
        a.setHeaderText("");
        a.setContentText("Vous ne pouvez pas donner de nom vide à un montage");
        a.showAndWait();
    }
    private static void makeNumberOnly(TextField taskLengthField) {
        var txtProperty = taskLengthField.textProperty();
        txtProperty.addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                taskLengthField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    private void addTheTask() {
        List<String> antTasks = new ArrayList<>();
        String antTaskTxt = antTaskTextField.getText();
        String[] split = antTaskTxt.split(" - ");
        Collections.addAll(antTasks, split);

        esv.addTask(taskNameField.getText(), taskDescField.getText(), parseInt(taskLengthField.getText()), antTasks);
        clearFields();
    }

    private void addingTask() {
        String tkNameFd = taskNameField.getText();
        if (tkNameFd.isBlank()) {
            noTaskNameAlert();
        } else if (tkNameFd.isBlank()) {
            noTaskLengthAlert();
        } else if(rsv.checkTaskAlreadyExist(tkNameFd)) {
            taskNameAlreadyExistAlert();
        } else {
            addTheTask();
        }
    }
    private void clearFields() {
        taskNameField.clear();
        taskDescField.clear();
        taskLengthField.clear();
        antTaskTextField.clear();
    }
    private void noTaskNameAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erreur");
        a.setHeaderText("");
        a.setContentText("Veuillez donner un nom à votre tâche");
        a.showAndWait();
    }
    private void noTaskLengthAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erreur");
        a.setHeaderText("");
        a.setContentText("Veuillez donner une durée à votre tâche");
        a.showAndWait();
    }

    private void taskNameAlreadyExistAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erreur");
        a.setHeaderText("");
        a.setContentText("Il existe déjà une tâche avec ce nom");
        a.showAndWait();
    }
    private void setDeleteTaskBtnAction() {
        deleteTaskBtn.setOnAction(e -> {
            int selectedIndex = delTasksBox.getSelectionModel().getSelectedIndex();
            int assot = esv.getAssociateNumber(selectedIndex);
            confirmDeleteAlert(selectedIndex, assot);

        });
    }
    private void confirmDeleteAlert(int selectedIndex, int assot) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmez la suppression");
        a.setHeaderText("Cette tâche est associée à " + assot + " tâche(s)");
        a.setContentText("Etes vous sûr de vouloir la supprimer ?");
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK) {
            esv.deleteTask(selectedIndex);
            var selectMd = delTasksBox.getSelectionModel();
            selectMd.selectFirst();
        }
    }
    private void assignTaskBtnSetAction() {
        assignTaskBtn.setOnAction(e -> esv.assignChief(assignTasksBox.getSelectionModel().getSelectedIndex(),
                assignChefBox.getSelectionModel().getSelectedIndex()));
    }

    private void setContent() {
        rsv.setEditView(this);
    }

    @Override
    public void setTitle(String montageName) {
        montageNameField.setText(montageName);
    }

    @Override
    public void setTaskList(List<Task> tasks) {
        if(tasks.size() != 0) {
            enableBtnAndBox();
        } else {
            disableBtnAndBox();
        }

        List<String> tasksNames = new ArrayList<>();
        for (var t : tasks) {
            tasksNames.add(t.getName());
        }
        delTasksBox.getItems().setAll(tasksNames);
        assignTasksBox.getItems().setAll(tasksNames);

        initAssignChiefBox();
    }
    private void initAssignChiefBox() {
        if(esv != null) {
            assignChefBox.getItems().setAll(esv.getChiefNames());
        }
    }
    private void disableBtnAndBox() {
        delTasksBox.setDisable(true);
        deleteTaskBtn.setDisable(true);
        assignTaskBtn.setDisable(true);
        assignChefBox.setDisable(true);
        assignTasksBox.setDisable(true);
    }
    private void enableBtnAndBox() {
        delTasksBox.setDisable(false);
        deleteTaskBtn.setDisable(false);
        assignTaskBtn.setDisable(false);
        assignChefBox.setDisable(false);
        assignTasksBox.setDisable(false);
    }

    @Override
    public void setEnable() {
        et.setDisable(false);
    }

    private void initMontageNameEdit() {
        gp.add(montageNameTitle, 0, 0, 5, 1);
        gp.add(montageNameLabel, 0, 1);
        gp.add(montageNameField, 1, 1);
        gp.add(montageNameBtn, 2, 1);
    }
    private void initAddTask() {
        gp.add(addTaskTitle, 0, 2, 5, 1);
        gp.add(taskNameLabel, 0, 3);
        gp.add(taskNameField, 1, 3);
        gp.add(taskDescLabel, 0, 4);
        gp.add(taskDescField, 1, 4);
        gp.add(taskLengthLabel, 0, 5);
        gp.add(taskLengthField, 1, 5);
        gp.add(addTaskBtn, 0, 6);
    }
    private void initAddAntTask() {
        gp.add(addAntTaskTitle, 3, 2, 5, 1);
        gp.add(antTaskTextField, 3, 3);
        gp.add(antTaskInstrLabel, 3, 4);
    }
    private void initDelTask() {
        gp.add(deleteTaskTitle, 0, 7, 5, 1);
        gp.add(delTasksBox, 0, 8);
        gp.add(deleteTaskBtn, 0, 9);
    }
    private void initAssignTask() {
        gp.add(assignTaskTitle, 0, 10, 5, 1);
        gp.add(assignTasksBox, 0, 11);
        gp.add(assignChefBox, 1, 11);
        gp.add(assignTaskBtn, 0, 12);
    }
    private void initGridPane() {
        gp.setPadding(new Insets(10));
        gp.setVgap(5);
        gp.setHgap(5);
    }
}
