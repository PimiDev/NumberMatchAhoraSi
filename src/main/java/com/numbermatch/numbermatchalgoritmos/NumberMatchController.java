package com.numbermatch.numbermatchalgoritmos;

import com.numbermatch.numbermatchalgoritmos.GUI.CasillaView;
import com.numbermatch.numbermatchalgoritmos.GUI.NumberMatchView;
import com.numbermatch.numbermatchalgoritmos.Logica.HistorialTablero;
import com.numbermatch.numbermatchalgoritmos.Logica.Tablero;
import javafx.scene.control.Alert;

public class NumberMatchController {

    private Tablero tablero;
    private NumberMatchView vista;
    private HistorialTablero historial;

    private CasillaView primerSeleccionada;
    private int paresEncontrados = 0;

    public NumberMatchController(NumberMatchView vista) {
        this.vista = vista;
        this.tablero = vista.getTablero();
        this.historial = new HistorialTablero(tablero);

        actualizarInfo();
        asignarEventosBotones();
        asignarEventosCasillas();
    }

    private void asignarEventosBotones() {
        vista.getBotonesView().getObtenerPista().setOnAction(e -> manejarPista());
        vista.getBotonesView().getDeshacerTurno().setOnAction(e -> manejarDeshacer());
        vista.getBotonesView().getAgregarFila().setOnAction(e -> manejarAgregarFila());
    }

    private void asignarEventosCasillas() {
        for (CasillaView cv : vista.getTableroView().getCasillas()) {
            cv.setOnAction(e -> manejarClick(cv));
        }
    }

    private void manejarClick(CasillaView clickeada) {
        limpiarSugeridas();

        if (primerSeleccionada == null) {
            primerSeleccionada = clickeada;
            clickeada.getNodoAsociado().getInfo().setSeleccionada(true);
        } else {
            if (primerSeleccionada == clickeada) {
                clickeada.getNodoAsociado().getInfo().setSeleccionada(false);
                primerSeleccionada = null;
            } else {
                boolean sonValidas = primerSeleccionada.getNodoAsociado().isNeighbor(clickeada.getNodoAsociado())
                        && primerSeleccionada.getNodoAsociado().getInfo().comparar(clickeada.getNodoAsociado().getInfo());

                if (sonValidas) {
                    historial.guardarEstado(paresEncontrados);
                    tablero.eliminarPar(primerSeleccionada.getNodoAsociado(), clickeada.getNodoAsociado());
                    paresEncontrados++;
                } else {
                    primerSeleccionada.getNodoAsociado().getInfo().setSeleccionada(false);
                    clickeada.getNodoAsociado().getInfo().setSeleccionada(false);
                }
                primerSeleccionada = null;
            }
        }

        vista.getTableroView().refrescarCasillas();
        actualizarInfo();
        verificarFinJuego();
    }

    private void manejarPista() {
        limpiarSugeridas();
        if (primerSeleccionada != null) {
            primerSeleccionada.getNodoAsociado().getInfo().setSeleccionada(false);
            primerSeleccionada = null;
        }
        tablero.darPista();
        vista.getTableroView().refrescarCasillas();
    }

    private void manejarDeshacer() {
        if (primerSeleccionada != null) {
            primerSeleccionada.getNodoAsociado().getInfo().setSeleccionada(false);
            primerSeleccionada = null;
        }

        int paresAnteriores = historial.deshacerUltimo();
        if (paresAnteriores != -1) {
            paresEncontrados = paresAnteriores;
            vista.getTableroView().dibujarTablero();
            asignarEventosCasillas();
            actualizarInfo();
            vista.getBotonesView().getAgregarFila().setDisable(tablero.getFilas() >= 8);
        }
    }

    private void manejarAgregarFila() {
        if (tablero.getFilas() >= 8) {
            vista.getBotonesView().getAgregarFila().setDisable(true);
            return;
        }
        historial.guardarEstado(paresEncontrados);
        tablero.agregarFila();
        vista.getTableroView().dibujarTablero();
        asignarEventosCasillas();
        actualizarInfo();
        vista.getBotonesView().getAgregarFila().setDisable(tablero.getFilas() >= 8);
        verificarFinJuego();
    }

    // ─── FIN DE JUEGO ──────────────────────────────────────────────────

    private void verificarFinJuego() {
        boolean sinConcordancias = tablero.contarConcordancias() == 0;
        boolean filasLlenas      = tablero.getFilas() >= 8;

        if (sinConcordancias && filasLlenas) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Game Over");
            alert.setHeaderText("No hay mas concordancias y no puedes poner filas");
            alert.showAndWait();
        }
    }

    private void limpiarSugeridas() {
        for (CasillaView cv : vista.getTableroView().getCasillas()) {
            if (cv.getNodoAsociado() != null && cv.getNodoAsociado().getInfo() != null) {
                cv.getNodoAsociado().getInfo().setSugerida(false);
            }
        }
    }

    private void actualizarInfo() {
        vista.getInfoView().setParesRestantes(tablero.contarConcordancias());
        vista.getInfoView().setParesEncontrados(paresEncontrados);
        vista.getInfoView().setFilasRestantes(tablero.getFilas());
    }
}