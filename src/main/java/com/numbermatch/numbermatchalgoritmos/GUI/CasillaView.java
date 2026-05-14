package com.numbermatch.numbermatchalgoritmos.GUI;

import com.numbermatch.numbermatchalgoritmos.Logica.Casilla;
import com.numbermatch.numbermatchalgoritmos.Logica.Node;
import javafx.scene.control.Button;

public class CasillaView extends Button {
    private Node<Casilla> nodoAsociado;

    public CasillaView(Node<Casilla> nodo) {
        super("" + nodo.getInfo().getValor());
        this.nodoAsociado = nodo;

        this.getStyleClass().add("casilla-btn");

        this.setMinWidth(50);
        this.setMinHeight(50);
        this.setAlignment(javafx.geometry.Pos.CENTER);

        actualizarEstado();
    }

    public Node<Casilla> getNodoAsociado() {
        return nodoAsociado;
    }

    public void actualizarEstado() {
        this.getStyleClass().removeAll("casilla-sugerida", "casilla-eliminada");

        if (nodoAsociado.getInfo().isEliminada()) {
            this.setText("");
            this.getStyleClass().add("casilla-eliminada");
            this.setDisable(true);
        } else {
            this.setText(String.valueOf(nodoAsociado.getInfo().getValor()));

            if (nodoAsociado.getInfo().isSugerida()) {
                this.getStyleClass().add("casilla-sugerida");
            }
        }
    }
}