package com.numbermatch.numbermatchalgoritmos.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BotonesView extends VBox {

    //Botones para el usuario
    private Button agregarFila;
    private Button obtenerPista;
    private Button deshacerTurno;

    public BotonesView(){
        agregarFila = new Button("+");
        obtenerPista = new Button("?");
        deshacerTurno = new Button("↺");


        agregarFila.getStyleClass().add("boton-accion");
        obtenerPista.getStyleClass().add("boton-accion");
        deshacerTurno.getStyleClass().add("boton-accion");

        this.getChildren().addAll(agregarFila,obtenerPista,deshacerTurno);

    }

    public Button getAgregarFila() { return agregarFila; }
    public Button getObtenerPista() { return obtenerPista; }
    public Button getDeshacerTurno() { return deshacerTurno; }

}
