package com.numbermatch.numbermatchalgoritmos.GUI;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InfoView extends VBox {

    //Informacion en pantalla
    private Label paresRestantes;
    private Label paresEncontrados;
    private Label filasRestantes;

    public InfoView(){
        this.getStyleClass().add("info-panel");

        paresRestantes = new Label("Pares restantes: "+0);
        paresEncontrados = new Label("Pares encontrados: "+0);
        filasRestantes = new Label("Filas restantes: "+0);

        paresRestantes.getStyleClass().add("info-label");
        paresEncontrados.getStyleClass().add("info-label");
        filasRestantes.getStyleClass().add("info-label");


        this.getChildren().addAll(paresRestantes,paresEncontrados,filasRestantes);
    }

    public void setParesRestantes(int n) { paresRestantes.setText("Pares restantes: " + n); }
    public void setParesEncontrados(int n) { paresEncontrados.setText("Pares encontrados: " + n); }
    public void setFilasRestantes(int n) { filasRestantes.setText("Filas restantes: " + n); }

}
