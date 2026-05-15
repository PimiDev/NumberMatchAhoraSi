package com.numbermatch.numbermatchalgoritmos.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuInicialView extends Stage {

    private Slider sliderFilas;
    private Slider sliderColumnas;
    private Button btnJugar;

    public MenuInicialView() {
        this.setTitle("Number Match");
        this.setResizable(false);

        Label titulo = new Label("Number Match");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // ── Filas ──
        Label lblFilas = new Label("Filas iniciales: 5");
        lblFilas.setStyle("-fx-text-fill: white;");

        sliderFilas = new Slider(4, 8, 5);
        sliderFilas.setMajorTickUnit(1);
        sliderFilas.setMinorTickCount(0);
        sliderFilas.setSnapToTicks(true);
        sliderFilas.setShowTickLabels(true);
        sliderFilas.setShowTickMarks(true);
        sliderFilas.valueProperty().addListener((obs, oldVal, newVal) ->
                lblFilas.setText("Filas iniciales: " + newVal.intValue()));

        // ── Columnas ──
        Label lblColumnas = new Label("Columnas iniciales: 10");
        lblColumnas.setStyle("-fx-text-fill: white;");

        sliderColumnas = new Slider(10, 16, 10);
        sliderColumnas.setMajorTickUnit(1);
        sliderColumnas.setMinorTickCount(0);
        sliderColumnas.setSnapToTicks(true);
        sliderColumnas.setShowTickLabels(true);
        sliderColumnas.setShowTickMarks(true);
        sliderColumnas.valueProperty().addListener((obs, oldVal, newVal) ->
                lblColumnas.setText("Columnas iniciales: " + newVal.intValue()));

        // ── Botón ──
        btnJugar = new Button("¡Jugar!");
        btnJugar.getStyleClass().add("boton-accion");

        VBox root = new VBox(20, titulo, lblFilas, sliderFilas, lblColumnas, sliderColumnas, btnJugar);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #1E1E2E;");

        this.setScene(new Scene(root, 400, 350));
    }

    public int getFilasSeleccionadas() {
        return (int) sliderFilas.getValue();
    }

    public int getColumnasSeleccionadas() {
        return (int) sliderColumnas.getValue();
    }

    public Button getBtnJugar() {
        return btnJugar;
    }
}