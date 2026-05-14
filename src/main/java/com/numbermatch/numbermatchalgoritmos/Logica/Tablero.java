package com.numbermatch.numbermatchalgoritmos.Logica;
import java.util.Random;

    public class Tablero {
        private ListaSimple<Casilla> listaPrincipal;
        private int filas;
        private int columnas;
        private static final int MINFILAS = 10;

        private Random rand;

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
        /**
         * Genera una lista nueva de nodos NO conectada a la lista principal
         */
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
        /**
         *Toma el nodo inicial de dos filas e itera sobre ellos uniendolos de forma correcta
         */
        private void unirFilas(Node<Casilla> superior, Node<Casilla> inferior) {
            Node<Casilla> s = superior;
            Node<Casilla> i = inferior;

            while (s != null && i != null) {
                s.setDown(i);
                i.setUp(s);

                if (i.getRight() != null) {
                    s.setDownRight(i.getRight());
                    i.getRight().setUpLeft(s);
                }
                if (i.getLeft() != null) {
                    s.setDownLeft(i.getLeft());
                    i.getLeft().setUpRight(s);
                }
                s = s.getRight();
                i = i.getRight();

                if (i == null || s == null) break;
            }
        }

        /**
         * GENERA FILAS NUEVAS Y LAS UNE A LA ULTIMA FILA CREADA
         */
        public void generarTablero() {
            Node<Casilla> filaAnteriorHead = null;

            for (int f = 0; f < filas; f++) {
                Node<Casilla> filaActualHead = generarFilaNueva();

                if (f == 0) {
                    listaPrincipal.setInicio(filaActualHead);
                } else {
                    // Conectar en serpiente: cola de fila anterior -> cabeza de fila nueva
                    Node<Casilla> auxTail = filaAnteriorHead;
                    while (auxTail.getRight() != null) {
                        auxTail = auxTail.getRight();
                    }
                    auxTail.setRight(filaActualHead);
                    filaActualHead.setLeft(auxTail);

                    // Conectar vertical y diagonal
                    unirFilas(filaAnteriorHead, filaActualHead);
                }

                filaAnteriorHead = filaActualHead;
            }
        }

        /**
         *Cuenta concordancias solo tomando en cuenta derecha, abajo y abazo izquierda para NO repetir
         */
        public int contarConcordancias() {
            int count = 0;
            Node<Casilla> actual = listaPrincipal.getInicio();

            while (actual != null) {
                Casilla c = actual.getInfo();

                if (!c.isEliminada()) {
                    // Solo revisamos 3 direcciones para no contar doble
                    Node<Casilla> right     = actual.getRight();
                    Node<Casilla> down      = actual.getDown();
                    Node<Casilla> downRight = actual.getDownRight();

                    if (right != null && !right.getInfo().isEliminada() && c.comparar(right.getInfo())) count++;
                    if (down  != null && !down.getInfo().isEliminada()  && c.comparar(down.getInfo()))  count++;
                    if (downRight != null && !downRight.getInfo().isEliminada() && c.comparar(downRight.getInfo())) count++;
                }

                actual = actual.getRight(); // avanza en serpiente
            }

            return count;
        }


        /**
         *Utiliza el mismo principio que contarConcordancias() pero resalta la primera concordancia encontrada
         */
        public boolean darPista() {
            Node<Casilla> actual = listaPrincipal.getInicio();

            while (actual != null) {
                Casilla c = actual.getInfo();

                if (!c.isEliminada()) {
                    Node<Casilla> right     = actual.getRight();
                    Node<Casilla> down      = actual.getDown();
                    Node<Casilla> downRight = actual.getDownRight();

                    if (right != null && !right.getInfo().isEliminada() && c.comparar(right.getInfo())) {
                        c.setSugerida(true);
                        right.getInfo().setSugerida(true);
                        return true;
                    }
                    if (down != null && !down.getInfo().isEliminada() && c.comparar(down.getInfo())) {
                        c.setSugerida(true);
                        down.getInfo().setSugerida(true);
                        return true;
                    }
                    if (downRight != null && !downRight.getInfo().isEliminada() && c.comparar(downRight.getInfo())) {
                        c.setSugerida(true);
                        downRight.getInfo().setSugerida(true);
                        return true;
                    }
                }

                actual = actual.getRight();
            }

            return false; // no hay concordancias disponibles
        }

        public ListaSimple<Casilla> getListaPrincipal(){
            return listaPrincipal;
        }

        public int getFilas(){
            return filas;
        }
        public int getColumnas(){
            return columnas;
        }

    }
