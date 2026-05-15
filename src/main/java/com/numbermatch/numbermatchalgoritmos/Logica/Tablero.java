package com.numbermatch.numbermatchalgoritmos.Logica;
import java.util.Random;

public class Tablero {
    private ListaSimple<Casilla> listaPrincipal;
    private int filas;
    private int columnas;
    private static final int MAXFILAS = 8;
    private Random rand;
    private ListaSimple<Node<Casilla>> cabeceras = new ListaSimple<>();
    private ListaSimple<ListaSimple<Node<Casilla>>> filasPorNodos = new ListaSimple<>();

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        listaPrincipal = new ListaSimple<Casilla>();
        rand = new Random();
        generarTablero();
    }

    public int numeroRandom() {
        return rand.nextInt(9) + 1;
    }

    public Node<Casilla> generarFilaNueva() {
        Node<Casilla> inicioFila = new Node<>(new Casilla(numeroRandom()));
        Node<Casilla> aux = inicioFila;
        for (int i = 1; i < columnas; i++) {
            Node<Casilla> nuevo = new Node<>(new Casilla(numeroRandom()));
            aux.setRight(nuevo);
            nuevo.setLeft(aux);
            aux = nuevo;
        }
        return inicioFila;
    }

    private ListaSimple<Node<Casilla>> capturarFila(Node<Casilla> cabecera) {
        ListaSimple<Node<Casilla>> fila = new ListaSimple<>();
        Node<Casilla> actual = cabecera;
        for (int j = 0; j < columnas; j++) {
            if (actual != null) {
                fila.insertarFin(new Node<>(actual));
                actual = actual.getRight();
            }
        }
        return fila;
    }

    /**
     * Devuelve el Node<Casilla> en la posición (fila, col) de filasPorNodos.
     * Útil para reconstruir enlaces verticales y diagonales por índice.
     */
    private Node<Casilla> getCelda(int fila, int col) {
        Node<ListaSimple<Node<Casilla>>> filaNode = filasPorNodos.getInicio();
        for (int f = 0; f < fila && filaNode != null; f++) {
            filaNode = filaNode.getDown();
        }
        if (filaNode == null) return null;

        Node<Node<Casilla>> celdaNode = filaNode.getInfo().getInicio();
        for (int c = 0; c < col && celdaNode != null; c++) {
            celdaNode = celdaNode.getDown();
        }
        return celdaNode != null ? celdaNode.getInfo() : null;
    }

    public void generarTablero() {
        cabeceras     = new ListaSimple<>();
        filasPorNodos = new ListaSimple<>();
        Node<Casilla> filaAnteriorHead = null;

        for (int f = 0; f < filas; f++) {
            Node<Casilla> filaActualHead = generarFilaNueva();
            filasPorNodos.insertarFin(new Node<>(capturarFila(filaActualHead)));

            if (f == 0) {
                listaPrincipal.setInicio(filaActualHead);
            } else {
                Node<Casilla> auxTail = filaAnteriorHead;
                while (auxTail.getRight() != null) auxTail = auxTail.getRight();
                auxTail.setRight(filaActualHead);
                filaActualHead.setLeft(auxTail);
            }

            filaAnteriorHead = filaActualHead;
        }

        reconectarTodo();
    }

    public boolean agregarFila() {
        if (filas >= MAXFILAS) return false;

        Node<Casilla> nuevaFila = generarFilaNueva();

        // Enlazar al final de la cadena horizontal (ya excluye eliminadas por reconectarNodo)
        Node<Casilla> tail = listaPrincipal.getInicio();
        while (tail.getRight() != null) tail = tail.getRight();
        tail.setRight(nuevaFila);
        nuevaFila.setLeft(tail);

        filasPorNodos.insertarFin(new Node<>(capturarFila(nuevaFila)));
        filas++;

        // reconectarTodo ahora respeta eliminadas, es seguro llamarlo
        reconectarTodo();
        return true;
    }

    /**
     * Reconstruye cabeceras y todos los enlaces verticales/diagonales
     * recorriendo filasPorNodos por índice (columna a columna y diagonal a diagonal),
     * saltando celdas eliminadas. Los enlaces horizontales no se tocan aquí,
     * ya que son mantenidos por reconectarNodo al eliminar.
     */
    public void reconectarTodo() {
        cabeceras = new ListaSimple<>();

        // 1. Limpiar todos los enlaces verticales y diagonales (no horizontales)
        Node<ListaSimple<Node<Casilla>>> filaNode = filasPorNodos.getInicio();
        while (filaNode != null) {
            Node<Node<Casilla>> celda = filaNode.getInfo().getInicio();
            while (celda != null) {
                Node<Casilla> n = celda.getInfo();
                n.setUp(null);       n.setDown(null);
                n.setUpLeft(null);   n.setUpRight(null);
                n.setDownLeft(null); n.setDownRight(null);
                celda = celda.getDown();
            }
            filaNode = filaNode.getDown();
        }

        // 2. Registrar cabeceras
        filaNode = filasPorNodos.getInicio();
        while (filaNode != null) {
            Node<Node<Casilla>> primero = filaNode.getInfo().getInicio();
            if (primero != null) cabeceras.insertarFin(new Node<>(primero.getInfo()));
            filaNode = filaNode.getDown();
        }

        // 3. Reconectar verticales: columna por columna, saltando eliminadas
        for (int col = 0; col < columnas; col++) {
            Node<Casilla> anterior = null;
            for (int fila = 0; fila < filas; fila++) {
                Node<Casilla> actual = getCelda(fila, col);
                if (actual != null && !actual.getInfo().isEliminada()) {
                    if (anterior != null) {
                        anterior.setDown(actual);
                        actual.setUp(anterior);
                    }
                    anterior = actual;
                }
            }
        }

        // 4. Reconectar diagonales abajo-derecha (↘)
        //    Recorre cada diagonal que parte del borde superior o izquierdo
        for (int startFila = 0; startFila < filas; startFila++) {
            for (int startCol = 0; startCol < columnas; startCol++) {
                if (startFila > 0 && startCol > 0) continue; // solo desde borde
                Node<Casilla> anterior = null;
                int f = startFila, c = startCol;
                while (f < filas && c < columnas) {
                    Node<Casilla> actual = getCelda(f, c);
                    if (actual != null && !actual.getInfo().isEliminada()) {
                        if (anterior != null) {
                            anterior.setDownRight(actual);
                            actual.setUpLeft(anterior);
                        }
                        anterior = actual;
                    }
                    f++; c++;
                }
            }
        }

        // 5. Reconectar diagonales abajo-izquierda (↙)
        //    Recorre cada diagonal que parte del borde superior o derecho
        for (int startFila = 0; startFila < filas; startFila++) {
            for (int startCol = 0; startCol < columnas; startCol++) {
                if (startFila > 0 && startCol < columnas - 1) continue; // solo desde borde
                Node<Casilla> anterior = null;
                int f = startFila, c = startCol;
                while (f < filas && c >= 0) {
                    Node<Casilla> actual = getCelda(f, c);
                    if (actual != null && !actual.getInfo().isEliminada()) {
                        if (anterior != null) {
                            anterior.setDownLeft(actual);
                            actual.setUpRight(anterior);
                        }
                        anterior = actual;
                    }
                    f++; c--;
                }
            }
        }
    }

    public ListaSimple<ListaSimple<Node<Casilla>>> getFilasPorNodos() { return filasPorNodos; }
    public ListaSimple<Node<Casilla>> getCabeceras() { return cabeceras; }
    public ListaSimple<Casilla> getListaPrincipal() { return listaPrincipal; }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public void setFilas(int filasAnterior) { this.filas = filasAnterior; }

    public int contarConcordancias() {
        int count = 0;
        Node<Casilla> actual = listaPrincipal.getInicio();
        while (actual != null) {
            Casilla c = actual.getInfo();
            if (!c.isEliminada()) {
                Node<Casilla> right     = actual.getRight();
                Node<Casilla> down      = actual.getDown();
                Node<Casilla> downRight = actual.getDownRight();
                Node<Casilla> downLeft  = actual.getDownLeft();
                if (right     != null && !right.getInfo().isEliminada()     && c.comparar(right.getInfo()))     count++;
                if (down      != null && !down.getInfo().isEliminada()      && c.comparar(down.getInfo()))      count++;
                if (downRight != null && !downRight.getInfo().isEliminada() && c.comparar(downRight.getInfo())) count++;
                if (downLeft  != null && !downLeft.getInfo().isEliminada()  && c.comparar(downLeft.getInfo()))  count++;
            }
            actual = actual.getRight();
        }
        return count;
    }

    public boolean darPista() {
        Node<Casilla> actual = listaPrincipal.getInicio();
        while (actual != null) {
            Casilla c = actual.getInfo();
            if (!c.isEliminada()) {
                Node<Casilla> right     = actual.getRight();
                Node<Casilla> down      = actual.getDown();
                Node<Casilla> downRight = actual.getDownRight();
                Node<Casilla> downLeft  = actual.getDownLeft();
                if (right != null && !right.getInfo().isEliminada() && c.comparar(right.getInfo())) {
                    c.setSugerida(true); right.getInfo().setSugerida(true); return true;
                }
                if (down != null && !down.getInfo().isEliminada() && c.comparar(down.getInfo())) {
                    c.setSugerida(true); down.getInfo().setSugerida(true); return true;
                }
                if (downRight != null && !downRight.getInfo().isEliminada() && c.comparar(downRight.getInfo())) {
                    c.setSugerida(true); downRight.getInfo().setSugerida(true); return true;
                }
                if (downLeft != null && !downLeft.getInfo().isEliminada() && c.comparar(downLeft.getInfo())) {
                    c.setSugerida(true); downLeft.getInfo().setSugerida(true); return true;
                }
            }
            actual = actual.getRight();
        }
        return false;
    }

    /**
     * Al eliminar un nodo, puentea sus vecinos en todas las direcciones
     * para mantener la conectividad (salto sobre eliminadas).
     */
    public void reconectarNodo(Node<Casilla> nodo) {
        // Horizontal
        Node<Casilla> izq = nodo.getLeft();
        Node<Casilla> der = nodo.getRight();
        if (izq != null) izq.setRight(der);
        if (der != null) der.setLeft(izq);

        // Vertical
        Node<Casilla> arr = nodo.getUp();
        Node<Casilla> aba = nodo.getDown();
        if (arr != null) arr.setDown(aba);
        if (aba != null) aba.setUp(arr);

        // Diagonal ↘ / ↖
        Node<Casilla> upLeft    = nodo.getUpLeft();
        Node<Casilla> downRight = nodo.getDownRight();
        if (upLeft    != null) upLeft.setDownRight(downRight);
        if (downRight != null) downRight.setUpLeft(upLeft);

        // Diagonal ↙ / ↗
        Node<Casilla> upRight  = nodo.getUpRight();
        Node<Casilla> downLeft = nodo.getDownLeft();
        if (upRight  != null) upRight.setDownLeft(downLeft);
        if (downLeft != null) downLeft.setUpRight(upRight);
    }

    public void eliminarPar(Node<Casilla> nodo1, Node<Casilla> nodo2) {
        nodo1.getInfo().setEliminada(true);
        nodo2.getInfo().setEliminada(true);
        reconectarNodo(nodo1);
        reconectarNodo(nodo2);
    }

    public void setFilasPorNodos(ListaSimple<ListaSimple<Node<Casilla>>> filasPorNodos) {
        this.filasPorNodos = filasPorNodos;
    }
}