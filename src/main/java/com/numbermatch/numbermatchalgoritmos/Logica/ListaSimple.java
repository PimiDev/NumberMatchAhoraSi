package com.numbermatch.numbermatchalgoritmos.Logica;

public class ListaSimple<T> {
    private Node<T> inicio;

    public ListaSimple() {
        inicio = null;
    }

    public void insertarInicio(T dato) {
        Node<T> n = new Node<>(dato);
        n.setRight(inicio);
        inicio = n;
    }

    public void insertarFin(Node<T> nuevoNodo) {
        if (inicio == null) {
            inicio = nuevoNodo;
        } else {
            Node<T> ultimo = getUltimo();
            ultimo.setDown(nuevoNodo);
            nuevoNodo.setUp(ultimo);
        }
    }

    public Node<T> getUltimo() {
        if (inicio == null) return null;
        Node<T> aux = inicio;
        while (aux.getDown() != null) {
            aux = aux.getDown();
        }
        return aux;
    }

    public Node<T> eliminaInicio() {
        if (inicio == null) {
            System.out.println("luista vacia");
            return null;
        }
        Node<T> temp = inicio;
        inicio = inicio.getRight();
        temp.setRight(null); // Desconectamos el nodo eliminado
        return temp;
    }

    public Node<T> eliminaFin() {
        if (inicio == null) {
            System.out.println("Lista vacia");
            return null;
        }

        if (inicio.getRight() == null) {
            Node<T> temp = inicio;
            inicio = null;
            return temp;
        }

        Node<T> r = inicio;
        Node<T> a = null;
        while (r.getRight() != null) {
            a = r;
            r = r.getRight();
        }
        a.setRight(null);
        return r;
    }

    public String mostrarLista() {
        if (inicio == null) return "Lista vacia";

        StringBuilder cadena = new StringBuilder();
        Node<T> r = inicio;
        while (r != null) {
            cadena.append("|").append(r.getInfo()).append("|");
            r = r.getRight();
        }
        cadena.append("null");
        return cadena.toString();
    }

    public int contarNodes() {
        int total = 0;
        Node<T> aux = inicio;
        while (aux != null) {
            total++;
            aux = aux.getRight();
        }
        return total;
    }

    public void insertarEn(int pos, T dato) {
        if (pos <= 1 || inicio == null) {
            insertarInicio(dato);
            return;
        }
        Node<T> aux = inicio;
        int nodeActual = 1;
        while (nodeActual < pos - 1 && aux.getRight() != null) {
            aux = aux.getRight();
            nodeActual++;
        }
        Node<T> nuevo = new Node<>(dato);
        nuevo.setRight(aux.getRight());
        aux.setRight(nuevo);
    }

    public T eliminarX(T dato) {
        if (inicio == null) return null;

        if (inicio.getInfo().equals(dato)) {
            return eliminaInicio().getInfo();
        }

        Node<T> aux = inicio;
        while (aux.getRight() != null && !aux.getRight().getInfo().equals(dato)) {
            aux = aux.getRight();
        }

        if (aux.getRight() != null) {
            T datoRetorno = aux.getRight().getInfo();
            aux.setRight(aux.getRight().getRight());
            return datoRetorno;
        }
        return null;
    }

    public void setInicio(Node<T> inicio) {
        this.inicio = inicio;
    }

    public Node<T> getInicio() {
        return inicio;
    }

}