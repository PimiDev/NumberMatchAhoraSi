package com.numbermatch.numbermatchalgoritmos.Logica;

public class HistorialTablero {

    private ListaSimple<ListaSimple<ListaSimple<Casilla>>> historial      = new ListaSimple<>();
    private ListaSimple<Integer>                           filasHistorial = new ListaSimple<>();
    private ListaSimple<Integer>                           paresHistorial = new ListaSimple<>();
    private Tablero tablero;

    public HistorialTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    // ─── GUARDAR ───────────────────────────────────────────────────────

    public void guardarEstado(int paresEncontrados) {
        ListaSimple<ListaSimple<Casilla>> snapshot = new ListaSimple<>();

        Node<ListaSimple<Node<Casilla>>> filaNode = tablero.getFilasPorNodos().getInicio();
        while (filaNode != null) {
            ListaSimple<Casilla> filaCopia = new ListaSimple<>();

            Node<Node<Casilla>> celdaNode = filaNode.getInfo().getInicio();
            while (celdaNode != null) {
                Casilla original = celdaNode.getInfo().getInfo();
                Casilla copia = new Casilla(original.getValor());
                copia.setEliminada(original.isEliminada());
                copia.setSeleccionada(original.isSeleccionada());
                copia.setSugerida(original.isSugerida());
                filaCopia.insertarFin(new Node<>(copia));
                celdaNode = celdaNode.getDown();
            }

            snapshot.insertarFin(new Node<>(filaCopia));
            filaNode = filaNode.getDown();
        }

        historial.insertarFin(new Node<>(snapshot));
        filasHistorial.insertarFin(new Node<>(tablero.getFilas()));
        paresHistorial.insertarFin(new Node<>(paresEncontrados));
    }

    // ─── DESHACER ──────────────────────────────────────────────────────

    // Devuelve los paresEncontrados del estado anterior, o -1 si no hay historial
    public int deshacerUltimo() {
        if (historial.getInicio() == null) return -1;

        ListaSimple<ListaSimple<Casilla>> snapshot = sacarUltimo(historial);
        int filasAnterior                          = sacarUltimo(filasHistorial);
        int paresAnterior                          = sacarUltimo(paresHistorial);

        ListaSimple<ListaSimple<Node<Casilla>>> nuevasFilasPorNodos = new ListaSimple<>();

        Node<Casilla> inicioPrincipal   = null;
        Node<Casilla> anteriorPrincipal = null;

        Node<ListaSimple<Casilla>> filaSnap = snapshot.getInicio();
        while (filaSnap != null) {
            ListaSimple<Node<Casilla>> filaNodos = new ListaSimple<>();

            Node<Casilla> celdaSnap = filaSnap.getInfo().getInicio();
            while (celdaSnap != null) {
                Casilla datos = celdaSnap.getInfo();
                Node<Casilla> nodo = new Node<>(datos);

                if (anteriorPrincipal != null) {
                    anteriorPrincipal.setRight(nodo);
                    nodo.setLeft(anteriorPrincipal);
                }
                if (inicioPrincipal == null) inicioPrincipal = nodo;
                anteriorPrincipal = nodo;

                filaNodos.insertarFin(new Node<>(nodo));
                celdaSnap = celdaSnap.getDown();
            }

            nuevasFilasPorNodos.insertarFin(new Node<>(filaNodos));
            filaSnap = filaSnap.getDown();
        }

        tablero.getListaPrincipal().setInicio(inicioPrincipal);
        tablero.setFilas(filasAnterior);
        tablero.setFilasPorNodos(nuevasFilasPorNodos);
        tablero.reconectarTodo();

        return paresAnterior;
    }

    // ─── HELPER ────────────────────────────────────────────────────────

    private <T> T sacarUltimo(ListaSimple<T> lista) {
        Node<T> actual   = lista.getInicio();
        Node<T> anterior = null;

        while (actual.getDown() != null) {
            anterior = actual;
            actual   = actual.getDown();
        }

        if (anterior == null) {
            lista.setInicio(null);
        } else {
            anterior.setDown(null);
        }

        return actual.getInfo();
    }

    public boolean hayHistorial() {
        return historial.getInicio() != null;
    }
}