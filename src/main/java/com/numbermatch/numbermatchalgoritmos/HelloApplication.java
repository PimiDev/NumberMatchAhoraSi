package com.numbermatch.numbermatchalgoritmos;

import com.numbermatch.numbermatchalgoritmos.GUI.NumberMatchView;
import com.numbermatch.numbermatchalgoritmos.GUI.TableroView;
import com.numbermatch.numbermatchalgoritmos.Logica.Tablero;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {

        NumberMatchView nmv = new NumberMatchView();
        StackPane root = new StackPane(nmv);
        Scene scene = new Scene(root, 600, 700);

        scene.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());

        stage.setTitle("Number Match");
        stage.setScene(scene);
        stage.show();
    }
}