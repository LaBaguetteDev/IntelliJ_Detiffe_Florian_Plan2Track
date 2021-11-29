package org.helmo.plan2track.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.helmo.plan2track.entities.Montage;

public class MainView {

    private static Tab createView;
    private static Tab montageView;
    private static Tab editView;

    public MainView(Tab createView, Tab montageView, Tab editView) {
        this.createView = createView;
        this.montageView = montageView;
        this.editView = editView;
    }

    private static final BorderPane bp = new BorderPane();

    public static BorderPane getBorderPane() {
        TabPane tp = new TabPane();

        tp.getTabs().addAll(createView, montageView, editView);

        bp.setCenter(tp);

        /*askForMontageName();

        HBox hBox = new HBox(); hBox.setPadding(new Insets(10)); hBox.setSpacing(10);

        Label labelNewMontage = new Label("Nouvelle tâche : "); labelNewMontage.setPadding(new Insets(5));
        TextField textFieldNewMontage = new TextField();
        Button addButton = new Button("Ajouter");

        Button deleteMontage = new Button("Créer un nouveau montage");

        hBox.getChildren().addAll(labelNewMontage, textFieldNewMontage, addButton, deleteMontage);

        VBox vBox = new VBox(); vBox.setPadding(new Insets(10));
        ListView<String> listView = new ListView<>();

        TableView<Montage> tv = new TableView<Montage>();
        TableColumn<Montage, String> tc = new TableColumn("Nom");
        tv.getColumns().addAll(tc);

        bp.setCenter(vBox);
        bp.setTop(hBox);
        bp.setBottom(tv);

        addButton.setOnAction(e -> {
            String nom = textFieldNewMontage.getText();
            listView.getItems().add(nom);
            vBox.getChildren().add(listView);
        });

        deleteMontage.setOnAction(e -> {
            askForMontageName();
        });*/

        return bp;
    }

}
