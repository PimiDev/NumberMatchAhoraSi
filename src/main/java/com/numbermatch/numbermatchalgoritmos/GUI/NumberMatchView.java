package com.numbermatch.numbermatchalgoritmos.GUI;

import com.numbermatch.numbermatchalgoritmos.Logica.Tablero;
import javafx.scene.layout.HBox;

public class NumberMatchView extends HBox {

    private TableroView tableroView;
    private BotonesView botonesView;
    private InfoView infoView;
    private Tablero tablero;

    public NumberMatchView(int filas, int columnas) {
        tablero = new Tablero(filas, columnas);
        tableroView = new TableroView(tablero);
        botonesView = new BotonesView();
        infoView = new InfoView();

        this.getChildren().addAll(botonesView, tableroView, infoView);
        this.setStyle("-fx-background-color: #1E1E2E;");
    }

    public TableroView getTableroView() { return tableroView; }
    public BotonesView getBotonesView() { return botonesView; }
    public InfoView getInfoView() { return infoView; }
    public Tablero getTablero() { return tablero; }
}