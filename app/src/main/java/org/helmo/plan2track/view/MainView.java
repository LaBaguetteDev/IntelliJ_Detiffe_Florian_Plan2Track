package org.helmo.plan2track.view;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainView {

    private final Tab createView;
    private final Tab montageView;
    private final Tab editView;

    public MainView(Tab createView, Tab montageView, Tab editView) {
        this.createView = createView;
        this.montageView = montageView;
        this.editView = editView;
    }

    private final BorderPane bp = new BorderPane();

    public BorderPane getBorderPane() {
        TabPane tp = new TabPane();

        tp.getTabs().addAll(createView, montageView, editView);

        bp.setCenter(tp);

        return bp;
    }

}
