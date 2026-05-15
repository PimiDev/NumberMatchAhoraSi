package com.numbermatch.numbermatchalgoritmos.GUI;

import com.numbermatch.numbermatchalgoritmos.Logica.Casilla;
import com.numbermatch.numbermatchalgoritmos.Logica.ListaSimple;
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

        Node<ListaSimple<Node<Casilla>>> filaActual = tableroLogico.getFilasPorNodos().getInicio();
        int fila = 0;

        while (filaActual != null) {
            Node<Node<Casilla>> nodoActual = filaActual.getInfo().getInicio();
            int columna = 0;

            while (nodoActual != null) {
                CasillaView casillaUI = new CasillaView(nodoActual.getInfo());
                this.add(casillaUI, columna, fila);
                columna++;
                nodoActual = nodoActual.getDown();
            }

            fila++;
            filaActual = filaActual.getDown();
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