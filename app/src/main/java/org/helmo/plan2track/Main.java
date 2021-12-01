/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.helmo.plan2track;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.helmo.plan2track.entities.Montage;
import org.helmo.plan2track.supervisers.EditSuperviser;
import org.helmo.plan2track.supervisers.ReadSuperviser;
import org.helmo.plan2track.view.CreateView;
import org.helmo.plan2track.view.EditView;
import org.helmo.plan2track.view.MainView;
import org.helmo.plan2track.view.MontageView;

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
        /*ReadSuperviser rsv = new ReadSuperviser();
        EditSuperviser esv = new EditSuperviser(rsv);
        SelectSuperviser ssv = new SelectSuperviser(rsv, esv);
        ssv.menu();*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Montage m = new Montage("Aucun montage créé");
        ReadSuperviser rsv = new ReadSuperviser(m);
        EditSuperviser esv = new EditSuperviser(m, rsv);


        CreateView cView = new CreateView(esv);
        MontageView mView = new MontageView(rsv);
        EditView eView = new EditView(esv, rsv);

        var mv = new MainView(
                cView.getCreateViewTab(),
                mView.getMontageViewTab(),
                eView.getEditViewTab());

        Scene scene = new Scene(mv.getBorderPane(), 800, 600);

        //String css = getClass().getResource("/style.css").toExternalForm();
        //scene.getStylesheets().add(css);

        primaryStage.setTitle("Plan2Track - Florian Detiffe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
