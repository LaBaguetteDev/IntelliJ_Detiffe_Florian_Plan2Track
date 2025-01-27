/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.helmo.plan2track;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.helmo.plan2track.entities.Montage;
import org.helmo.plan2track.supervisers.EditSuperviser;
import org.helmo.plan2track.supervisers.PlanningSuperviser;
import org.helmo.plan2track.supervisers.ReadSuperviser;
import org.helmo.plan2track.view.CreateTab;
import org.helmo.plan2track.view.EditTab;
import org.helmo.plan2track.view.MainView;
import org.helmo.plan2track.view.MontageTab;

/**
 * Classe principale
 */
public class Main extends Application {

    /**
     * Méthode principale
     * param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Montage m = new Montage("Aucun montage créé");
        ReadSuperviser rsv = new ReadSuperviser(m);
        PlanningSuperviser psv = new PlanningSuperviser(m);
        EditSuperviser esv = new EditSuperviser(m, rsv);

        CreateTab cView = new CreateTab(esv);
        MontageTab mView = new MontageTab(rsv, psv);
        EditTab eView = new EditTab(esv, rsv);

        var mv = new MainView(
                cView.getCreateViewTab(),
                mView.getMontageViewTab(),
                eView.getEditViewTab());

        Scene scene = new Scene(mv.getBorderPane(), 800, 600);

        primaryStage.setTitle("Plan2Track - Florian Detiffe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
