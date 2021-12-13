package org.helmo.plan2track.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.helmo.plan2track.supervisers.EditSuperviser;

import java.util.Optional;

/**
 * Classe permettant d'obtenir la vue de création d'un montage
 */
public class CreateTab {

    private final Tab ct = new Tab("Créer montage");
    private final EditSuperviser esv;

    private final HBox hBox = new HBox(); {
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
    }
    private final Label newMontageLabel = new Label("Nom du montage : "); {
        newMontageLabel.setPadding(new Insets(5));
    }
    private final TextField montageNameField = new TextField();
    private final Button createBtn = new Button("Créer");

    /**
     * Onglet de la vue permettant de créer un montage
     * @param csv Superviser d'édition de montage
     */
    public CreateTab(EditSuperviser csv) {
        this.esv = csv;
        setCreateViewBp();
    }

    /**
     * Permet d'obtenir l'onglet contenant les Nodes de création de montage
     * @return Onglet de création de montage
     */
    public Tab getCreateViewTab() {
        return ct;
    }

    private void setCreateViewBp() {
        var hBoxChildren = hBox.getChildren();
        hBoxChildren.addAll(newMontageLabel, montageNameField, createBtn);

        ct.setContent(hBox);
        ct.setClosable(false);

        createBtnSetAction();
    }
    private void createBtnSetAction() {
        createBtn.setOnAction(e -> {
            if(montageNameField.getText().isBlank()) {
                emptyNameAlert();
            }
            else if(checkMontageExist()) {
                esv.createMontage(montageNameField.getText());
                montageNameField.setText("");
            } else {
                montageAlreadyExistAlert();
            }
        });
    }

    private void montageAlreadyExistAlert() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Montage déjà existant");
        a.setHeaderText("");
        a.setContentText("Etes-vous sûr de vouloir écraser le montage existant ?");
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK) {
            esv.createMontage(montageNameField.getText());
        }
    }

    private void emptyNameAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Nom du montage vide");
        a.setHeaderText("");
        a.setContentText("Vous ne pouvez pas créer un montage avec un nom blanc");
        a.showAndWait();
    }

    private boolean checkMontageExist() {
        return esv.montageExist();
    }
}
