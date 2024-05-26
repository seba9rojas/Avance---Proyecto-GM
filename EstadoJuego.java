package com.mygdx.game;

public class EstadoJuego {
    public boolean invencible;
    public long tiempoInvencibilidad;
    public boolean tarroLento;
    public long tiempoInicioLentitud;
    public boolean gotasTransformadas;
    public long tiempoTransformacion;

    public EstadoJuego() {
        this.invencible = false;
        this.tiempoInvencibilidad = 0;
        this.tarroLento = false;
        this.tiempoInicioLentitud = 0;
        this.gotasTransformadas = false;
        this.tiempoTransformacion = 0;
    }

}
