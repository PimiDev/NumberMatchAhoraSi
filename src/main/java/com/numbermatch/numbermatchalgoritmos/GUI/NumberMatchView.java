package com.numbermatch.numbermatchalgoritmos.GUI;

import com.numbermatch.numbermatchalgoritmos.Logica.Tablero;
import javafx.scene.layout.HBox;

public class NumberMatchView extends HBox {

    TableroView tableroView;

    public NumberMatchView(){
        Tablero tb = new Tablero(3,3);
        tableroView = new TableroView(tb);
        this.getChildren().addAll(tableroView);
        int concordancias = tb.contarConcordancias();

        System.out.println(concordancias);

        this.setStyle("-fx-background-color: #1a1a2e;");

    }

}
