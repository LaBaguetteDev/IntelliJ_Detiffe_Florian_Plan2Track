package org.helmo.plan2track.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.helmo.plan2track.supervisers.EditSuperviser;

import java.util.Optional;

public class CreateView {

    private final Tab ct = new Tab("Créer montage");
    private final EditSuperviser esv;

    HBox hBox = new HBox(); {
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
    }

    Label newMontageLabel = new Label("Nom du montage : "); {
        newMontageLabel.setPadding(new Insets(5));
    }

    TextField montageNameField = new TextField();
    Button createBtn = new Button("Créer");


    public CreateView(EditSuperviser csv) {
        this.esv = csv;
        setCreateViewBp();
    }

    public Tab getCreateViewTab() {
        return ct;
    }

    private void setCreateViewBp() {
        hBox.getChildren().addAll(newMontageLabel, montageNameField, createBtn);

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
