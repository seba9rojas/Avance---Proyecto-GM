package com.mygdx.game;

public class EstadoJuego {
    private boolean invencible;
    private long tiempoInvencibilidad;
    private boolean arcoLento;
    private long tiempoInicioLentitud;
    private boolean balonesTransformados;
    private long tiempoTransformacion;

    public EstadoJuego() {
        this.invencible = false;
        this.tiempoInvencibilidad = 0;
        this.arcoLento = false;
        this.tiempoInicioLentitud = 0;
        this.balonesTransformados = false;
        this.tiempoTransformacion = 0;
    }

    public boolean isInvencible() {
        return invencible;
    }

    public void setInvencible(boolean invencible) {
        this.invencible = invencible;
    }

    public long getTiempoInvencibilidad() {
        return tiempoInvencibilidad;
    }

    public void setTiempoInvencibilidad(long tiempoInvencibilidad) {
        this.tiempoInvencibilidad = tiempoInvencibilidad;
    }

    public boolean isArcoLento() {
        return arcoLento;
    }

    public void setArcoLento(boolean arcoLento) {
        this.arcoLento = arcoLento;
    }

    public long getTiempoInicioLentitud() {
        return tiempoInicioLentitud;
    }

    public void setTiempoInicioLentitud(long tiempoInicioLentitud) {
        this.tiempoInicioLentitud = tiempoInicioLentitud;
    }

    public boolean isBalonesTransformados() {
        return balonesTransformados;
    }

    public void setBalonesTransformados(boolean balonesTransformados) {
        this.balonesTransformados = balonesTransformados;
    }

    public long getTiempoTransformacion() {
        return tiempoTransformacion;
    }

    public void setTiempoTransformacion(long tiempoTransformacion) {
        this.tiempoTransformacion = tiempoTransformacion;
    }
}
