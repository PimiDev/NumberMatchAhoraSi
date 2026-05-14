package com.numbermatch.numbermatchalgoritmos.Logica;

import java.util.ArrayList;


/**
 * Clase Node
 * Models a node in an 8 way linked list
 * @author Cecilia M. Curlango Rosas
 * @version 01 2026
 */
public class Node<T> {
    private T dato; // INFO part
    private Node<T> up, down,
            left, right,
            downLeft, downRight,
            upLeft, upRight;


    public Node(T dato) {
        this.dato = dato;
        up = null;
        down = null;
        left = null;
        right = null;
        downLeft = null;
        downRight = null;
        upLeft = null;
        upRight = null;
    }


    /**
     * Updates all links
     * to neighboring nodes so that
     * nothing points to it anymore.
     */
    public void delete() {

    }
    /**
     * Returns whether input node is next to
     * node.
     * @return true if nodes are next to each other
     */
    public boolean isNeighbor(Node<T> input) {
        if (input == null) return false;
        return input == up || input == down || input == left || input == right ||
                input == upLeft || input == upRight || input == downLeft || input == downRight;
    }

    public T getInfo() { return dato; }
    public void setInfo(T info) { this.dato = info; }
    public Node<T> getUp() { return up; }
    public void setUp(Node<T> up) { this.up = up; }
    public Node<T> getDown() { return down; }
    public void setDown(Node<T> down) { this.down = down; }
    public Node<T> getLeft() { return left; }
    public void setLeft(Node<T> left) { this.left = left; }
    public Node<T> getRight() { return right; }
    public void setRight(Node<T> right) { this.right = right; }
    public Node<T> getDownLeft() { return downLeft; }
    public void setDownLeft(Node<T> downLeft) { this.downLeft = downLeft; }
    public Node<T> getDownRight() { return downRight; }
    public void setDownRight(Node<T> downRight) { this.downRight = downRight; }
    public Node<T> getUpLeft() { return upLeft; }
    public void setUpLeft(Node<T> upLeft) { this.upLeft = upLeft; }
    public Node<T> getUpRight() { return upRight; }
    public void setUpRight(Node<T> upRight) { this.upRight = upRight; }

}
