package com.numbermatch.numbermatchalgoritmos.GUI;

import com.numbermatch.numbermatchalgoritmos.Logica.Casilla;
import com.numbermatch.numbermatchalgoritmos.Logica.Node;
import com.numbermatch.numbermatchalgoritmos.Logica.Tablero;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

public class TableroView extends GridPane {

    private Tablero tableroLogico;

    public TableroView(Tablero tableroLogico) {
        this.tableroLogico = tableroLogico;

        this.setAlignment(Pos.CENTER);
        this.setHgap(8);
        this.setVgap(8);

        dibujarTablero();
    }

    public void dibujarTablero() {
        this.getChildren().clear();

        int filas = tableroLogico.getFilas();
        int columnas = tableroLogico.getColumnas();

        Node<Casilla> actual = tableroLogico.getListaPrincipal().getInicio();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (actual != null) {
                    CasillaView casillaUI = new CasillaView(actual);
                    this.add(casillaUI, j, i);
                    actual = actual.getRight();
                }
            }
        }
    }

    public List<CasillaView> getCasillas() {
        List<CasillaView> lista = new ArrayList<>();
        for (javafx.scene.Node n : this.getChildren()) {
            if (n instanceof CasillaView) {
                lista.add((CasillaView) n);
            }
        }
        return lista;
    }

    public void refrescarCasillas() {
        for (CasillaView cv : getCasillas()) {
            cv.actualizarEstado();
        }
    }
}