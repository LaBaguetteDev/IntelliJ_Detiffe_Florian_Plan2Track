package org.helmo.plan2track.view;

import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Classe permettant d'obtenir la vue principale avec tous les onlgets
 */
public class MainView {

    private final Tab createView;
    private final Tab montageView;
    private final Tab editView;
    private final BorderPane bp = new BorderPane();

    /**
     * Initialise la vue principal
     * @param createView Onglet de création de montage
     * @param montageView Onglet de visualisation de montage
     * @param editView Onglet d'édition de montage
     */
    public MainView(Tab createView, Tab montageView, Tab editView) {
        this.createView = createView;
        this.montageView = montageView;
        this.editView = editView;
    }

    /**
     * Permet d'obtenir le BorderPane de la vue principale
     * @return BorderPane de la vue principale
     */
    public BorderPane getBorderPane() {
        TabPane tp = new TabPane();
        var tabs = tp.getTabs();
        tabs.addAll(createView, montageView, editView);

        bp.setCenter(tp);

        return bp;
    }

}
