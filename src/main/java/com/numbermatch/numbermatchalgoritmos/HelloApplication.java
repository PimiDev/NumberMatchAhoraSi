package com.numbermatch.numbermatchalgoritmos;

import com.numbermatch.numbermatchalgoritmos.GUI.MenuInicialView;
import com.numbermatch.numbermatchalgoritmos.GUI.NumberMatchView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        MenuInicialView menu = new MenuInicialView();

        // Cargar estilos en la ventana del menú
        menu.getScene().getStylesheets().add(
                getClass().getResource("/estilos.css").toExternalForm());

        menu.getBtnJugar().setOnAction(e -> {
            int filas    = menu.getFilasSeleccionadas();
            int columnas = menu.getColumnasSeleccionadas();
            menu.close();

            NumberMatchView vista = new NumberMatchView(filas, columnas);
            new NumberMatchController(vista);

            Scene scene = new Scene(vista, 800, 600);
            // Cargar estilos en la ventana del juego
            scene.getStylesheets().add(
                    getClass().getResource("/estilos.css").toExternalForm());

            stage.setTitle("Number Match");
            stage.setScene(scene);
            stage.show();
        });

        menu.show();
    }
}