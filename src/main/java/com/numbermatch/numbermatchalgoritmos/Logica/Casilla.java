package com.numbermatch.numbermatchalgoritmos.Logica;

public class Casilla implements Comparable<Casilla>{

    private int valor;
    private boolean seleccionada;
    private boolean eliminada;
    private boolean sugerida;

    public Casilla(int valor) {
        this.valor = valor;
        this.seleccionada = false;
        this.eliminada = false;
        this.sugerida = false;
    }

    public int getValor() { return valor; }
    public void setValor(int valor) { this.valor = valor; }

    public boolean isSeleccionada() {return seleccionada;}
    public void setSeleccionada(boolean seleccionada) {this.seleccionada = seleccionada;}

    public boolean isEliminada(){return eliminada;}
    public void setEliminada(boolean eliminada){this.eliminada = eliminada;}

    public boolean isSugerida(){return sugerida;}
    public void setSugerida(boolean sugerida){this.sugerida = sugerida;}

    //comparar es el metodo final para validar
    public boolean esIgualA(Casilla otra) {
        return this.compareTo(otra) == 0;
    }
    public boolean sumaDiez(Casilla otra) {
        return (this.valor + otra.getValor()) == 10;
    }
    public boolean comparar(Casilla otra){
        return sumaDiez(otra)||esIgualA(otra);
    }

    @Override
    public int compareTo(Casilla o) {
        return Integer.compare(this.valor, o.getValor());
    }



}
